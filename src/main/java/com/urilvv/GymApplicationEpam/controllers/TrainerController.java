package com.urilvv.GymApplicationEpam.controllers;

import com.urilvv.GymApplicationEpam.dtos.TrainerDTO;
import com.urilvv.GymApplicationEpam.dtos.TrainingDTO;
import com.urilvv.GymApplicationEpam.models.Trainer;
import com.urilvv.GymApplicationEpam.requestResponseModels.login.LoginChangeRequest;
import com.urilvv.GymApplicationEpam.requestResponseModels.register.RegisterResponse;
import com.urilvv.GymApplicationEpam.requestResponseModels.register.RegisterTrainerRequest;
import com.urilvv.GymApplicationEpam.requestResponseModels.trainerRequests.TrainerEditRequest;
import com.urilvv.GymApplicationEpam.requestResponseModels.trainerRequests.TrainerTrainingsReq;
import com.urilvv.GymApplicationEpam.services.TrainerService;
import com.urilvv.GymApplicationEpam.utils.Mapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/trainers", produces = {"application/JSON"})
@AllArgsConstructor
public class TrainerController {

    private final TrainerService trainerService;
    private final Mapper mapper;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> registerTrainer(@Valid @RequestBody RegisterTrainerRequest trainerReq) {
        Trainer trainer = trainerService.createTrainer(trainerReq.getFirstName(), trainerReq.getLastName(),
                true, trainerReq.getSpec());
        return new ResponseEntity<>(new RegisterResponse(trainer.getUserId(), trainer.getUsername(), trainer.getPassword()), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TrainerDTO>> getAllTrainers() {
        List<TrainerDTO> trainersDTO = trainerService.getAllTrainers()
                .stream()
                .map(mapper::mapTrainer)
                .toList();

        return new ResponseEntity<>(trainersDTO, HttpStatus.OK);
    }

    @GetMapping("/profile/{username}")
    public ResponseEntity<TrainerDTO> getTrainerProfile(@PathVariable String username) {
        Trainer trainer = trainerService.selectTrainer(username).orElseThrow(ExceptionHandlerController.UserNotFoundException::new);
        return new ResponseEntity<>(mapper.mapTrainer(trainer), HttpStatus.OK);
    }

    @PatchMapping("/trainer/{user_id}/change-active-status")
    public ResponseEntity<String> changeActiveStatus(@PathVariable("user_id") String userId) {
        trainerService.changeActiveStatus(userId);
        return ResponseEntity.status(HttpStatus.OK).body("Active status was changed successfully!");
    }

    @PutMapping("/trainer/change-password")
    public ResponseEntity<String> changeTraineePassword(@Valid @RequestBody LoginChangeRequest loginChangeReq) {
        trainerService.changePassword(loginChangeReq.getUserId(), loginChangeReq.getOldPassword(), loginChangeReq.getNewPassword());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Password was changed successfully!");
    }

    @PutMapping("/trainer/{user_id}/update-profile")
    public ResponseEntity<TrainerDTO> updateTrainerProfile(@PathVariable("user_id") String userId,
                                                           @RequestBody TrainerEditRequest trainerEditReq) {
        TrainerDTO trainerDTO = mapper.mapTrainer(trainerService.editTrainer(userId, trainerEditReq.getFirstName(),
                trainerEditReq.getLastName(), trainerEditReq.getSpec()));
        return new ResponseEntity<>(trainerDTO, HttpStatus.OK);
    }

    @GetMapping("trainer/{username}/trainings")
    public ResponseEntity<List<TrainingDTO>> getTrainerTrainings(@PathVariable String username,
                                                                 @Valid @RequestBody TrainerTrainingsReq trainerTrainingsReq) {
        List<TrainingDTO> trainings = trainerService.getTrainerTrainingList(username, trainerTrainingsReq.getFrom(),
                trainerTrainingsReq.getTo(), trainerTrainingsReq.getTraineeName())
                .stream().map(mapper::mapTraining).toList();
        return new ResponseEntity<>(trainings, HttpStatus.OK);
    }

}

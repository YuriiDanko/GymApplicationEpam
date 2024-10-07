package com.urilvv.GymApplicationEpam.controllers;

import com.urilvv.GymApplicationEpam.dtos.TraineeDTO;
import com.urilvv.GymApplicationEpam.dtos.TrainingDTO;
import com.urilvv.GymApplicationEpam.enums.TrainingType;
import com.urilvv.GymApplicationEpam.models.Trainee;
import com.urilvv.GymApplicationEpam.requestResponseModels.login.LoginChangeRequest;
import com.urilvv.GymApplicationEpam.requestResponseModels.register.RegisterResponse;
import com.urilvv.GymApplicationEpam.requestResponseModels.register.RegisterTraineeRequest;
import com.urilvv.GymApplicationEpam.requestResponseModels.traineeRequests.TraineeEditRequest;
import com.urilvv.GymApplicationEpam.requestResponseModels.traineeRequests.TraineeTrainingsReq;
import com.urilvv.GymApplicationEpam.services.TraineeService;
import com.urilvv.GymApplicationEpam.utils.Mapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/trainees", produces = {"application/JSON"})
@AllArgsConstructor
public class TraineeController {

    private final TraineeService traineeService;
    private final Mapper mapper;

    @GetMapping
    public ResponseEntity<List<TraineeDTO>> getTrainees() {
        List<TraineeDTO> traineeDTOS = traineeService.getAllTrainees()
                .stream()
                .map(mapper::mapTrainee)
                .toList();

        return new ResponseEntity<>(traineeDTOS, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> registerTrainee(@Valid @RequestBody RegisterTraineeRequest traineeReq) {
        Trainee trainee = traineeService.createTrainee(traineeReq.getFirstName(), traineeReq.getLastName(),
                true, traineeReq.getDateOfBirth(), traineeReq.getAddress());
        return new ResponseEntity<>(new RegisterResponse(trainee.getUserId(), trainee.getUsername(), trainee.getPassword()), HttpStatus.CREATED);
    }

    @GetMapping("/profile/{username}")
    public ResponseEntity<TraineeDTO> getTraineeProfile(@PathVariable String username) {
        Trainee trainee = traineeService.selectTrainee(username).orElseThrow(ExceptionHandlerController.UserNotFoundException::new);
        return new ResponseEntity<>(mapper.mapTrainee(trainee), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{user_id}")
    public ResponseEntity<Object> deleteTrainee(@PathVariable("user_id") String userId) {
        boolean isDeleted = traineeService.deleteTrainee(userId);
        return new ResponseEntity<>(isDeleted, HttpStatus.OK);
    }

    @GetMapping("/trainee/{username}/trainings")
    public ResponseEntity<List<TrainingDTO>> getTraineeTrainings(@PathVariable String username,
                                                                 @Valid @RequestBody TraineeTrainingsReq trainingsRequest) {
        List<TrainingDTO> trainings = traineeService.getTraineeTrainingList(username, trainingsRequest.getFrom(),
                trainingsRequest.getTo(), trainingsRequest.getTrainerName(), TrainingType.getType(trainingsRequest.getTrainingType()))
                .stream().map(mapper::mapTraining).toList();

        return new ResponseEntity<>(trainings, HttpStatus.OK);
    }

    @PutMapping("/trainee/change-password")
    public ResponseEntity<String> changeTraineePassword(@Valid @RequestBody LoginChangeRequest loginChangeReq) {
        traineeService.changePassword(loginChangeReq.getUserId(), loginChangeReq.getOldPassword(), loginChangeReq.getNewPassword());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Password was changed successfully!");
    }

    @PatchMapping("/trainee/{user_id}/change-active-status")
    public ResponseEntity<String> changeActiveStatus(@PathVariable("user_id") String userId) {
        traineeService.changeActiveStatus(userId);
        return ResponseEntity.status(HttpStatus.OK).body("Active status was changed successfully!");
    }

    @PutMapping("/trainee/{user_id}/update-profile")
    public ResponseEntity<TraineeDTO> updateTraineeProfile(@PathVariable("user_id") String userId,
                                                           @RequestBody TraineeEditRequest traineeEditReq) {
        TraineeDTO traineeDTO = mapper.mapTrainee(traineeService.editTrainee(userId, traineeEditReq.getFirstName(), traineeEditReq.getLastName(),
                traineeEditReq.getDateOfBirth(), traineeEditReq.getAddress()));
        return new ResponseEntity<>(traineeDTO, HttpStatus.OK);
    }

}

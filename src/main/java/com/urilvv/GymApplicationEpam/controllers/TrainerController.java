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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/trainers")
@AllArgsConstructor
public class TrainerController {

    private final TrainerService trainerService;
    private final Mapper mapper;

    @Operation(
            description = "POST endpoint to register a new Trainer.",
            responses = {
                    @ApiResponse(
                            description = "User was registered successfully.",
                            responseCode = "201"
                    ),
                    @ApiResponse(
                            description = "Error with processing an request.",
                            responseCode = "400"
                    )
            }
    )
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> registerTrainer(@Valid @RequestBody RegisterTrainerRequest trainerReq) {
        Trainer trainer = trainerService.createTrainer(trainerReq.getFirstName(), trainerReq.getLastName(),
                true, trainerReq.getSpec());
        return new ResponseEntity<>(new RegisterResponse(trainer.getUserId(), trainer.getUsername(), trainer.getPassword()), HttpStatus.CREATED);
    }

    @Operation(
            description = "GET endpoint to get a list of all Trainers entities.",
            responses = {
                    @ApiResponse(
                            description = "Successfully returned all Trainers.",
                            responseCode = "200"
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<TrainerDTO>> getAllTrainers() {
        List<TrainerDTO> trainersDTO = trainerService.getAllTrainers()
                .stream()
                .map(mapper::mapTrainer)
                .toList();

        return new ResponseEntity<>(trainersDTO, HttpStatus.OK);
    }

    @Operation(
            description = "GET endpoint to receive Trainer Profile.",
            responses = {
                    @ApiResponse(
                            description = "Profile received successfully.",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "User was not found.",
                            responseCode = "404"
                    )
            }
    )
    @GetMapping("/profile/{username}")
    public ResponseEntity<TrainerDTO> getTrainerProfile(@PathVariable String username) {
        Trainer trainer = trainerService.selectTrainer(username).orElseThrow(ExceptionHandlerController.UserNotFoundException::new);
        return new ResponseEntity<>(mapper.mapTrainer(trainer), HttpStatus.OK);
    }

    @Operation(
            description = "PATCH endpoint to change active status of Trainer.",
            responses = {
                    @ApiResponse(
                            description = "Active status changed successfully.",
                            responseCode = "204"
                    ),
                    @ApiResponse(
                            description = "Error processing request.",
                            responseCode = "400"
                    ),
                    @ApiResponse(
                            description = "User was not found.",
                            responseCode = "404"
                    )
            }
    )
    @PatchMapping("/{user_id}/change-active-status")
    public ResponseEntity<String> changeActiveStatus(@PathVariable("user_id") String userId) {
        trainerService.changeActiveStatus(userId);
        return ResponseEntity.status(HttpStatus.OK).body("Active status was changed successfully!");
    }

    @Operation(
            description = "PUT endpoint to change Trainer password.",
            responses = {
                    @ApiResponse(
                            description = "Password was changed successfully.",
                            responseCode = "204"
                    ),
                    @ApiResponse(
                            description = "User was not found.",
                            responseCode = "404"
                    )
            }
    )
    @PutMapping("/{user_id}/change-password")
    public ResponseEntity<String> changeTraineePassword(@PathVariable("user_id") String userId,
                                                        @Valid @RequestBody LoginChangeRequest loginChangeReq) {
        trainerService.changePassword(userId, loginChangeReq.getOldPassword(), loginChangeReq.getNewPassword());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Password was changed successfully!");
    }

    @Operation(
            description = "PUT endpoint to update Trainer profile.",
            responses = {
                    @ApiResponse(
                            description = "Trainer profile updated successfully.",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "User was not found.",
                            responseCode = "404"
                    )
            }
    )
    @PutMapping("/{user_id}/update-profile")
    public ResponseEntity<TrainerDTO> updateTrainerProfile(@PathVariable("user_id") String userId,
                                                           @RequestBody TrainerEditRequest trainerEditReq) {
        TrainerDTO trainerDTO = mapper.mapTrainer(trainerService.editTrainer(userId, trainerEditReq.getFirstName(),
                trainerEditReq.getLastName(), trainerEditReq.getSpec()));
        return new ResponseEntity<>(trainerDTO, HttpStatus.OK);
    }

    @Operation(
            description = "GET endpoint to receive Trainer trainings entities.",
            responses = {
                    @ApiResponse(
                            description = "Training were returned successfully.",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Error processing request.",
                            responseCode = "400"
                    )
            }
    )
    @GetMapping("/{username}/trainings")
    public ResponseEntity<List<TrainingDTO>> getTrainerTrainings(@PathVariable String username,
                                                                 @Valid @RequestBody TrainerTrainingsReq trainerTrainingsReq) {
        List<TrainingDTO> trainings = trainerService.getTrainerTrainingList(username, trainerTrainingsReq.getFrom(),
                trainerTrainingsReq.getTo(), trainerTrainingsReq.getTraineeName())
                .stream().map(mapper::mapTraining).toList();
        return new ResponseEntity<>(trainings, HttpStatus.OK);
    }

}

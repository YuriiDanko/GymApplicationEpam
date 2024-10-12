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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/trainees")
@AllArgsConstructor
public class TraineeController {

    private final TraineeService traineeService;
    private final Mapper mapper;

    @Operation(
            description = "GET endpoint to get a list of all Trainees entities.",
            responses = {
                    @ApiResponse(
                            description = "Successfully returned all Trainees.",
                            responseCode = "200"
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<TraineeDTO>> getTrainees() {
        List<TraineeDTO> traineeDTOS = traineeService.getAllTrainees()
                .stream()
                .map(mapper::mapTrainee)
                .toList();

        return new ResponseEntity<>(traineeDTOS, HttpStatus.OK);
    }

    @Operation(
            description = "POST endpoint to register a new Trainee.",
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
    public ResponseEntity<RegisterResponse> registerTrainee(@Valid @RequestBody RegisterTraineeRequest traineeReq) {
        Trainee trainee = traineeService.createTrainee(traineeReq.getFirstName(), traineeReq.getLastName(),
                true, traineeReq.getDateOfBirth(), traineeReq.getAddress());
        return new ResponseEntity<>(new RegisterResponse(trainee.getUserId(), trainee.getUsername(), trainee.getPassword()), HttpStatus.CREATED);
    }

    @Operation(
            description = "GET endpoint to receive Trainee Profile.",
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
    public ResponseEntity<TraineeDTO> getTraineeProfile(@PathVariable String username) {
        Trainee trainee = traineeService.selectTrainee(username).orElseThrow(ExceptionHandlerController.UserNotFoundException::new);
        return new ResponseEntity<>(mapper.mapTrainee(trainee), HttpStatus.OK);
    }

    @Operation(
            description = "DELETE endpoint to delete Trainee record.",
            responses = {
                    @ApiResponse(
                            description = "Trainee was deleted successfully.",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "User was not found.",
                            responseCode = "404"
                    )
            }
    )
    @DeleteMapping("/delete/{user_id}")
    public ResponseEntity<Object> deleteTrainee(@PathVariable("user_id") String userId) {
        boolean isDeleted = traineeService.deleteTrainee(userId);
        return new ResponseEntity<>(isDeleted, HttpStatus.OK);
    }

    @Operation(
            description = "GET endpoint to receive Trainee trainings entities.",
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
    public ResponseEntity<List<TrainingDTO>> getTraineeTrainings(@PathVariable String username,
                                                                 @Valid @RequestBody TraineeTrainingsReq trainingsRequest) {
        List<TrainingDTO> trainings = traineeService.getTraineeTrainingList(username, trainingsRequest.getFrom(),
                        trainingsRequest.getTo(), trainingsRequest.getTrainerName(), TrainingType.getType(trainingsRequest.getTrainingType()))
                .stream().map(mapper::mapTraining).toList();

        return new ResponseEntity<>(trainings, HttpStatus.OK);
    }

    @Operation(
            description = "PUT endpoint to change Trainee password.",
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
        traineeService.changePassword(userId, loginChangeReq.getOldPassword(), loginChangeReq.getNewPassword());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Password was changed successfully!");
    }

    @Operation(
            description = "PATCH endpoint to change active status of Trainee.",
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
        traineeService.changeActiveStatus(userId);
        return ResponseEntity.status(HttpStatus.OK).body("Active status was changed successfully!");
    }

    @Operation(
            description = "PUT endpoint to update Trainee profile.",
            responses = {
                    @ApiResponse(
                            description = "Trainee profile updated successfully.",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "User was not found.",
                            responseCode = "404"
                    )
            }
    )
    @PutMapping("/{user_id}/update-profile")
    public ResponseEntity<TraineeDTO> updateTraineeProfile(@PathVariable("user_id") String userId,
                                                           @RequestBody TraineeEditRequest traineeEditReq) {
        TraineeDTO traineeDTO = mapper.mapTrainee(traineeService.editTrainee(userId, traineeEditReq.getFirstName(), traineeEditReq.getLastName(),
                traineeEditReq.getDateOfBirth(), traineeEditReq.getAddress()));
        return new ResponseEntity<>(traineeDTO, HttpStatus.OK);
    }

}

package org.gym.crm.facade;

import org.gym.crm.dto.request.TraineeRequestDto;
import org.gym.crm.dto.request.TrainerRequestDto;
import org.gym.crm.dto.request.TrainingRequestDto;
import org.gym.crm.dto.responce.TraineeResponseDto;
import org.gym.crm.dto.responce.TrainerResponseDto;
import org.gym.crm.dto.responce.TrainingResponseDto;
import org.gym.crm.model.Training;
import org.gym.crm.service.TraineeService;
import org.gym.crm.service.TrainerService;
import org.gym.crm.service.TrainingService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class GymFacade {
    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TrainingService trainingService;

    public GymFacade(TraineeService traineeService,
                     TrainerService trainerService,
                     TrainingService trainingService) {
        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.trainingService = trainingService;
    }

    public TraineeResponseDto createTrainee(Long id, TraineeRequestDto request) {
        return traineeService.create(id, request);
    }

    public Optional<TraineeResponseDto> getTrainee(Long id) {
        return traineeService.findById(id);
    }

    public List<TraineeResponseDto> getAllTrainees() {
        return traineeService.findAll();
    }

    public TraineeResponseDto updateTrainee(Long id, TraineeRequestDto request) {
        return traineeService.update(id, request);
    }

    public void deleteTrainee(Long id) {
        traineeService.delete(id);
    }

    public TrainerResponseDto createTrainer(Long id, TrainerRequestDto request) {
        return trainerService.create(id, request);
    }

    public Optional<TrainerResponseDto> getTrainer(Long id) {
        return trainerService.findById(id);
    }

    public List<TrainerResponseDto> getAllTrainers() {
        return trainerService.findAll();
    }

    public TrainerResponseDto updateTrainer(Long id, TrainerRequestDto request) {
        return trainerService.update(id, request);
    }

    public TrainingResponseDto createTraining(TrainingRequestDto request) {
        return trainingService.create(request);
    }

    public Optional<TrainingResponseDto> getTraining(Long id) {
        return trainingService.findById(id);
    }

    public List<TrainingResponseDto> getAllTrainings() {
        return trainingService.findAll();
    }
}

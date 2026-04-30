package org.gym.crm.facade;

import org.gym.crm.dto.request.TraineeRequestDto;
import org.gym.crm.dto.request.TrainerRequestDto;
import org.gym.crm.dto.request.TrainingRequestDto;
import org.gym.crm.dto.response.TraineeResponseDto;
import org.gym.crm.dto.response.TrainerResponseDto;
import org.gym.crm.dto.response.TrainingResponseDto;
import org.gym.crm.mapper.TraineeMapper;
import org.gym.crm.mapper.TrainerMapper;
import org.gym.crm.mapper.TrainingMapper;
import org.gym.crm.model.Trainee;
import org.gym.crm.model.Trainer;
import org.gym.crm.model.Training;
import org.gym.crm.service.TraineeService;
import org.gym.crm.service.TrainerService;
import org.gym.crm.service.TrainingService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class GymFacade {
    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TrainingService trainingService;

    private final TraineeMapper traineeMapper;
    private final TrainerMapper trainerMapper;
    private final TrainingMapper trainingMapper;

    public GymFacade(TraineeService traineeService,
                     TrainerService trainerService,
                     TrainingService trainingService,
                     TraineeMapper traineeMapper,
                     TrainerMapper trainerMapper,
                     TrainingMapper trainingMapper) {
        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.trainingService = trainingService;
        this.traineeMapper = traineeMapper;
        this.trainerMapper = trainerMapper;
        this.trainingMapper = trainingMapper;
    }

    public TraineeResponseDto createTrainee(TraineeRequestDto request) {
        Trainee trainee = traineeMapper.toEntity(request);

        return traineeMapper.toDto(traineeService.create(trainee));
    }

    public Optional<TraineeResponseDto> getTrainee(Long id) {
        return traineeService.findById(id)
                .map(traineeMapper::toDto);
    }

    public List<TraineeResponseDto> getAllTrainees() {
        return traineeService.findAll().stream()
                .map(traineeMapper::toDto)
                .collect(Collectors.toList());
    }

    public TraineeResponseDto updateTrainee(TraineeRequestDto request) {
        Trainee trainee = traineeMapper.toEntity(request);

        return traineeMapper.toDto(traineeService.update(trainee));
    }

    public void deleteTrainee(Long id) {
        traineeService.delete(id);
    }

    public TrainerResponseDto createTrainer(TrainerRequestDto request) {
        Trainer trainer = trainerMapper.toEntity(request);

        return trainerMapper.toDto(trainerService.create(trainer));
    }

    public Optional<TrainerResponseDto> getTrainer(Long id) {
        return trainerService.findById(id)
                .map(trainerMapper::toDto);
    }

    public List<TrainerResponseDto> getAllTrainers() {
        return trainerService.findAll().stream()
                .map(trainerMapper::toDto)
                .collect(Collectors.toList());
    }

    public TrainerResponseDto updateTrainer(TrainerRequestDto request) {
        Trainer trainer = trainerMapper.toEntity(request);

        return trainerMapper.toDto(trainerService.update(trainer));
    }

    public TrainingResponseDto createTraining(TrainingRequestDto request) {
        Training training = trainingMapper.toEntity(request);

        return trainingMapper.toDto(trainingService.create(training));
    }

    public Optional<TrainingResponseDto> getTraining(Long id) {
        return trainingService.findById(id)
                .map(trainingMapper::toDto);
    }

    public List<TrainingResponseDto> getAllTrainings() {
        return trainingService.findAll().stream()
                .map(trainingMapper::toDto)
                .collect(Collectors.toList());
    }
}

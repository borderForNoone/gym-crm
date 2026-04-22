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

    public TraineeResponseDto createTrainee(Long id, TraineeRequestDto request) {
        Trainee trainee = traineeMapper.toEntity(request);

        return traineeMapper.toResponseDto(traineeService.create(id, trainee));
    }

    public Optional<TraineeResponseDto> getTrainee(Long id) {
        return traineeService.findById(id)
                .map(traineeMapper::toResponseDto);
    }

    public List<TraineeResponseDto> getAllTrainees() {
        return traineeService.findAll().stream()
                .map(traineeMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public TraineeResponseDto updateTrainee(Long id, TraineeRequestDto request) {
        Trainee trainee = traineeMapper.toEntity(request);

        return traineeMapper.toResponseDto(traineeService.update(id, trainee));
    }

    public void deleteTrainee(Long id) {
        traineeService.delete(id);
    }

    public TrainerResponseDto createTrainer(Long id, TrainerRequestDto request) {
        Trainer trainer = trainerMapper.toEntity(request);

        return trainerMapper.toResponseDto(trainerService.create(id, trainer));
    }

    public Optional<TrainerResponseDto> getTrainer(Long id) {
        return trainerService.findById(id)
                .map(trainerMapper::toResponseDto);
    }

    public List<TrainerResponseDto> getAllTrainers() {
        return trainerService.findAll().stream()
                .map(trainerMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public TrainerResponseDto updateTrainer(Long id, TrainerRequestDto request) {
        Trainer trainer = trainerMapper.toEntity(request);

        return trainerMapper.toResponseDto(trainerService.update(id, trainer));
    }

    public TrainingResponseDto createTraining(TrainingRequestDto request) {
        Training training = trainingMapper.toEntity(request);

        return trainingMapper.toResponseDto(trainingService.create(training));
    }

    public Optional<TrainingResponseDto> getTraining(Long id) {
        return trainingService.findById(id)
                .map(trainingMapper::toResponseDto);
    }

    public List<TrainingResponseDto> getAllTrainings() {
        return trainingService.findAll().stream()
                .map(trainingMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}

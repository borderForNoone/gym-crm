package org.gym.crm.facade;

import org.gym.crm.model.Trainee;
import org.gym.crm.model.Trainer;
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

    public Trainee createTrainee(Long id, Trainee trainee) {
        return traineeService.create(id, trainee);
    }

    public Optional<Trainee> getTrainee(Long id) {
        return traineeService.findById(id);
    }

    public List<Trainee> getAllTrainees() {
        return traineeService.findAll();
    }

    public Trainee updateTrainee(Long id, Trainee trainee) {
        return traineeService.update(id, trainee);
    }

    public void deleteTrainee(Long id) {
        traineeService.delete(id);
    }

    public Trainer createTrainer(Long id, Trainer trainer) {
        return trainerService.create(id, trainer);
    }

    public Optional<Trainer> getTrainer(Long id) {
        return trainerService.findById(id);
    }

    public List<Trainer> getAllTrainers() {
        return trainerService.findAll();
    }

    public Trainer updateTrainer(Long id, Trainer trainer) {
        return trainerService.update(id, trainer);
    }

    public Training createTraining(Training training) {
        return trainingService.create(training);
    }

    public Optional<Training> getTraining(Long id) {
        return trainingService.findById(id);
    }

    public List<Training> getAllTrainings() {
        return trainingService.findAll();
    }
}

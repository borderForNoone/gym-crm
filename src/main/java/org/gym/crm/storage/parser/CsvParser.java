package org.gym.crm.storage.parser;

import org.gym.crm.model.Trainee;
import org.gym.crm.model.Trainer;
import org.gym.crm.model.Training;
import org.gym.crm.model.TrainingType;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class CsvParser {
    public Trainee parseTrainee(String[] fields) {
        return Trainee.builder()
                .firstName(fields[1])
                .lastName(fields[2])
                .username(fields[3])
                .password(fields[4])
                .isActive(Boolean.parseBoolean(fields[5]))
                .dateOfBirth(fields[6].isBlank() ? null : LocalDate.parse(fields[6]))
                .address(fields[7])
                .userId(Long.parseLong(fields[8]))
                .build();
    }

    public Trainer parseTrainer(String[] fields) {
        TrainingType specialization = new TrainingType();
        specialization.setTrainingTypeName(fields[6]);

        return Trainer.builder()
                .firstName(fields[1])
                .lastName(fields[2])
                .username(fields[3])
                .password(fields[4])
                .isActive(Boolean.parseBoolean(fields[5]))
                .specialization(specialization)
                .userId(Long.parseLong(fields[7]))
                .build();
    }

    public Training parseTraining(String[] fields) {
        TrainingType trainingType = new TrainingType();
        trainingType.setTrainingTypeName(fields[4]);

        return Training.builder()
                .id(Long.parseLong(fields[0]))
                .traineeId(Long.parseLong(fields[1]))
                .trainerId(Long.parseLong(fields[2]))
                .trainingName(fields[3])
                .trainingType(trainingType)
                .trainingDate(LocalDate.parse(fields[5]))
                .trainingDuration(Integer.parseInt(fields[6]))
                .build();
    }
}

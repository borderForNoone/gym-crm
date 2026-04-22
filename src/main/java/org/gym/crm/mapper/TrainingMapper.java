package org.gym.crm.mapper;

import org.gym.crm.dto.request.TrainingRequestDto;
import org.gym.crm.dto.response.TrainingResponseDto;
import org.gym.crm.model.Training;
import org.springframework.stereotype.Component;

@Component
public class TrainingMapper {
    public Training toEntity(TrainingRequestDto request) {
        return Training.builder()
                .traineeId(request.getTraineeId())
                .trainerId(request.getTrainerId())
                .trainingName(request.getTrainingName())
                .trainingType(request.getTrainingType())
                .trainingDate(request.getTrainingDate())
                .trainingDuration(request.getTrainingDuration())
                .build();
    }

    public TrainingResponseDto toResponseDto(Training training) {
        return TrainingResponseDto.builder()
                .id(training.getId())
                .traineeId(training.getTraineeId())
                .trainerId(training.getTrainerId())
                .trainingName(training.getTrainingName())
                .trainingType(training.getTrainingType())
                .trainingDate(training.getTrainingDate())
                .trainingDuration(training.getTrainingDuration())
                .build();
    }
}

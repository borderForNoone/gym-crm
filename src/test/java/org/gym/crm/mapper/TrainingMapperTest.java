package org.gym.crm.mapper;

import org.gym.crm.dto.request.TrainingRequestDto;
import org.gym.crm.dto.response.TrainingResponseDto;
import org.gym.crm.model.Training;
import org.gym.crm.model.TrainingType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.gym.crm.util.TestConstants.DURATION;
import static org.gym.crm.util.TestConstants.FITNESS;
import static org.gym.crm.util.TestConstants.ID;
import static org.gym.crm.util.TestConstants.SECOND_ID;
import static org.gym.crm.util.TestConstants.THIRD_ID;
import static org.gym.crm.util.TestConstants.TRAINING_DATE;
import static org.gym.crm.util.TestConstants.TRAINING_NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TrainingMapperTest {
    private TrainingMapper trainingMapper;
    private TrainingType fitness;

    @BeforeEach
    void setUp() {
        trainingMapper = new TrainingMapper();
        fitness = buildFitnessType();
    }

    @Test
    void toEntity_shouldMapAllFields() {
        TrainingRequestDto request = TrainingRequestDto.builder()
                .traineeId(SECOND_ID)
                .trainerId(THIRD_ID)
                .trainingName(TRAINING_NAME)
                .trainingType(fitness)
                .trainingDate(TRAINING_DATE)
                .trainingDuration(DURATION)
                .build();

        Training actual = trainingMapper.toEntity(request);

        assertEquals(SECOND_ID, actual.getTraineeId());
        assertEquals(THIRD_ID, actual.getTrainerId());
        assertEquals(TRAINING_NAME, actual.getTrainingName());
        assertEquals(fitness, actual.getTrainingType());
        assertEquals(TRAINING_DATE, actual.getTrainingDate());
        assertEquals(DURATION, actual.getTrainingDuration());
    }

    @Test
    void toEntity_shouldNotSetId() {
        TrainingRequestDto request = TrainingRequestDto.builder()
                .traineeId(SECOND_ID)
                .trainerId(THIRD_ID)
                .trainingName(TRAINING_NAME)
                .trainingType(fitness)
                .trainingDate(TRAINING_DATE)
                .trainingDuration(DURATION)
                .build();

        Training actual = trainingMapper.toEntity(request);

        assertNull(actual.getId());
    }

    @Test
    void toResponseDto_shouldMapAllFields() {
        Training training = Training.builder()
                .id(ID)
                .traineeId(SECOND_ID)
                .trainerId(THIRD_ID)
                .trainingName(TRAINING_NAME)
                .trainingType(fitness)
                .trainingDate(TRAINING_DATE)
                .trainingDuration(DURATION)
                .build();

        TrainingResponseDto actual = trainingMapper.toResponseDto(training);

        assertEquals(ID, actual.getId());
        assertEquals(SECOND_ID, actual.getTraineeId());
        assertEquals(THIRD_ID, actual.getTrainerId());
        assertEquals(TRAINING_NAME, actual.getTrainingName());
        assertEquals(fitness, actual.getTrainingType());
        assertEquals(TRAINING_DATE, actual.getTrainingDate());
        assertEquals(DURATION, actual.getTrainingDuration());
    }

    private TrainingType buildFitnessType() {
        TrainingType fitness = new TrainingType();
        fitness.setTrainingTypeName(FITNESS);

        return fitness;
    }
}
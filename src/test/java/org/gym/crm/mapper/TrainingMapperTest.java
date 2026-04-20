package org.gym.crm.mapper;

import org.gym.crm.dto.request.TrainingRequestDto;
import org.gym.crm.dto.responce.TrainingResponseDto;
import org.gym.crm.model.Training;
import org.gym.crm.model.TrainingType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TrainingMapperTest {

    private TrainingMapper trainingMapper;
    private TrainingType fitness;

    @BeforeEach
    void setUp() {
        trainingMapper = new TrainingMapper();
        fitness = new TrainingType();
        fitness.setTrainingTypeName("FITNESS");
    }

    @Test
    void toEntity_shouldMapAllFields() {
        TrainingRequestDto request = TrainingRequestDto.builder()
                .traineeId(1L)
                .trainerId(2L)
                .trainingName("Morning Workout")
                .trainingType(fitness)
                .trainingDate(LocalDate.of(2024, 3, 15))
                .trainingDuration(60)
                .build();

        Training result = trainingMapper.toEntity(request);

        assertEquals(1L, result.getTraineeId());
        assertEquals(2L, result.getTrainerId());
        assertEquals("Morning Workout", result.getTrainingName());
        assertEquals(fitness, result.getTrainingType());
        assertEquals(LocalDate.of(2024, 3, 15), result.getTrainingDate());
        assertEquals(60, result.getTrainingDuration());
    }

    @Test
    void toEntity_shouldNotSetId() {
        TrainingRequestDto request = TrainingRequestDto.builder()
                .traineeId(1L)
                .trainerId(2L)
                .trainingName("Morning Workout")
                .trainingType(fitness)
                .trainingDate(LocalDate.of(2024, 3, 15))
                .trainingDuration(60)
                .build();

        Training result = trainingMapper.toEntity(request);

        assertNull(result.getId());
    }

    @Test
    void toResponseDto_shouldMapAllFields() {
        Training training = Training.builder()
                .id(1L)
                .traineeId(2L)
                .trainerId(3L)
                .trainingName("Morning Workout")
                .trainingType(fitness)
                .trainingDate(LocalDate.of(2024, 3, 15))
                .trainingDuration(60)
                .build();

        TrainingResponseDto result = trainingMapper.toResponseDto(training);

        assertEquals(1L, result.getId());
        assertEquals(2L, result.getTraineeId());
        assertEquals(3L, result.getTrainerId());
        assertEquals("Morning Workout", result.getTrainingName());
        assertEquals(fitness, result.getTrainingType());
        assertEquals(LocalDate.of(2024, 3, 15), result.getTrainingDate());
        assertEquals(60, result.getTrainingDuration());
    }
}
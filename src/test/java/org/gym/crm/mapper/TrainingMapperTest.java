package org.gym.crm.mapper;

import org.gym.crm.dto.request.TrainingRequestDto;
import org.gym.crm.dto.response.TrainingResponseDto;
import org.gym.crm.model.Training;
import org.gym.crm.model.TrainingType;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;

import static org.gym.crm.util.TestConstants.ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TrainingMapperTest {
    private static final String TRAINING_NAME = "Morning Cardio";
    private static final String TRAINING_TYPE_NAME = "Cardio";
    private static final LocalDate TRAINING_DATE = LocalDate.of(2026, 4, 4);
    private static final int TRAINING_DURATION = 60;
    private static final long VALID_ID = 1L;

    private final TrainingMapper mapper = Mappers.getMapper(TrainingMapper.class);

    @Test
    void toEntity_shouldMapAllFields_whenMapFromTrainingRequestDTO() {
        TrainingRequestDto dto = buildTrainingRequestDTO();

        Training training = mapper.toEntity(dto);

        assertEquals(VALID_ID, training.getTraineeId());
        assertEquals(VALID_ID, training.getTrainerId());
        assertEquals(TRAINING_NAME, training.getTrainingName());
        assertEquals(TRAINING_DATE, training.getTrainingDate());
        assertEquals(TRAINING_DURATION, training.getTrainingDuration());

        assertNull(training.getTrainingType());
        assertNull(training.getId());
    }

    @Test
    void toDto_shouldMapAllFields_whenMapFromTrainingEntity() {
        Training training = buildTraining();

        TrainingResponseDto trainingResponseDTO = mapper.toDto(training);

        assertEquals(VALID_ID, trainingResponseDTO.getTraineeId());
        assertEquals(VALID_ID, trainingResponseDTO.getTrainerId());
        assertEquals(TRAINING_NAME, trainingResponseDTO.getTrainingName());
        assertEquals(TRAINING_TYPE_NAME, trainingResponseDTO.getTrainingTypeName());
        assertEquals(TRAINING_DATE, trainingResponseDTO.getTrainingDate());
        assertEquals(TRAINING_DURATION, trainingResponseDTO.getTrainingDuration());
    }

    private TrainingRequestDto buildTrainingRequestDTO() {
        return TrainingRequestDto.builder()
                .traineeId(VALID_ID)
                .trainerId(VALID_ID)
                .trainingName(TRAINING_NAME)
                .trainingTypeId(ID)
                .trainingDate(TRAINING_DATE)
                .trainingDuration(TRAINING_DURATION)
                .build();
    }

    private Training buildTraining() {
        return Training.builder()
                .id(VALID_ID)
                .traineeId(VALID_ID)
                .trainerId(VALID_ID)
                .trainingName(TRAINING_NAME)
                .trainingType(buildTrainingType())
                .trainingDate(TRAINING_DATE)
                .trainingDuration(TRAINING_DURATION)
                .build();
    }

    private TrainingType buildTrainingType() {
        TrainingType type = new TrainingType();
        type.setTrainingTypeName(TRAINING_TYPE_NAME);

        return type;
    }
}
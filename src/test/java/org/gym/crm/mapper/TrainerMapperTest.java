package org.gym.crm.mapper;

import org.gym.crm.dto.request.TrainerRequestDto;
import org.gym.crm.dto.response.TrainerResponseDto;
import org.gym.crm.model.Trainer;
import org.gym.crm.model.TrainingType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.gym.crm.util.TestConstants.FIRST_NAME;
import static org.gym.crm.util.TestConstants.FITNESS;
import static org.gym.crm.util.TestConstants.ID;
import static org.gym.crm.util.TestConstants.LAST_NAME;
import static org.gym.crm.util.TestConstants.TRAINER_USERNAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TrainerMapperTest {
    private TrainerMapper trainerMapper;
    private TrainingType fitness;

    @BeforeEach
    void setUp() {
        trainerMapper = new TrainerMapper();
        fitness = buildFitnessType();
    }

    @Test
    void toEntity_shouldMapAllFields() {
        TrainerRequestDto request = TrainerRequestDto.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .isActive(true)
                .specialization(fitness)
                .build();

        Trainer actual = trainerMapper.toEntity(request);

        assertEquals(FIRST_NAME, actual.getFirstName());
        assertEquals(LAST_NAME, actual.getLastName());
        assertTrue(actual.isActive());
        assertEquals(fitness, actual.getSpecialization());
    }

    @Test
    void toEntity_shouldNotSetUsernameAndPassword() {
        TrainerRequestDto request = TrainerRequestDto.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .isActive(true)
                .specialization(fitness)
                .build();

        Trainer actual = trainerMapper.toEntity(request);

        assertNull(actual.getUsername());
        assertNull(actual.getPassword());
    }

    @Test
    void toResponseDto_shouldMapAllFields() {
        Trainer trainer = Trainer.builder()
                .userId(ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .username(TRAINER_USERNAME)
                .isActive(true)
                .specialization(fitness)
                .build();

        TrainerResponseDto actual = trainerMapper.toResponseDto(trainer);

        assertEquals(ID, actual.getId());
        assertEquals(TRAINER_USERNAME, actual.getUsername());
        assertEquals(FIRST_NAME, actual.getFirstName());
        assertEquals(LAST_NAME, actual.getLastName());
        assertTrue(actual.isActive());
        assertEquals(fitness, actual.getSpecialization());
    }

    @Test
    void toResponseDto_shouldHandleNullSpecialization() {
        Trainer trainer = Trainer.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .username(TRAINER_USERNAME)
                .isActive(true)
                .specialization(null)
                .build();

        TrainerResponseDto actual = trainerMapper.toResponseDto(trainer);

        assertNull(actual.getSpecialization());
    }

    private TrainingType buildFitnessType() {
        TrainingType fitness = new TrainingType();
        fitness.setTrainingTypeName(FITNESS);

        return fitness;
    }
}
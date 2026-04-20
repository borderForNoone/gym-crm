package org.gym.crm.mapper;

import org.gym.crm.dto.request.TrainerRequestDto;
import org.gym.crm.dto.responce.TrainerResponseDto;
import org.gym.crm.model.Trainer;
import org.gym.crm.model.TrainingType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TrainerMapperTest {

    private TrainerMapper trainerMapper;
    private TrainingType fitness;

    @BeforeEach
    void setUp() {
        trainerMapper = new TrainerMapper();
        fitness = new TrainingType();
        fitness.setTrainingTypeName("FITNESS");
    }

    @Test
    void toEntity_shouldMapAllFields() {
        TrainerRequestDto request = TrainerRequestDto.builder()
                .firstName("Jane")
                .lastName("Doe")
                .isActive(true)
                .specialization(fitness)
                .build();

        Trainer result = trainerMapper.toEntity(request);

        assertEquals("Jane", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertTrue(result.isActive());
        assertEquals(fitness, result.getSpecialization());
    }

    @Test
    void toEntity_shouldNotSetUsernameAndPassword() {
        TrainerRequestDto request = TrainerRequestDto.builder()
                .firstName("Jane")
                .lastName("Doe")
                .isActive(true)
                .specialization(fitness)
                .build();

        Trainer result = trainerMapper.toEntity(request);

        assertNull(result.getUsername());
        assertNull(result.getPassword());
    }

    @Test
    void toResponseDto_shouldMapAllFields() {
        Trainer trainer = Trainer.builder()
                .userId(1L)
                .firstName("Jane")
                .lastName("Doe")
                .username("Jane.Doe")
                .isActive(true)
                .specialization(fitness)
                .build();

        TrainerResponseDto result = trainerMapper.toResponseDto(trainer);

        assertEquals(1L, result.getId());
        assertEquals("Jane.Doe", result.getUsername());
        assertEquals("Jane", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertTrue(result.isActive());
        assertEquals(fitness, result.getSpecialization());
    }

    @Test
    void toResponseDto_shouldHandleNullSpecialization() {
        Trainer trainer = Trainer.builder()
                .firstName("Jane")
                .lastName("Doe")
                .username("Jane.Doe")
                .isActive(true)
                .specialization(null)
                .build();

        TrainerResponseDto result = trainerMapper.toResponseDto(trainer);

        assertNull(result.getSpecialization());
    }
}
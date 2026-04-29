package org.gym.crm.mapper;

import org.gym.crm.dto.request.TrainerRequestDto;
import org.gym.crm.dto.response.TrainerResponseDto;
import org.gym.crm.dto.update.TrainerUpdateDTO;
import org.gym.crm.model.Trainer;
import org.gym.crm.model.TrainingType;
import org.gym.crm.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.gym.crm.util.TestConstants.FIRST_NAME;
import static org.gym.crm.util.TestConstants.FITNESS;
import static org.gym.crm.util.TestConstants.ID;
import static org.gym.crm.util.TestConstants.LAST_NAME;
import static org.gym.crm.util.TestConstants.TRAINER_USERNAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TrainerMapperTest {
    private TrainerMapper trainerMapper;
    private TrainingType fitness;

    @BeforeEach
    void setUp() {
        trainerMapper = Mappers.getMapper(TrainerMapper.class);
        fitness = buildFitnessType();
    }

    @Test
    void toEntity_shouldMapAllFields() {
        TrainerRequestDto request = TrainerRequestDto.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .isActive(true)
                .specialization(fitness.getTrainingTypeName())
                .build();

        Trainer actual = trainerMapper.toEntity(request);

        assertNull(actual.getUser());
        assertEquals(fitness, actual.getSpecialization());
    }

    @Test
    void toEntity_shouldNotSetUsernameAndPassword() {
        TrainerRequestDto request = TrainerRequestDto.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .isActive(true)
                .specialization(fitness.getTrainingTypeName())
                .build();

        Trainer actual = trainerMapper.toEntity(request);

        assertNull(actual.getUser());
    }

    @Test
    void toResponseDto_shouldMapAllFields() {
        User user = User.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .username(TRAINER_USERNAME)
                .isActive(true)
                .build();

        Trainer trainer = Trainer.builder()
                .userId(ID)
                .user(user)
                .specialization(fitness)
                .build();

        TrainerResponseDto actual = trainerMapper.toDto(trainer);

        assertEquals(ID, actual.getId());
        assertEquals(TRAINER_USERNAME, actual.getUsername());
        assertEquals(FIRST_NAME, actual.getFirstName());
        assertEquals(LAST_NAME, actual.getLastName());
        assertTrue(actual.isActive());
        assertEquals(fitness, actual.getSpecialization());
    }

    @Test
    void toEntity_shouldReturnNull_whenRequestIsNull() {
        assertNull(trainerMapper.toEntity((TrainerRequestDto) null));
    }

    @Test
    void toEntity_update_shouldReturnNull_whenDtoIsNull() {
        assertNull(trainerMapper.toEntity((TrainerUpdateDTO) null));
    }

    @Test
    void toDto_shouldReturnNull_whenTrainerIsNull() {
        assertNull(trainerMapper.toDto(null));
    }

    @Test
    void toEntity_update_shouldMapAllFields() {
        TrainerUpdateDTO dto = TrainerUpdateDTO.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .isActive(true)
                .specialization(FITNESS)
                .build();

        Trainer actual = trainerMapper.toEntity(dto);

        assertEquals(FIRST_NAME, actual.getUser().getFirstName());
        assertEquals(LAST_NAME, actual.getUser().getLastName());
        assertTrue(actual.getUser().getIsActive());
        assertEquals(FITNESS, actual.getSpecialization().getTrainingTypeName());
    }

    @Test
    void toDto_shouldHandleNullUser() {
        Trainer trainer = Trainer.builder()
                .user(null)
                .specialization(fitness)
                .build();

        TrainerResponseDto actual = trainerMapper.toDto(trainer);

        assertNull(actual.getUsername());
        assertNull(actual.getFirstName());
        assertNull(actual.getLastName());
        assertFalse(actual.isActive());
    }

    @Test
    void toDto_shouldHandleNullSpecialization() {
        User user = User.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .username(TRAINER_USERNAME)
                .isActive(true)
                .build();

        Trainer trainer = Trainer.builder()
                .user(user)
                .specialization(null)
                .build();

        TrainerResponseDto actual = trainerMapper.toDto(trainer);

        assertNull(actual.getSpecialization());
    }

    @Test
    void map_shouldReturnNull_whenStringIsNull() {
        assertNull(trainerMapper.map((String) null));
    }

    @Test
    void map_shouldConvertStringToTrainingType() {
        TrainingType actual = trainerMapper.map(FITNESS);

        assertEquals(FITNESS, actual.getTrainingTypeName());
    }

    @Test
    void map_shouldReturnNull_whenTrainingTypeIsNull() {
        assertNull(trainerMapper.map((TrainingType) null));
    }

    private TrainingType buildFitnessType() {
        TrainingType fitness = new TrainingType();
        fitness.setTrainingTypeName(FITNESS);

        return fitness;
    }
}
package org.gym.crm.mapper;

import org.gym.crm.dto.request.TraineeRequestDto;
import org.gym.crm.dto.responce.TraineeResponseDto;
import org.gym.crm.model.Trainee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TraineeMapperTest {

    private TraineeMapper traineeMapper;

    @BeforeEach
    void setUp() {
        traineeMapper = new TraineeMapper();
    }

    @Test
    void toEntity_shouldMapAllFields() {
        TraineeRequestDto request = TraineeRequestDto.builder()
                .firstName("John")
                .lastName("Smith")
                .isActive(true)
                .dateOfBirth(LocalDate.of(1990, 1, 15))
                .address("123 Main St")
                .build();

        Trainee result = traineeMapper.toEntity(request);

        assertEquals("John", result.getFirstName());
        assertEquals("Smith", result.getLastName());
        assertTrue(result.isActive());
        assertEquals(LocalDate.of(1990, 1, 15), result.getDateOfBirth());
        assertEquals("123 Main St", result.getAddress());
    }

    @Test
    void toEntity_shouldNotSetUsernameAndPassword() {
        TraineeRequestDto request = TraineeRequestDto.builder()
                .firstName("John")
                .lastName("Smith")
                .isActive(true)
                .build();

        Trainee result = traineeMapper.toEntity(request);

        assertNull(result.getUsername());
        assertNull(result.getPassword());
    }

    @Test
    void toResponseDto_shouldMapAllFields() {
        Trainee trainee = Trainee.builder()
                .userId(1L)
                .firstName("John")
                .lastName("Smith")
                .username("John.Smith")
                .isActive(true)
                .dateOfBirth(LocalDate.of(1990, 1, 15))
                .address("123 Main St")
                .build();

        TraineeResponseDto result = traineeMapper.toResponseDto(trainee);

        assertEquals(1L, result.getId());
        assertEquals("John.Smith", result.getUsername());
        assertEquals("John", result.getFirstName());
        assertEquals("Smith", result.getLastName());
        assertTrue(result.isActive());
        assertEquals(LocalDate.of(1990, 1, 15), result.getDateOfBirth());
        assertEquals("123 Main St", result.getAddress());
    }

    @Test
    void toResponseDto_shouldHandleNullDateOfBirth() {
        Trainee trainee = Trainee.builder()
                .firstName("John")
                .lastName("Smith")
                .username("John.Smith")
                .isActive(true)
                .dateOfBirth(null)
                .build();

        TraineeResponseDto result = traineeMapper.toResponseDto(trainee);

        assertNull(result.getDateOfBirth());
    }
}
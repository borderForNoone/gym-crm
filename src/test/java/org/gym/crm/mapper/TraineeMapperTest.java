package org.gym.crm.mapper;

import org.gym.crm.dto.request.TraineeRequestDto;
import org.gym.crm.dto.response.TraineeResponseDto;
import org.gym.crm.model.Trainee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.gym.crm.util.TestConstants.ADDRESS;
import static org.gym.crm.util.TestConstants.DATE_OF_BIRTH;
import static org.gym.crm.util.TestConstants.FIRST_NAME;
import static org.gym.crm.util.TestConstants.ID;
import static org.gym.crm.util.TestConstants.LAST_NAME;
import static org.gym.crm.util.TestConstants.USERNAME;
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
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .isActive(true)
                .dateOfBirth(DATE_OF_BIRTH)
                .address(ADDRESS)
                .build();

        Trainee actual = traineeMapper.toEntity(request);

        assertEquals(FIRST_NAME, actual.getFirstName());
        assertEquals(LAST_NAME, actual.getLastName());
        assertTrue(actual.isActive());
        assertEquals(DATE_OF_BIRTH, actual.getDateOfBirth());
        assertEquals(ADDRESS, actual.getAddress());
    }

    @Test
    void toEntity_shouldNotSetUsernameAndPassword() {
        TraineeRequestDto request = TraineeRequestDto.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .isActive(true)
                .build();

        Trainee actual = traineeMapper.toEntity(request);

        assertNull(actual.getUsername());
        assertNull(actual.getPassword());
    }

    @Test
    void toResponseDto_shouldMapAllFields() {
        Trainee trainee = Trainee.builder()
                .userId(ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .username(USERNAME)
                .isActive(true)
                .dateOfBirth(DATE_OF_BIRTH)
                .address(ADDRESS)
                .build();

        TraineeResponseDto actual = traineeMapper.toResponseDto(trainee);

        assertEquals(ID, actual.getId());
        assertEquals(USERNAME, actual.getUsername());
        assertEquals(FIRST_NAME, actual.getFirstName());
        assertEquals(LAST_NAME, actual.getLastName());
        assertTrue(actual.isActive());
        assertEquals(DATE_OF_BIRTH, actual.getDateOfBirth());
        assertEquals(ADDRESS, actual.getAddress());
    }

    @Test
    void toResponseDto_shouldHandleNullDateOfBirth() {
        Trainee trainee = Trainee.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .username(USERNAME)
                .isActive(true)
                .dateOfBirth(null)
                .build();

        TraineeResponseDto actual = traineeMapper.toResponseDto(trainee);

        assertNull(actual.getDateOfBirth());
    }
}
package org.gym.crm.mapper;

import org.gym.crm.dto.request.TraineeRequestDto;
import org.gym.crm.dto.response.TraineeResponseDto;
import org.gym.crm.model.Trainee;
import org.gym.crm.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

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
        traineeMapper = Mappers.getMapper(TraineeMapper.class);
    }

    @Test
    void toEntity_shouldMapAllFields() {
        TraineeRequestDto request = TraineeRequestDto.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .active(true)
                .dateOfBirth(DATE_OF_BIRTH)
                .address(ADDRESS)
                .build();

        Trainee actual = traineeMapper.toEntity(request);

        assertEquals(FIRST_NAME, actual.getUser().getFirstName());
        assertEquals(LAST_NAME, actual.getUser().getLastName());
        assertTrue(actual.getUser().getIsActive());
        assertEquals(DATE_OF_BIRTH, actual.getDateOfBirth());
        assertEquals(ADDRESS, actual.getAddress());
    }

    @Test
    void toEntity_shouldNotSetUsernameAndPassword() {
        TraineeRequestDto request = TraineeRequestDto.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .active(true)
                .build();

        Trainee actual = traineeMapper.toEntity(request);

        assertNull(actual.getUser().getUsername());
        assertNull(actual.getUser().getPassword());
    }

    @Test
    void toResponseDto_shouldMapAllFields() {
        Trainee trainee = Trainee.builder()
                .userId(ID)
                .user(User.builder()
                        .firstName(FIRST_NAME)
                        .lastName(LAST_NAME)
                        .username(USERNAME)
                        .isActive(true)
                        .build())
                .dateOfBirth(DATE_OF_BIRTH)
                .address(ADDRESS)
                .build();

        TraineeResponseDto actual = traineeMapper.toDto(trainee);

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
        User user = User.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .username(USERNAME)
                .isActive(true)
                .build();

        Trainee trainee = Trainee.builder()
                .user(user)
                .dateOfBirth(null)
                .build();

        TraineeResponseDto actual = traineeMapper.toDto(trainee);

        assertNull(actual.getDateOfBirth());
    }
}
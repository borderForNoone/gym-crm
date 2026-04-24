package org.gym.crm.service;

import org.gym.crm.dao.TraineeDao;
import org.gym.crm.dao.TrainerDao;
import org.gym.crm.model.Trainee;
import org.gym.crm.model.Trainer;
import org.gym.crm.model.TrainingType;
import org.gym.crm.service.impl.UserProfileServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.gym.crm.util.TestConstants.ALLOWED_CHARS;
import static org.gym.crm.util.TestConstants.FIRST_NAME;
import static org.gym.crm.util.TestConstants.LAST_NAME;
import static org.gym.crm.util.TestConstants.PASSWORD_LENGTH;
import static org.gym.crm.util.TestConstants.USERNAME;
import static org.gym.crm.util.TestConstants.USERNAME_WITH_SUFFIX_1;
import static org.gym.crm.util.TestConstants.USERNAME_WITH_SUFFIX_2;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserProfileServiceImplTest {
    @Mock
    private TraineeDao traineeDao;
    @Mock
    private TrainerDao trainerDao;
    @InjectMocks
    private UserProfileServiceImpl userProfileService;

    @Test
    void generateUsername_shouldReturnBaseUsername_whenNoDuplicates() {
        when(traineeDao.findAll()).thenReturn(List.of());
        when(trainerDao.findAll()).thenReturn(List.of());

        String actual = userProfileService.generateUsername(FIRST_NAME, LAST_NAME);

        assertEquals(USERNAME, actual);
    }

    @Test
    void generateUsername_shouldReturnUsernameWithSuffix_whenDuplicateExists() {
        when(traineeDao.findAll()).thenReturn(List.of(buildTraineeWithUsername(USERNAME)));
        when(trainerDao.findAll()).thenReturn(List.of());

        String actual = userProfileService.generateUsername(FIRST_NAME, LAST_NAME);

        assertEquals(USERNAME_WITH_SUFFIX_1, actual);
    }

    @Test
    void generateUsername_shouldReturnUsernameWithSuffix2_whenTwoDuplicatesExist() {
        when(traineeDao.findAll()).thenReturn(List.of(buildTraineeWithUsername(USERNAME)));
        when(trainerDao.findAll()).thenReturn(List.of(buildTrainerWithUsername(USERNAME_WITH_SUFFIX_1)));

        String actual = userProfileService.generateUsername(FIRST_NAME, LAST_NAME);

        assertEquals(USERNAME_WITH_SUFFIX_2, actual);
    }

    @Test
    void generatePassword_shouldReturn10CharString() {
        String actual = userProfileService.generatePassword();

        assertNotNull(actual);
        assertEquals(PASSWORD_LENGTH, actual.length());
    }

    @Test
    void generatePassword_shouldContainOnlyAllowedChars() {
        String actual = userProfileService.generatePassword();

        assertTrue(actual.chars().allMatch(c -> ALLOWED_CHARS.indexOf(c) >= 0));
    }

    @Test
    void generatePassword_shouldReturnDifferentPasswordsEachTime() {
        String first = userProfileService.generatePassword();
        String second = userProfileService.generatePassword();

        assertNotEquals(first, second);
    }

    @Test
    void generateUsername_shouldNotReturnExistingUsername_whenGapExists() {
        when(traineeDao.findAll()).thenReturn(List.of(buildTraineeWithUsername(USERNAME)));
        when(trainerDao.findAll()).thenReturn(List.of(buildTrainerWithUsername(USERNAME_WITH_SUFFIX_2)));

        String actual = userProfileService.generateUsername(FIRST_NAME, LAST_NAME);

        assertEquals(USERNAME, actual); assertNotEquals(USERNAME_WITH_SUFFIX_2, actual);
    }

    private Trainee buildTraineeWithUsername(String username) {
        return Trainee.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .username(username)
                .build();
    }

    private Trainer buildTrainerWithUsername(String username) {
        return Trainer.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .username(username)
                .specialization(new TrainingType())
                .build();
    }
}
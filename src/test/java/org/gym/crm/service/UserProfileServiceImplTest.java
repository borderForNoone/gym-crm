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

        String result = userProfileService.generateUsername("John", "Smith");

        assertEquals("John.Smith", result);
    }

    @Test
    void generateUsername_shouldReturnUsernameWithSuffix_whenDuplicateExists() {
        Trainee existingTrainee = Trainee.builder()
                .firstName("John")
                .lastName("Smith")
                .username("John.Smith")
                .build();
        when(traineeDao.findAll()).thenReturn(List.of(existingTrainee));
        when(trainerDao.findAll()).thenReturn(List.of());

        String result = userProfileService.generateUsername("John", "Smith");

        assertEquals("John.Smith1", result);
    }

    @Test
    void generateUsername_shouldReturnUsernameWithSuffix2_whenTwoDuplicatesExist() {
        Trainee existingTrainee = Trainee.builder()
                .firstName("John")
                .lastName("Smith")
                .username("John.Smith")
                .build();
        TrainingType fitness = new TrainingType();
        Trainer existingTrainer = Trainer.builder()
                .firstName("John")
                .lastName("Smith")
                .username("John.Smith1")
                .specialization(fitness)
                .build();
        when(traineeDao.findAll()).thenReturn(List.of(existingTrainee));
        when(trainerDao.findAll()).thenReturn(List.of(existingTrainer));

        String result = userProfileService.generateUsername("John", "Smith");

        assertEquals("John.Smith2", result);
    }

    @Test
    void generatePassword_shouldReturn10CharString() {
        String password = userProfileService.generatePassword();

        assertNotNull(password);
        assertEquals(10, password.length());
    }

    @Test
    void generatePassword_shouldContainOnlyAllowedChars() {
        String allowed = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        String password = userProfileService.generatePassword();

        assertTrue(password.chars().allMatch(c -> allowed.indexOf(c) >= 0));
    }

    @Test
    void generatePassword_shouldReturnDifferentPasswordsEachTime() {
        String first = userProfileService.generatePassword();
        String second = userProfileService.generatePassword();

        assertNotEquals(first, second);
    }
}
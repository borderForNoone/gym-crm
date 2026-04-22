package org.gym.crm.service;

import org.gym.crm.dao.TraineeDao;
import org.gym.crm.model.Trainee;
import org.gym.crm.service.impl.TraineeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.gym.crm.util.TestConstants.FIRST_NAME;
import static org.gym.crm.util.TestConstants.ID;
import static org.gym.crm.util.TestConstants.LAST_NAME;
import static org.gym.crm.util.TestConstants.NON_EXISTING_ID;
import static org.gym.crm.util.TestConstants.PASSWORD;
import static org.gym.crm.util.TestConstants.USERNAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TraineeServiceImplTest {
    private final Trainee trainee = buildTrainee();

    @Mock
    private TraineeDao traineeDao;
    @Mock
    private UserProfileService userProfileService;
    @InjectMocks
    private TraineeServiceImpl traineeService;

    @Test
    void create_shouldGenerateUsernameAndPasswordAndSave() {
        when(userProfileService.generateUsername(FIRST_NAME, LAST_NAME)).thenReturn(USERNAME);
        when(userProfileService.generatePassword()).thenReturn(PASSWORD);

        Trainee expected = trainee.toBuilder().username(USERNAME).password(PASSWORD).build();

        when(traineeDao.save(ID, expected)).thenReturn(expected);

        Trainee actual = traineeService.create(ID, trainee);

        assertEquals(USERNAME, actual.getUsername());
        assertEquals(PASSWORD, actual.getPassword());
        verify(userProfileService).generateUsername(FIRST_NAME, LAST_NAME);
        verify(userProfileService).generatePassword();
        verify(traineeDao).save(ID, expected);
    }

    @Test
    void findById_shouldReturnTrainee_whenExists() {
        when(traineeDao.findById(ID)).thenReturn(Optional.of(trainee));

        Optional<Trainee> actual = traineeService.findById(ID);

        assertTrue(actual.isPresent());
        assertEquals(trainee, actual.get());
        verify(traineeDao).findById(ID);
    }

    @Test
    void findById_shouldReturnEmpty_whenNotExists() {
        when(traineeDao.findById(NON_EXISTING_ID)).thenReturn(Optional.empty());

        Optional<Trainee> actual = traineeService.findById(NON_EXISTING_ID);

        assertTrue(actual.isEmpty());
        verify(traineeDao).findById(NON_EXISTING_ID);
    }

    @Test
    void findAll_shouldReturnAllTrainees() {
        List<Trainee> expected = List.of(trainee);
        when(traineeDao.findAll()).thenReturn(expected);

        List<Trainee> actual = traineeService.findAll();

        assertEquals(expected.size(), actual.size());
        assertEquals(expected.getFirst(), actual.getFirst());
        verify(traineeDao).findAll();
    }

    @Test
    void update_shouldUpdateAndReturnTrainee() {
        when(traineeDao.update(ID, trainee)).thenReturn(trainee);

        Trainee actual = traineeService.update(ID, trainee);

        assertEquals(trainee, actual);
        verify(traineeDao).update(ID, trainee);
    }

    @Test
    void delete_shouldCallDaoDelete() {
        traineeService.delete(ID);

        verify(traineeDao).delete(ID);
    }

    private Trainee buildTrainee() {
        return Trainee.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .isActive(true)
                .build();
    }
}
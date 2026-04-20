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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TraineeServiceImplTest {

    @Mock
    private TraineeDao traineeDao;

    @Mock
    private UserProfileService userProfileService;

    @InjectMocks
    private TraineeServiceImpl traineeService;

    private Trainee trainee;

    @BeforeEach
    void setUp() {
        trainee = Trainee.builder()
                .firstName("John")
                .lastName("Smith")
                .isActive(true)
                .build();
    }

    @Test
    void create_shouldGenerateUsernameAndPasswordAndSave() {
        when(userProfileService.generateUsername("John", "Smith")).thenReturn("John.Smith");
        when(userProfileService.generatePassword()).thenReturn("pass123456");

        Trainee expected = trainee.toBuilder()
                .username("John.Smith")
                .password("pass123456")
                .build();
        when(traineeDao.save(1L, expected)).thenReturn(expected);

        Trainee result = traineeService.create(1L, trainee);

        assertEquals("John.Smith", result.getUsername());
        assertEquals("pass123456", result.getPassword());
        verify(userProfileService).generateUsername("John", "Smith");
        verify(userProfileService).generatePassword();
        verify(traineeDao).save(1L, expected);
    }

    @Test
    void findById_shouldReturnTrainee_whenExists() {
        when(traineeDao.findById(1L)).thenReturn(Optional.of(trainee));

        Optional<Trainee> result = traineeService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(trainee, result.get());
        verify(traineeDao).findById(1L);
    }

    @Test
    void findById_shouldReturnEmpty_whenNotExists() {
        when(traineeDao.findById(1L)).thenReturn(Optional.empty());

        Optional<Trainee> result = traineeService.findById(1L);

        assertTrue(result.isEmpty());
        verify(traineeDao).findById(1L);
    }

    @Test
    void findAll_shouldReturnAllTrainees() {
        List<Trainee> trainees = List.of(trainee);
        when(traineeDao.findAll()).thenReturn(trainees);

        List<Trainee> result = traineeService.findAll();

        assertEquals(1, result.size());
        assertEquals(trainee, result.get(0));
        verify(traineeDao).findAll();
    }

    @Test
    void update_shouldUpdateAndReturnTrainee() {
        when(traineeDao.update(1L, trainee)).thenReturn(trainee);

        Trainee result = traineeService.update(1L, trainee);

        assertEquals(trainee, result);
        verify(traineeDao).update(1L, trainee);
    }

    @Test
    void delete_shouldCallDaoDelete() {
        traineeService.delete(1L);

        verify(traineeDao).delete(1L);
    }
}

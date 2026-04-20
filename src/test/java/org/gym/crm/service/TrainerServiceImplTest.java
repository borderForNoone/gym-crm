package org.gym.crm.service;

import org.gym.crm.dao.TrainerDao;
import org.gym.crm.model.Trainer;
import org.gym.crm.model.TrainingType;
import org.gym.crm.service.impl.TrainerServiceImpl;
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
class TrainerServiceImplTest {

    @Mock
    private TrainerDao trainerDao;

    @Mock
    private UserProfileService userProfileService;

    @InjectMocks
    private TrainerServiceImpl trainerService;

    private Trainer trainer;

    @BeforeEach
    void setUp() {
        TrainingType fitness = new TrainingType();
        fitness.setTrainingTypeName("FITNESS");

        trainer = Trainer.builder()
                .firstName("Jane")
                .lastName("Doe")
                .isActive(true)
                .specialization(fitness)
                .build();
    }

    @Test
    void create_shouldGenerateUsernameAndPasswordAndSave() {
        when(userProfileService.generateUsername("Jane", "Doe")).thenReturn("Jane.Doe");
        when(userProfileService.generatePassword()).thenReturn("pass123456");
        Trainer expected = trainer.toBuilder()
                .username("Jane.Doe")
                .password("pass123456")
                .build();
        when(trainerDao.save(1L, expected)).thenReturn(expected);

        Trainer result = trainerService.create(1L, trainer);

        assertEquals("Jane.Doe", result.getUsername());
        assertEquals("pass123456", result.getPassword());
        verify(userProfileService).generateUsername("Jane", "Doe");
        verify(userProfileService).generatePassword();
        verify(trainerDao).save(1L, expected);
    }

    @Test
    void findById_shouldReturnTrainer_whenExists() {
        when(trainerDao.findById(1L)).thenReturn(Optional.of(trainer));

        Optional<Trainer> result = trainerService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(trainer, result.get());
        verify(trainerDao).findById(1L);
    }

    @Test
    void findById_shouldReturnEmpty_whenNotExists() {
        when(trainerDao.findById(1L)).thenReturn(Optional.empty());

        Optional<Trainer> result = trainerService.findById(1L);

        assertTrue(result.isEmpty());
        verify(trainerDao).findById(1L);
    }

    @Test
    void findAll_shouldReturnAllTrainers() {
        List<Trainer> trainers = List.of(trainer);
        when(trainerDao.findAll()).thenReturn(trainers);

        List<Trainer> result = trainerService.findAll();

        assertEquals(1, result.size());
        assertEquals(trainer, result.get(0));
        verify(trainerDao).findAll();
    }

    @Test
    void update_shouldUpdateAndReturnTrainer() {
        when(trainerDao.update(1L, trainer)).thenReturn(trainer);

        Trainer result = trainerService.update(1L, trainer);

        assertEquals(trainer, result);
        verify(trainerDao).update(1L, trainer);
    }
}
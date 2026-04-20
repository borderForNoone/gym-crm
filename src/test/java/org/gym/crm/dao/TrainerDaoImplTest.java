package org.gym.crm.dao;

import org.gym.crm.dao.impl.TrainerDaoImpl;
import org.gym.crm.model.Trainer;
import org.gym.crm.model.TrainingType;
import org.gym.crm.storage.Storage;
import org.gym.crm.storage.TrainerStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrainerDaoImplTest {

    @Mock
    private Storage storage;

    @Mock
    private TrainerStorage trainerStorage;

    private TrainerDaoImpl trainerDao;
    private Map<Long, Trainer> trainers;
    private Trainer trainer;

    @BeforeEach
    void setUp() {
        trainers = new HashMap<>();

        TrainingType fitness = new TrainingType();
        fitness.setTrainingTypeName("FITNESS");

        trainer = Trainer.builder()
                .firstName("Jane")
                .lastName("Doe")
                .username("Jane.Doe")
                .isActive(true)
                .specialization(fitness)
                .build();

        when(storage.getTrainerStorage()).thenReturn(trainerStorage);
        when(trainerStorage.getTrainers()).thenReturn(trainers);

        trainerDao = new TrainerDaoImpl(storage);
    }

    @Test
    void save_shouldPutTrainerInStorageAndReturn() {
        Trainer result = trainerDao.save(1L, trainer);

        assertEquals(trainer, result);
        assertEquals(trainer, trainers.get(1L));
    }

    @Test
    void findById_shouldReturnTrainer_whenExists() {
        trainers.put(1L, trainer);

        Optional<Trainer> result = trainerDao.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(trainer, result.get());
    }

    @Test
    void findById_shouldReturnEmpty_whenNotExists() {
        Optional<Trainer> result = trainerDao.findById(99L);

        assertTrue(result.isEmpty());
    }

    @Test
    void findAll_shouldReturnAllTrainers() {
        trainers.put(1L, trainer);

        List<Trainer> result = trainerDao.findAll();

        assertEquals(1, result.size());
        assertTrue(result.contains(trainer));
    }

    @Test
    void findAll_shouldReturnEmptyList_whenNoTrainers() {
        List<Trainer> result = trainerDao.findAll();

        assertTrue(result.isEmpty());
    }

    @Test
    void update_shouldUpdateTrainer_whenExists() {
        trainers.put(1L, trainer);
        Trainer updated = trainer.toBuilder().firstName("John").build();

        Trainer result = trainerDao.update(1L, updated);

        assertEquals(updated, result);
        assertEquals(updated, trainers.get(1L));
    }

    @Test
    void update_shouldThrowException_whenNotExists() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> trainerDao.update(99L, trainer)
        );

        assertEquals("Trainer not found with id: 99", ex.getMessage());
    }
}
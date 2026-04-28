package org.gym.crm.dao;

import org.gym.crm.dao.impl.TrainerDaoImpl;
import org.gym.crm.model.Trainer;
import org.gym.crm.model.TrainingType;
import org.gym.crm.model.User;
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

import static org.gym.crm.util.TestConstants.FITNESS;
import static org.gym.crm.util.TestConstants.ID;
import static org.gym.crm.util.TestConstants.NON_EXISTING_ID;
import static org.gym.crm.util.TestConstants.NOT_FOUND_MESSAGE;
import static org.gym.crm.util.TestConstants.TRAINER_FIRST_NAME;
import static org.gym.crm.util.TestConstants.TRAINER_LAST_NAME;
import static org.gym.crm.util.TestConstants.TRAINER_USERNAME;
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

    private TrainerDao dao;
    private Map<Long, Trainer> trainers;
    private Trainer trainer;

    @BeforeEach
    void setUp() {
        trainers = new HashMap<>();
        trainer = buildTrainer();

        when(storage.getTrainerStorage()).thenReturn(trainerStorage);
        when(trainerStorage.getTrainers()).thenReturn(trainers);

        dao = new TrainerDaoImpl(storage);
    }

    @Test
    void save_shouldPutTrainerInStorageAndReturn() {
        Trainer actual = dao.save(trainer);

        assertEquals(trainer, actual);
        assertEquals(trainer, trainers.get(ID));
    }

    @Test
    void findById_shouldReturnTrainer_whenExists() {
        trainers.put(ID, trainer);

        Optional<Trainer> actual = dao.findById(ID);

        assertTrue(actual.isPresent());
        assertEquals(trainer, actual.get());
    }

    @Test
    void findById_shouldReturnEmpty_whenNotExists() {
        Optional<Trainer> actual = dao.findById(NON_EXISTING_ID);

        assertTrue(actual.isEmpty());
    }

    @Test
    void findAll_shouldReturnAllTrainers() {
        trainers.put(ID, trainer);

        List<Trainer> actual = dao.findAll();

        assertEquals(1, actual.size());
        assertTrue(actual.contains(trainer));
    }

    @Test
    void findAll_shouldReturnEmptyList_whenNoTrainers() {
        List<Trainer> actual = dao.findAll();

        assertTrue(actual.isEmpty());
    }

    @Test
    void update_shouldUpdateTrainer_whenExists() {
        trainers.put(ID, trainer);

        Trainer expected = trainer.toBuilder()
                .user(trainer.getUser().toBuilder()
                        .firstName("John")
                        .build())
                .build();

        Trainer actual = dao.update(ID, expected);

        assertEquals(expected, actual);
        assertEquals(expected, trainers.get(ID));
    }

    @Test
    void update_shouldThrowException_whenNotExists() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> dao.update(NON_EXISTING_ID, trainer));

        assertEquals(NOT_FOUND_MESSAGE + NON_EXISTING_ID, exception.getMessage());
    }

    private TrainingType fitnessType() {
        TrainingType fitness = new TrainingType();
        fitness.setTrainingTypeName(FITNESS);

        return fitness;
    }

    private Trainer buildTrainer() {
        return Trainer.builder()
                .user(User.builder()
                        .firstName(TRAINER_FIRST_NAME)
                        .lastName(TRAINER_LAST_NAME)
                        .username(TRAINER_USERNAME)
                        .isActive(true)
                        .build())
                .specialization(fitnessType())
                .build();
    }
}
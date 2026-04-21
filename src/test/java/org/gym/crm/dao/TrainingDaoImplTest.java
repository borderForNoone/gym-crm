package org.gym.crm.dao;

import org.gym.crm.dao.impl.TrainingDaoImpl;
import org.gym.crm.model.Training;
import org.gym.crm.model.TrainingType;
import org.gym.crm.storage.Storage;
import org.gym.crm.storage.TrainingStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.gym.crm.util.TestConstants.DURATION;
import static org.gym.crm.util.TestConstants.FITNESS;
import static org.gym.crm.util.TestConstants.ID;
import static org.gym.crm.util.TestConstants.NON_EXISTING_ID;
import static org.gym.crm.util.TestConstants.TRAINING_DATE;
import static org.gym.crm.util.TestConstants.TRAINING_NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrainingDaoImplTest {
    @Mock
    private Storage storage;
    @Mock
    private TrainingStorage trainingStorage;

    private TrainingDaoImpl dao;
    private Map<Long, Training> trainings;
    private Training training;

    @BeforeEach
    void setUp() {
        trainings = new HashMap<>();
        training = buildTraining();

        when(storage.getTrainingStorage()).thenReturn(trainingStorage);
        when(trainingStorage.getTrainings()).thenReturn(trainings);

        dao = new TrainingDaoImpl(storage);
    }

    @Test
    void save_shouldPutTrainingInStorageAndReturn() {
        Training actual = dao.save(training);

        assertEquals(training, actual);
        assertEquals(training, trainings.get(ID));
    }

    @Test
    void findById_shouldReturnTraining_whenExists() {
        trainings.put(ID, training);

        Optional<Training> actual = dao.findById(ID);

        assertTrue(actual.isPresent());
        assertEquals(training, actual.get());
    }

    @Test
    void findById_shouldReturnEmpty_whenNotExists() {
        Optional<Training> actual = dao.findById(NON_EXISTING_ID);

        assertTrue(actual.isEmpty());
    }

    @Test
    void findAll_shouldReturnAllTrainings() {
        trainings.put(ID, training);

        List<Training> actual = dao.findAll();

        assertEquals(1, actual.size());
        assertTrue(actual.contains(training));
    }

    @Test
    void findAll_shouldReturnEmptyList_whenNoTrainings() {
        List<Training> actual = dao.findAll();

        assertTrue(actual.isEmpty());
    }

    private TrainingType fitnessType() {
        TrainingType fitness = new TrainingType();
        fitness.setTrainingTypeName(FITNESS);
        return fitness;
    }

    private Training buildTraining() {
        return Training.builder()
                .id(ID)
                .traineeId(ID)
                .trainerId(ID)
                .trainingName(TRAINING_NAME)
                .trainingType(fitnessType())
                .trainingDate(TRAINING_DATE)
                .trainingDuration(DURATION)
                .build();
    }
}
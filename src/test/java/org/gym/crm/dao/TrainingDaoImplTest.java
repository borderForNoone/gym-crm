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

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrainingDaoImplTest {

    @Mock
    private Storage storage;

    @Mock
    private TrainingStorage trainingStorage;

    private TrainingDaoImpl trainingDao;
    private Map<Long, Training> trainings;
    private Training training;

    @BeforeEach
    void setUp() {
        trainings = new HashMap<>();

        TrainingType fitness = new TrainingType();
        fitness.setTrainingTypeName("FITNESS");

        training = Training.builder()
                .id(1L)
                .traineeId(1L)
                .trainerId(1L)
                .trainingName("Morning Workout")
                .trainingType(fitness)
                .trainingDate(LocalDate.of(2024, 1, 1))
                .trainingDuration(60)
                .build();

        when(storage.getTrainingStorage()).thenReturn(trainingStorage);
        when(trainingStorage.getTrainings()).thenReturn(trainings);

        trainingDao = new TrainingDaoImpl(storage);
    }

    @Test
    void save_shouldPutTrainingInStorageAndReturn() {
        Training result = trainingDao.save(training);

        assertEquals(training, result);
        assertEquals(training, trainings.get(1L));
    }

    @Test
    void findById_shouldReturnTraining_whenExists() {
        trainings.put(1L, training);

        Optional<Training> result = trainingDao.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(training, result.get());
    }

    @Test
    void findById_shouldReturnEmpty_whenNotExists() {
        Optional<Training> result = trainingDao.findById(99L);

        assertTrue(result.isEmpty());
    }

    @Test
    void findAll_shouldReturnAllTrainings() {
        trainings.put(1L, training);

        List<Training> result = trainingDao.findAll();

        assertEquals(1, result.size());
        assertTrue(result.contains(training));
    }

    @Test
    void findAll_shouldReturnEmptyList_whenNoTrainings() {
        List<Training> result = trainingDao.findAll();

        assertTrue(result.isEmpty());
    }
}
package org.gym.crm.service;

import org.gym.crm.dao.TrainingDao;
import org.gym.crm.model.Training;
import org.gym.crm.model.TrainingType;
import org.gym.crm.service.impl.TrainingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrainingServiceImplTest {

    @Mock
    private TrainingDao trainingDao;

    @InjectMocks
    private TrainingServiceImpl trainingService;

    private Training training;

    @BeforeEach
    void setUp() {
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
    }

    @Test
    void create_shouldSaveAndReturnTraining() {
        when(trainingDao.save(training)).thenReturn(training);

        Training result = trainingService.create(training);

        assertEquals(training, result);
        verify(trainingDao).save(training);
    }

    @Test
    void findById_shouldReturnTraining_whenExists() {
        when(trainingDao.findById(1L)).thenReturn(Optional.of(training));

        Optional<Training> result = trainingService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(training, result.get());
        verify(trainingDao).findById(1L);
    }

    @Test
    void findById_shouldReturnEmpty_whenNotExists() {
        when(trainingDao.findById(1L)).thenReturn(Optional.empty());

        Optional<Training> result = trainingService.findById(1L);

        assertTrue(result.isEmpty());
        verify(trainingDao).findById(1L);
    }

    @Test
    void findAll_shouldReturnAllTrainings() {
        List<Training> trainings = List.of(training);
        when(trainingDao.findAll()).thenReturn(trainings);

        List<Training> result = trainingService.findAll();

        assertEquals(1, result.size());
        assertEquals(training, result.get(0));
        verify(trainingDao).findAll();
    }
}


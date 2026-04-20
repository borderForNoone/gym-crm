package org.gym.crm.storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class StorageTest {

    @Mock
    private TraineeStorage traineeStorage;

    @Mock
    private TrainerStorage trainerStorage;

    @Mock
    private TrainingStorage trainingStorage;

    private Storage storage;

    @BeforeEach
    void setUp() {
        storage = new Storage(traineeStorage, trainerStorage, trainingStorage);
    }

    @Test
    void getTraineeStorage_shouldReturnTraineeStorage() {
        TraineeStorage result = storage.getTraineeStorage();

        assertEquals(traineeStorage, result);
    }

    @Test
    void getTrainerStorage_shouldReturnTrainerStorage() {
        TrainerStorage result = storage.getTrainerStorage();

        assertEquals(trainerStorage, result);
    }

    @Test
    void getTrainingStorage_shouldReturnTrainingStorage() {
        TrainingStorage result = storage.getTrainingStorage();

        assertEquals(trainingStorage, result);
    }
}

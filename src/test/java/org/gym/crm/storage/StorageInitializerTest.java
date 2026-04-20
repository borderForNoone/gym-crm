package org.gym.crm.storage;

import org.gym.crm.model.Trainee;
import org.gym.crm.model.Trainer;
import org.gym.crm.model.Training;
import org.gym.crm.storage.dataReader.CsvDataReader;
import org.gym.crm.storage.parser.CsvParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StorageInitializerTest {

    @Mock
    private CsvDataReader csvDataReader;

    @Mock
    private CsvParser csvParser;

    @Mock
    private Storage storage;

    @Mock
    private TraineeStorage traineeStorage;

    @Mock
    private TrainerStorage trainerStorage;

    @Mock
    private TrainingStorage trainingStorage;

    @InjectMocks
    private StorageInitializer storageInitializer;

    private Map<Long, Trainee> trainees;
    private Map<Long, Trainer> trainers;
    private Map<Long, Training> trainings;

    private Trainee trainee;
    private Trainer trainer;
    private Training training;

    @BeforeEach
    void setUp() {
        trainees = new HashMap<>();
        trainers = new HashMap<>();
        trainings = new HashMap<>();

        trainee = Trainee.builder()
                .firstName("John")
                .lastName("Smith")
                .username("John.Smith")
                .isActive(true)
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .address("123 Main St")
                .build();

        trainer = Trainer.builder()
                .firstName("Jane")
                .lastName("Doe")
                .username("Jane.Doe")
                .isActive(true)
                .build();

        training = Training.builder()
                .id(1L)
                .traineeId(1L)
                .trainerId(1L)
                .trainingName("Morning Workout")
                .trainingDate(LocalDate.of(2024, 1, 1))
                .trainingDuration(60)
                .build();

        storageInitializer.setTraineesFilePath("trainees.csv");
        storageInitializer.setTrainersFilePath("trainers.csv");
        storageInitializer.setTrainingsFilePath("trainings.csv");

        when(storage.getTraineeStorage()).thenReturn(traineeStorage);
        when(storage.getTrainerStorage()).thenReturn(trainerStorage);
        when(storage.getTrainingStorage()).thenReturn(trainingStorage);

        when(traineeStorage.getTrainees()).thenReturn(trainees);
        when(trainerStorage.getTrainers()).thenReturn(trainers);
        when(trainingStorage.getTrainings()).thenReturn(trainings);
    }

    @Test
    void init_shouldLoadTrainees() {
        List<String[]> traineeData = new ArrayList<>();
        traineeData.add(new String[]{"1", "John", "Smith", "John.Smith", "pass123", "true", "1990-01-01", "123 Main St"});

        when(csvDataReader.readData("trainees.csv")).thenReturn(traineeData);
        when(csvDataReader.readData("trainers.csv")).thenReturn(new ArrayList<>());
        when(csvDataReader.readData("trainings.csv")).thenReturn(new ArrayList<>());
        when(csvParser.parseTrainee(traineeData.getFirst())).thenReturn(trainee);

        storageInitializer.init();

        assertEquals(1, trainees.size());
        assertEquals(trainee, trainees.get(1L));
        verify(csvParser).parseTrainee(traineeData.getFirst());
    }

    @Test
    void init_shouldLoadTrainers() {
        List<String[]> trainerData = new ArrayList<>();
        trainerData.add(new String[]{"1", "Jane", "Doe", "Jane.Doe", "pass123", "true", "FITNESS"});

        when(csvDataReader.readData("trainees.csv")).thenReturn(new ArrayList<>());
        when(csvDataReader.readData("trainers.csv")).thenReturn(trainerData);
        when(csvDataReader.readData("trainings.csv")).thenReturn(new ArrayList<>());
        when(csvParser.parseTrainer(trainerData.getFirst())).thenReturn(trainer);

        storageInitializer.init();

        assertEquals(1, trainers.size());
        assertEquals(trainer, trainers.get(1L));
        verify(csvParser).parseTrainer(trainerData.getFirst());
    }

    @Test
    void init_shouldLoadTrainings() {
        List<String[]> trainingData = new ArrayList<>();
        trainingData.add(new String[]{"1", "1", "1", "Morning Workout", "FITNESS", "2024-01-01", "60"});

        when(csvDataReader.readData("trainees.csv")).thenReturn(new ArrayList<>());
        when(csvDataReader.readData("trainers.csv")).thenReturn(new ArrayList<>());
        when(csvDataReader.readData("trainings.csv")).thenReturn(trainingData);
        when(csvParser.parseTraining(trainingData.getFirst())).thenReturn(training);

        storageInitializer.init();

        assertEquals(1, trainings.size());
        assertEquals(training, trainings.get(1L));
        verify(csvParser).parseTraining(trainingData.getFirst());
    }

    @Test
    void init_shouldHandleEmptyFiles() {
        when(csvDataReader.readData("trainees.csv")).thenReturn(List.of());
        when(csvDataReader.readData("trainers.csv")).thenReturn(List.of());
        when(csvDataReader.readData("trainings.csv")).thenReturn(List.of());

        storageInitializer.init();

        assertTrue(trainees.isEmpty());
        assertTrue(trainers.isEmpty());
        assertTrue(trainings.isEmpty());
    }

    @Test
    void init_shouldLoadMultipleTrainees() {
        Trainee secondTrainee = Trainee.builder()
                .firstName("Alice")
                .lastName("Brown")
                .username("Alice.Brown")
                .isActive(true)
                .build();

        String[] firstFields = {"1", "John", "Smith", "John.Smith", "pass123", "true", "1990-01-01", "123 Main St"};
        String[] secondFields = {"2", "Alice", "Brown", "Alice.Brown", "pass456", "true", "1995-05-05", "456 Oak Ave"};

        when(csvDataReader.readData("trainees.csv")).thenReturn(List.of(firstFields, secondFields));
        when(csvDataReader.readData("trainers.csv")).thenReturn(List.of());
        when(csvDataReader.readData("trainings.csv")).thenReturn(List.of());
        when(csvParser.parseTrainee(firstFields)).thenReturn(trainee);
        when(csvParser.parseTrainee(secondFields)).thenReturn(secondTrainee);

        storageInitializer.init();

        assertEquals(2, trainees.size());
        assertEquals(trainee, trainees.get(1L));
        assertEquals(secondTrainee, trainees.get(2L));
    }
}
package org.gym.crm.storage.parser;

import org.gym.crm.model.Trainee;
import org.gym.crm.model.Trainer;
import org.gym.crm.model.Training;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CsvParserTest {

    private CsvParser csvParser;

    @BeforeEach
    void setUp() {
        csvParser = new CsvParser();
    }

    @Test
    void parseTrainee_shouldParseAllFields() {
        String[] fields = {"1", "John", "Smith", "John.Smith", "pass123", "true", "1990-01-15", "123 Main St", "1"};

        Trainee result = csvParser.parseTrainee(fields);

        assertEquals("John", result.getFirstName());
        assertEquals("Smith", result.getLastName());
        assertEquals("John.Smith", result.getUsername());
        assertEquals("pass123", result.getPassword());
        assertTrue(result.isActive());
        assertEquals(LocalDate.of(1990, 1, 15), result.getDateOfBirth());
        assertEquals("123 Main St", result.getAddress());
        assertEquals(1L, result.getUserId());
    }

    @Test
    void parseTrainee_shouldSetDateOfBirthNull_whenBlank() {
        String[] fields = {"1", "John", "Smith", "John.Smith", "pass123", "true", "", "123 Main St", "1"};

        Trainee result = csvParser.parseTrainee(fields);

        assertNull(result.getDateOfBirth());
    }

    @Test
    void parseTrainee_shouldParseIsActiveFalse() {
        String[] fields = {"1", "John", "Smith", "John.Smith", "pass123", "false", "1990-01-15", "123 Main St", "1"};

        Trainee result = csvParser.parseTrainee(fields);

        assertFalse(result.isActive());
    }

    @Test
    void parseTrainer_shouldParseAllFields() {
        String[] fields = {"1", "Jane", "Doe", "Jane.Doe", "pass456", "true", "FITNESS", "2"};

        Trainer result = csvParser.parseTrainer(fields);

        assertEquals("Jane", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("Jane.Doe", result.getUsername());
        assertEquals("pass456", result.getPassword());
        assertTrue(result.isActive());
        assertEquals("FITNESS", result.getSpecialization().getTrainingTypeName());
        assertEquals(2L, result.getUserId());
    }

    @Test
    void parseTrainer_shouldParseIsActiveFalse() {
        String[] fields = {"1", "Jane", "Doe", "Jane.Doe", "pass456", "false", "FITNESS", "2"};

        Trainer result = csvParser.parseTrainer(fields);

        assertFalse(result.isActive());
    }

    @Test
    void parseTrainer_shouldParseSpecialization() {
        String[] fields = {"1", "Jane", "Doe", "Jane.Doe", "pass456", "true", "YOGA", "2"};

        Trainer result = csvParser.parseTrainer(fields);

        assertEquals("YOGA", result.getSpecialization().getTrainingTypeName());
    }

    @Test
    void parseTraining_shouldParseAllFields() {
        String[] fields = {"1", "2", "3", "Morning Workout", "FITNESS", "2024-03-15", "90"};

        Training result = csvParser.parseTraining(fields);

        assertEquals(1L, result.getId());
        assertEquals(2L, result.getTraineeId());
        assertEquals(3L, result.getTrainerId());
        assertEquals("Morning Workout", result.getTrainingName());
        assertEquals("FITNESS", result.getTrainingType().getTrainingTypeName());
        assertEquals(LocalDate.of(2024, 3, 15), result.getTrainingDate());
        assertEquals(90, result.getTrainingDuration());
    }

    @Test
    void parseTraining_shouldParseTrainingDuration() {
        String[] fields = {"1", "2", "3", "Evening Run", "CARDIO", "2024-06-01", "45"};

        Training result = csvParser.parseTraining(fields);

        assertEquals(45, result.getTrainingDuration());
    }

    @Test
    void parseTraining_shouldParseTrainingType() {
        String[] fields = {"1", "2", "3", "Yoga Session", "YOGA", "2024-06-01", "60"};

        Training result = csvParser.parseTraining(fields);

        assertEquals("YOGA", result.getTrainingType().getTrainingTypeName());
    }
}
package org.gym.crm.storage.dataReader;

import org.gym.crm.storage.exception.StorageException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CsvDataReaderTest {

    private CsvDataReader csvDataReader;
    private String testFilePath;

    @BeforeEach
    void setUp() throws URISyntaxException {
        csvDataReader = new CsvDataReader();
        testFilePath = Paths.get(
                Objects.requireNonNull(getClass().getClassLoader()
                                .getResource("test-trainees.csv"))
                        .toURI()
        ).toString();
    }

    @Test
    void readData_shouldSkipHeader() {
        List<String[]> result = csvDataReader.readData(testFilePath);

        assertEquals(2, result.size());
    }

    @Test
    void readData_shouldParseFirstRowCorrectly() {
        List<String[]> result = csvDataReader.readData(testFilePath);

        String[] firstRow = result.getFirst();
        assertEquals("1", firstRow[0]);
        assertEquals("John", firstRow[1]);
        assertEquals("Smith", firstRow[2]);
        assertEquals("John.Smith", firstRow[3]);
        assertEquals("testpass1", firstRow[4]);
        assertEquals("true", firstRow[5]);
        assertEquals("1990-01-15", firstRow[6]);
        assertEquals("123 Main St", firstRow[7]);
        assertEquals("1", firstRow[8]);
    }

    @Test
    void readData_shouldParseSecondRowCorrectly() {
        List<String[]> result = csvDataReader.readData(testFilePath);

        String[] secondRow = result.get(1);
        assertEquals("2", secondRow[0]);
        assertEquals("Jane", secondRow[1]);
        assertEquals("Doe", secondRow[2]);
        assertEquals("Jane.Doe", secondRow[3]);
        assertEquals("testpass2", secondRow[4]);
        assertEquals("false", secondRow[5]);
        assertEquals("", secondRow[6]);
        assertEquals("456 Oak Ave", secondRow[7]);
        assertEquals("2", secondRow[8]);
    }

    @Test
    void readData_shouldHandleBlankField() {
        List<String[]> result = csvDataReader.readData(testFilePath);

        String[] secondRow = result.get(1);
        assertEquals("", secondRow[6]);
    }

    @Test
    void readData_shouldThrowStorageException_whenFileNotFound() {
        StorageException ex = assertThrows(
                StorageException.class,
                () -> csvDataReader.readData("non-existent-file.csv")
        );

        assertEquals("Failed to read CSV file: non-existent-file.csv", ex.getMessage());
    }

    @Test
    void readData_shouldReturnEmptyList_whenFileHasOnlyHeader() throws URISyntaxException {
        String headerOnlyFilePath = Paths.get(
                Objects.requireNonNull(
                        getClass().getClassLoader().getResource("test-header-only.csv")
                ).toURI()
        ).toString();

        List<String[]> result = csvDataReader.readData(headerOnlyFilePath);

        assertTrue(result.isEmpty());
    }
}
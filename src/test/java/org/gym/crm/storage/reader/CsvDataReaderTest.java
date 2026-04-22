package org.gym.crm.storage.reader;

import org.gym.crm.storage.exception.StorageException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

import static org.gym.crm.util.TestConstants.ADDRESS;
import static org.gym.crm.util.TestConstants.FIRST_NAME;
import static org.gym.crm.util.TestConstants.ID;
import static org.gym.crm.util.TestConstants.LAST_NAME;
import static org.gym.crm.util.TestConstants.PASSWORD;
import static org.gym.crm.util.TestConstants.SECOND_ADDRESS;
import static org.gym.crm.util.TestConstants.SECOND_FIRST_NAME;
import static org.gym.crm.util.TestConstants.SECOND_ID;
import static org.gym.crm.util.TestConstants.SECOND_LAST_NAME;
import static org.gym.crm.util.TestConstants.SECOND_PASSWORD;
import static org.gym.crm.util.TestConstants.SECOND_USERNAME;
import static org.gym.crm.util.TestConstants.USERNAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CsvDataReaderTest {
    private CsvDataReader reader;
    private String testFilePath;

    @BeforeEach
    void setUp() throws URISyntaxException {
        reader = new CsvDataReader();
        testFilePath = getFilePath("test-trainees.csv");
    }

    @Test
    void readData_shouldSkipHeader() {
        List<String[]> result = reader.readData(testFilePath);

        assertEquals(2, result.size());
    }

    @Test
    void readData_shouldParseFirstRowCorrectly() {
        List<String[]> result = reader.readData(testFilePath);

        String[] firstRow = result.getFirst();
        assertEquals("1", firstRow[0]);
        assertEquals(FIRST_NAME, firstRow[1]);
        assertEquals(LAST_NAME, firstRow[2]);
        assertEquals(USERNAME, firstRow[3]);
        assertEquals(PASSWORD, firstRow[4]);
        assertEquals("true", firstRow[5]);
        assertEquals("1990-01-15", firstRow[6]);
        assertEquals(ADDRESS, firstRow[7]);
        assertEquals(ID.toString(), firstRow[8]);
    }

    @Test
    void readData_shouldParseSecondRowCorrectly() {
        List<String[]> result = reader.readData(testFilePath);

        String[] secondRow = result.get(1);

        assertEquals(SECOND_ID.toString(), secondRow[0]);
        assertEquals(SECOND_FIRST_NAME, secondRow[1]);
        assertEquals(SECOND_LAST_NAME, secondRow[2]);
        assertEquals(SECOND_USERNAME, secondRow[3]);
        assertEquals(SECOND_PASSWORD, secondRow[4]);
        assertEquals("false", secondRow[5]);
        assertEquals("", secondRow[6]);
        assertEquals(SECOND_ADDRESS, secondRow[7]);
        assertEquals(SECOND_ID.toString(), secondRow[8]);
    }

    @Test
    void readData_shouldHandleBlankField() {
        List<String[]> result = reader.readData(testFilePath);

        String[] secondRow = result.get(1);
        assertEquals("", secondRow[6]);
    }

    @Test
    void readData_shouldThrowStorageException_whenFileNotFound() {
        StorageException ex = assertThrows(StorageException.class,
                () -> reader.readData("non-existent-file.csv")
        );

        assertEquals("Failed to read CSV file: non-existent-file.csv", ex.getMessage());
    }

    @Test
    void readData_shouldReturnEmptyList_whenFileHasOnlyHeader() throws URISyntaxException {
        String headerOnlyFilePath = getFilePath("test-header-only.csv");

        List<String[]> result = reader.readData(headerOnlyFilePath);

        assertTrue(result.isEmpty());
    }

    private String getFilePath(String fileName) throws URISyntaxException {
        URL resource = getClass().getClassLoader().getResource(fileName);

        return Paths.get(Objects.requireNonNull(resource).toURI()).toString();
    }
}
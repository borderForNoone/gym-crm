package org.gym.crm.storage.reader;

import org.gym.crm.storage.exception.StorageException;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvDataReader {
    public List<String[]> readData(String filePath) {
        List<String[]> records = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean header = true;

            while ((line = br.readLine()) != null) {
                if (header) {
                    header = false;
                    continue;
                }
                records.add(line.split(",", -1));
            }
        } catch (IOException e) {
            throw new StorageException("Failed to read CSV file: " + filePath, e);
        }

        return records;
    }
}

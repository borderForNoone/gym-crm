package org.gym.crm.storage.dataReader;

import org.gym.crm.storage.dataReader.impl.CsvDataReader;
import org.gym.crm.storage.dataReader.impl.XmlDataReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataReaderFactory {
    private final CsvDataReader csvDataReader;
    private final XmlDataReader xmlDataReader;

    @Autowired
    public DataReaderFactory(CsvDataReader csvDataReader,
                             XmlDataReader xmlDataReader) {
        this.csvDataReader = csvDataReader;
        this.xmlDataReader = xmlDataReader;
    }

    public DataReader getReader(String filePath) {
        String extension = getFileExtension(filePath).toLowerCase();

        return switch (extension) {
            case "csv" -> csvDataReader;
            case "xml" -> xmlDataReader;
            default -> throw new IllegalArgumentException("Unsupported file format: " + extension);
        };
    }

    private String getFileExtension(String filePath) {
        return filePath.substring(filePath.lastIndexOf('.') + 1);
    }
}

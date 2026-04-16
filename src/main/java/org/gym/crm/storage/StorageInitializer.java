package org.gym.crm.storage;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.gym.crm.storage.dataReader.CsvDataReader;
import org.gym.crm.storage.parser.CsvParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class StorageInitializer {
    private final CsvDataReader csvDataReader;
    private final CsvParser csvParser;
    private final Storage storage;

    @Setter
    @Value("${storage.data.trainees}")
    private String traineesFilePath;

    @Setter
    @Value("${storage.data.trainers}")
    private String trainersFilePath;

    @Setter
    @Value("${storage.data.trainings}")
    private String trainingsFilePath;

    @PostConstruct
    public void init() {
        loadData(traineesFilePath, csvParser::parseTrainee,
                storage.getTraineeStorage().getTrainees());

        loadData(trainersFilePath, csvParser::parseTrainer,
                storage.getTrainerStorage().getTrainers());

        loadData(trainingsFilePath, csvParser::parseTraining,
                storage.getTrainingStorage().getTrainings());
    }

    private <T> void loadData(String filePath, Function<String[], T> parser, Map<Long, T> storageMap) {
        csvDataReader.readData(filePath).forEach(fields ->
                storageMap.put(Long.parseLong(fields[0]), parser.apply(fields))
        );
    }
}

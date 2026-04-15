package org.gym.crm.storage;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.gym.crm.storage.dataReader.DataReaderFactory;
import org.gym.crm.storage.parser.CsvParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class StorageInitializer {
    private final DataReaderFactory dataReaderFactory;
    private final CsvParser csvParser;
    private final TraineeStorage traineeStorage;
    private final TrainerStorage trainerStorage;
    private final TrainingStorage trainingStorage;

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
        loadData(traineesFilePath, csvParser::parseTrainee, traineeStorage.getTrainees());
        loadData(trainersFilePath, csvParser::parseTrainer, trainerStorage.getTrainers());
        loadData(trainingsFilePath, csvParser::parseTraining, trainingStorage.getTrainings());
    }

    private <T> void loadData(String filePath, Function<String[], T> parser, Map<Long, T> storage) {
        var reader = dataReaderFactory.getReader(filePath);
        reader.readData(filePath).forEach(fields ->
                storage.put(Long.parseLong(fields[0]), parser.apply(fields))
        );
    }
}

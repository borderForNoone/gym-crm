package org.gym.crm.storage;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.gym.crm.model.Trainee;
import org.gym.crm.model.Trainer;
import org.gym.crm.model.Training;
import org.gym.crm.model.TrainingType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryStorage {
    @Getter
    private final Map<Long, Trainee> trainees = new HashMap<>();
    @Getter
    private final Map<Long, Trainer> trainers = new HashMap<>();
    @Getter
    private final Map<Long, Training> trainings = new HashMap<>();

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
        loadTrainees(traineesFilePath);
        loadTrainers(trainersFilePath);
        loadTrainings(trainingsFilePath);
    }

    private void loadTrainees(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            boolean header = true;
            while ((line = br.readLine()) != null) {
                if (header) {
                    header = false;
                    continue;
                }
                String[] f = line.split(",", -1);
                Trainee t = Trainee.builder()
                        .firstName(f[1])
                        .lastName(f[2])
                        .username(f[3])
                        .password(f[4])
                        .isActive(Boolean.parseBoolean(f[5]))
                        .dateOfBirth(f[6].isBlank() ? null : LocalDate.parse(f[6]))
                        .address(f[7])
                        .userId(Long.parseLong(f[8]))
                        .build();
                trainees.put(Long.parseLong(f[0]), t);
            }
        } catch (IOException e) {
            System.out.println("Could not load trainees from '" + path + "': " + e.getMessage());
        }
    }

    private void loadTrainers(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            boolean header = true;
            while ((line = br.readLine()) != null) {
                if (header) {
                    header = false;
                    continue;
                }
                String[] f = line.split(",", -1);
                TrainingType spec = new TrainingType();
                spec.setTrainingTypeName(f[6]);
                Trainer t = Trainer.builder()
                        .firstName(f[1])
                        .lastName(f[2])
                        .username(f[3])
                        .password(f[4])
                        .isActive(Boolean.parseBoolean(f[5]))
                        .specialization(spec)
                        .userId(Long.parseLong(f[7]))
                        .build();
                trainers.put(Long.parseLong(f[0]), t);
            }
        } catch (IOException e) {
            System.out.println("Could not load trainers from '" + path + "': " + e.getMessage());
        }
    }

    private void loadTrainings(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            boolean header = true;
            while ((line = br.readLine()) != null) {
                if (header) {
                    header = false;
                    continue;
                }
                String[] f = line.split(",", -1);
                TrainingType tt = new TrainingType();
                tt.setTrainingTypeName(f[4]);
                Training t = Training.builder()
                        .id(Long.parseLong(f[0]))
                        .traineeId(Long.parseLong(f[1]))
                        .trainerId(Long.parseLong(f[2]))
                        .trainingName(f[3])
                        .trainingType(tt)
                        .trainingDate(LocalDate.parse(f[5]))
                        .trainingDuration(Integer.parseInt(f[6]))
                        .build();
                trainings.put(t.getId(), t);
            }
        } catch (IOException e) {
            System.out.println("Could not load trainings from '" + path + "': " + e.getMessage());
        }
    }
}

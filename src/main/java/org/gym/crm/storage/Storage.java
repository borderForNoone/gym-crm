package org.gym.crm.storage;

import java.util.HashMap;
import java.util.Map;

public class Storage {
    private final Map<Namespace, Object> storages = new HashMap<>();

    public Storage(TraineeStorage traineeStorage,
                   TrainerStorage trainerStorage,
                   TrainingStorage trainingStorage) {
        storages.put(Namespace.TRAINEE, traineeStorage);
        storages.put(Namespace.TRAINER, trainerStorage);
        storages.put(Namespace.TRAINING, trainingStorage);
    }

    public TraineeStorage getTraineeStorage() {
        return (TraineeStorage) storages.get(Namespace.TRAINEE);
    }

    public TrainerStorage getTrainerStorage() {
        return (TrainerStorage) storages.get(Namespace.TRAINER);
    }

    public TrainingStorage getTrainingStorage() {
        return (TrainingStorage) storages.get(Namespace.TRAINING);
    }
}

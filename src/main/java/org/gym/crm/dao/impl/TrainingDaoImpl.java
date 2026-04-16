package org.gym.crm.dao.impl;

import org.gym.crm.dao.TrainingDao;
import org.gym.crm.model.Training;
import org.gym.crm.storage.Storage;
import org.gym.crm.storage.TrainingStorage;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TrainingDaoImpl implements TrainingDao {
    private final TrainingStorage storage;

    public TrainingDaoImpl(Storage storage) {
        this.storage = storage.getTrainingStorage();
    }

    @Override
    public Training save(Training training) {
        storage.getTrainings().put(training.getId(), training);

        return training;
    }

    @Override
    public Optional<Training> findById(Long id) {
        return Optional.ofNullable(storage.getTrainings().get(id));
    }

    @Override
    public List<Training> findAll() {
        return new ArrayList<>(storage.getTrainings().values());
    }
}

package org.gym.crm.dao.impl;

import org.gym.crm.dao.TrainingDao;
import org.gym.crm.model.Training;
import org.gym.crm.storage.Storage;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class TrainingDaoImpl implements TrainingDao {
    private final Storage storage;

    public TrainingDaoImpl(Storage storage) {
        this.storage = storage;
    }

    @Override
    public Training save(Training training) {
        store().put(training.getId(), training);

        return training;
    }

    @Override
    public Optional<Training> findById(Long id) {
        return Optional.ofNullable(store().get(id));
    }

    @Override
    public List<Training> findAll() {
        return new ArrayList<>(store().values());
    }

    private Map<Long, Training> store() {
        return storage.getTrainingStorage().getTrainings();
    }
}

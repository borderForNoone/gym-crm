package org.gym.crm.dao.impl;

import lombok.Setter;
import org.gym.crm.dao.TrainingDao;
import org.gym.crm.model.Training;
import org.gym.crm.storage.TrainingStorage;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class TrainingDaoImpl implements TrainingDao {
    @Setter
    private TrainingStorage storage;

    private Map<Long, Training> store() {
        return storage.getTrainings();
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
}

package org.gym.crm.dao.impl;

import org.gym.crm.dao.TrainerDao;
import org.gym.crm.model.Trainer;
import org.gym.crm.storage.Storage;
import org.gym.crm.storage.TrainerStorage;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TrainerDaoImpl implements TrainerDao {
    private final TrainerStorage storage;

    public TrainerDaoImpl(Storage storage) {
        this.storage = storage.getTrainerStorage();
    }

    @Override
    public Trainer save(Long id, Trainer trainer) {
        storage.getTrainers().put(id, trainer);

        return trainer;
    }

    @Override
    public Optional<Trainer> findById(Long id) {
        return Optional.ofNullable(storage.getTrainers().get(id));
    }

    @Override
    public List<Trainer> findAll() {
        return new ArrayList<>(storage.getTrainers().values());
    }

    @Override
    public Trainer update(Long id, Trainer trainer) {
        if (!storage.getTrainers().containsKey(id)) {
            throw new IllegalArgumentException("Trainer not found with id: " + id);
        }
        storage.getTrainers().put(id, trainer);

        return trainer;
    }
}

package org.gym.crm.dao.impl;

import org.gym.crm.dao.TrainerDao;
import org.gym.crm.model.Trainer;
import org.gym.crm.storage.Storage;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class TrainerDaoImpl implements TrainerDao {
    private final Storage storage;

    public TrainerDaoImpl(Storage storage) {
        this.storage = storage;
    }

    @Override
    public Trainer save(Long id, Trainer trainer) {
        store().put(id, trainer);

        return trainer;
    }

    @Override
    public Optional<Trainer> findById(Long id) {
        return Optional.ofNullable(store().get(id));
    }

    @Override
    public List<Trainer> findAll() {
        return new ArrayList<>(store().values());
    }

    @Override
    public Trainer update(Long id, Trainer trainer) {
        if (!store().containsKey(id)) {
            throw new IllegalArgumentException("Trainer not found with id: " + id);
        }
        store().put(id, trainer);

        return trainer;
    }

    private Map<Long, Trainer> store() {
        return storage.getTrainerStorage().getTrainers();
    }
}

package org.gym.crm.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.gym.crm.dao.TrainerDao;
import org.gym.crm.model.Trainer;
import org.gym.crm.storage.Storage;
import org.gym.crm.storage.TrainerStorage;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class TrainerDaoImpl implements TrainerDao {
    private final TrainerStorage storage;

    public TrainerDaoImpl(Storage storage) {
        this.storage = storage.getTrainerStorage();
    }

    @Override
    public Trainer save(Long id, Trainer trainer) {
        storage.getTrainers().put(id, trainer);

        log.debug("Saved trainer with id={}", id);
        return trainer;
    }

    @Override
    public Optional<Trainer> findById(Long id) {
        return Optional.ofNullable(storage.getTrainers().get(id));
    }

    @Override
    public List<Trainer> findAll() {
        log.debug("Fetching all trainers, count={}", storage.getTrainers().size());
        return new ArrayList<>(storage.getTrainers().values());
    }

    @Override
    public Trainer update(Long id, Trainer trainer) {
        if (!storage.getTrainers().containsKey(id)) {
            log.error("Failed to update trainer, id not found={}", id);
            throw new IllegalArgumentException("Trainer not found with id: " + id);
        }
        storage.getTrainers().put(id, trainer);

        log.info("Trainer updated successfully id={}", id);
        return trainer;
    }
}

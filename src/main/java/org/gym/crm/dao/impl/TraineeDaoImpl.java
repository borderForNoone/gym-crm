package org.gym.crm.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.gym.crm.dao.TraineeDao;
import org.gym.crm.model.Trainee;
import org.gym.crm.storage.Storage;
import org.gym.crm.storage.TraineeStorage;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class TraineeDaoImpl implements TraineeDao {
    private static final String TRAINEE_NOT_FOUND_MESSAGE = "Trainee not found with id: ";

    private final TraineeStorage storage;

    public TraineeDaoImpl(Storage storage) {
        this.storage = storage.getTraineeStorage();
    }

    @Override
    public Trainee save(Long id, Trainee trainee) {
        storage.getTrainees().put(id, trainee);

        log.debug("Saved trainee with id={}", id);
        return trainee;
    }

    @Override
    public Optional<Trainee> findById(Long id) {
        return Optional.ofNullable(storage.getTrainees().get(id));
    }

    @Override
    public List<Trainee> findAll() {
        log.debug("Fetching all trainees, count={}", storage.getTrainees().size());
        return new ArrayList<>(storage.getTrainees().values());
    }

    @Override
    public Trainee update(Long id, Trainee trainee) {
        if (!storage.getTrainees().containsKey(id)) {
            log.error("Failed to update trainee with id: {}", id);
            throw new IllegalArgumentException(TRAINEE_NOT_FOUND_MESSAGE + id);
        }
        storage.getTrainees().put(id, trainee);

        return trainee;
    }

    @Override
    public void delete(Long id) {
        if (storage.getTrainees().remove(id) == null) {
            log.error("Failed to delete trainee, id not found={}", id);
            throw new IllegalArgumentException(TRAINEE_NOT_FOUND_MESSAGE + id);
        }

        log.info("Trainee deleted successfully id={}", id);
    }
}

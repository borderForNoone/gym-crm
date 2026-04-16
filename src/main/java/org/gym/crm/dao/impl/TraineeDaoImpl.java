package org.gym.crm.dao.impl;

import org.gym.crm.dao.TraineeDao;
import org.gym.crm.model.Trainee;
import org.gym.crm.storage.Storage;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class TraineeDaoImpl implements TraineeDao {
    private final Storage storage;

    public TraineeDaoImpl(Storage storage) {
        this.storage = storage;
    }

    @Override
    public Trainee save(Long id, Trainee trainee) {
        store().put(id, trainee);

        return trainee;
    }

    @Override
    public Optional<Trainee> findById(Long id) {
        return Optional.ofNullable(store().get(id));
    }

    @Override
    public List<Trainee> findAll() {
        return new ArrayList<>(store().values());
    }

    @Override
    public Trainee update(Long id, Trainee trainee) {
        if (!store().containsKey(id)) {
            throw new IllegalArgumentException("Trainee not found with id: " + id);
        }
        store().put(id, trainee);

        return trainee;
    }

    @Override
    public void delete(Long id) {
        if (store().remove(id) == null) {
            throw new IllegalArgumentException("Trainee not found with id: " + id);
        }
    }

    private Map<Long, Trainee> store() {
        return storage.getTraineeStorage().getTrainees();
    }
}

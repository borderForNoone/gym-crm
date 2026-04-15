package org.gym.crm.dao;

import org.gym.crm.model.Trainer;

import java.util.List;
import java.util.Optional;

public interface TrainerDao {
    Trainer save(Long id, Trainer trainer);
    Optional<Trainer> findById(Long id);
    List<Trainer> findAll();
    Trainer update(Long id, Trainer trainer);
}

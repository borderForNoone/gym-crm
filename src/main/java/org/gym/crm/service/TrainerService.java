package org.gym.crm.service;

import org.gym.crm.model.Trainer;

import java.util.List;
import java.util.Optional;

public interface TrainerService {
    Trainer create(Long id, Trainer trainer);

    Optional<Trainer> findById(Long id);

    List<Trainer> findAll();

    Trainer update(Long id, Trainer trainer);
}

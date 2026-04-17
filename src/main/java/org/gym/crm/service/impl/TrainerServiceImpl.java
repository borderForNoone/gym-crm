package org.gym.crm.service.impl;

import lombok.Setter;
import org.gym.crm.dao.TrainerDao;
import org.gym.crm.model.Trainer;
import org.gym.crm.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrainerServiceImpl implements TrainerService {
    @Autowired
    @Setter
    private TrainerDao trainerDao;

    @Override
    public Trainer create(Long id, Trainer trainer) {
        return trainerDao.save(id, trainer);
    }

    @Override
    public Optional<Trainer> findById(Long id) {
        return trainerDao.findById(id);
    }

    @Override
    public List<Trainer> findAll() {
        return trainerDao.findAll();
    }

    @Override
    public Trainer update(Long id, Trainer trainer) {
        return trainerDao.update(id, trainer);
    }
}

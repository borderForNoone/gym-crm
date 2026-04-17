package org.gym.crm.service.impl;

import lombok.Setter;
import org.gym.crm.dao.TrainingDao;
import org.gym.crm.model.Training;
import org.gym.crm.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrainingServiceImpl implements TrainingService {
    @Autowired
    @Setter
    private TrainingDao trainingDao;

    @Override
    public Training create(Training training) {
        return trainingDao.save(training);
    }

    @Override
    public Optional<Training> findById(Long id) {
        return trainingDao.findById(id);
    }

    @Override
    public List<Training> findAll() {
        return trainingDao.findAll();
    }
}

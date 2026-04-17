package org.gym.crm.service.impl;

import lombok.Setter;
import org.gym.crm.dao.TraineeDao;
import org.gym.crm.model.Trainee;
import org.gym.crm.service.TraineeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TraineeServiceImpl implements TraineeService {
    @Setter
    private TraineeDao traineeDao;

    @Override
    public Trainee create(Long id, Trainee trainee) {
        return traineeDao.save(id, trainee);
    }

    @Override
    public Optional<Trainee> findById(Long id) {
        return traineeDao.findById(id);
    }

    @Override
    public List<Trainee> findAll() {
        return traineeDao.findAll();
    }

    @Override
    public Trainee update(Long id, Trainee trainee) {
        return traineeDao.update(id, trainee);
    }

    @Override
    public void delete(Long id) {
        traineeDao.delete(id);
    }
}

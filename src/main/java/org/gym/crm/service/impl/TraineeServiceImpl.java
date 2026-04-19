package org.gym.crm.service.impl;

import lombok.Setter;
import org.gym.crm.dao.TraineeDao;
import org.gym.crm.model.Trainee;
import org.gym.crm.service.TraineeService;
import org.gym.crm.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TraineeServiceImpl implements TraineeService {
    @Autowired
    @Setter
    private TraineeDao traineeDao;

    @Autowired
    private UserProfileService userProfileService;

    @Override
    public Trainee create(Long id, Trainee trainee) {
        String username = userProfileService.generateUsername(
                trainee.getFirstName(), trainee.getLastName());
        String password = userProfileService.generatePassword();

        Trainee traineeWithProfile = trainee.toBuilder()
                .username(username)
                .password(password)
                .build();

        return traineeDao.save(id, traineeWithProfile);
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

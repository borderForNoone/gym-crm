package org.gym.crm.service.impl;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.gym.crm.dao.TraineeDao;
import org.gym.crm.model.Trainee;
import org.gym.crm.service.TraineeService;
import org.gym.crm.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TraineeServiceImpl implements TraineeService {
    @Autowired
    @Setter
    private TraineeDao traineeDao;

    @Autowired
    private UserProfileService userProfileService;

    @Override
    public Trainee create(Long id, Trainee trainee) {
        log.info("Creating trainee with first name and last name: {}, {}", trainee.getFirstName(), trainee.getLastName());
        String username = userProfileService.generateUsername(
                trainee.getFirstName(), trainee.getLastName());
        String password = userProfileService.generatePassword();

        Trainee traineeWithProfile = trainee.toBuilder()
                .username(username)
                .password(password)
                .build();

        log.info("Trainee created successfully with username: {}", username);
        return traineeDao.save(id, traineeWithProfile);
    }

    @Override
    public Optional<Trainee> findById(Long id) {
        Optional<Trainee> trainee = traineeDao.findById(id);

        if (trainee.isEmpty()) {
            log.warn("Trainee not found with id: {}", id);
        }

        return trainee;
    }

    @Override
    public List<Trainee> findAll() {
        log.debug("Fetching all trainees");
        return traineeDao.findAll();
    }

    @Override
    public Trainee update(Long id, Trainee trainee) {
        log.info("Updating trainee with id={}", id);
        return traineeDao.update(id, trainee);
    }

    @Override
    public void delete(Long id) {
        log.warn("Deleting trainee with id={}", id);
        traineeDao.delete(id);
    }
}

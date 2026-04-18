package org.gym.crm.service.impl;

import lombok.Setter;
import org.gym.crm.dao.TraineeDao;
import org.gym.crm.dto.request.TraineeRequestDto;
import org.gym.crm.dto.responce.TraineeResponseDto;
import org.gym.crm.mapper.TraineeMapper;
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

    @Autowired
    private TraineeMapper traineeMapper;

    @Override
    public TraineeResponseDto create(Long id, TraineeRequestDto request) {
        Trainee trainee = traineeMapper.toEntity(request);

        String username = userProfileService.generateUsername(
                trainee.getFirstName(), trainee.getLastName());
        String password = userProfileService.generatePassword();

        Trainee traineeWithProfile = trainee.toBuilder()
                .username(username)
                .password(password)
                .build();

        return traineeMapper.toResponseDto(traineeDao.save(id, traineeWithProfile));
    }

    @Override
    public Optional<TraineeResponseDto> findById(Long id) {
        return traineeDao.findById(id)
                .map(traineeMapper::toResponseDto);
    }

    @Override
    public List<TraineeResponseDto> findAll() {
        return traineeDao.findAll().stream()
                .map(traineeMapper::toResponseDto)
                .toList();
    }

    @Override
    public TraineeResponseDto update(Long id, TraineeRequestDto request) {
        Trainee trainee = traineeMapper.toEntity(request);
        return traineeMapper.toResponseDto(traineeDao.update(id, trainee));
    }

    @Override
    public void delete(Long id) {
        traineeDao.delete(id);
    }
}

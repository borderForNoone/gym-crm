package org.gym.crm.service.impl;

import lombok.Setter;
import org.gym.crm.dao.TrainerDao;
import org.gym.crm.dto.request.TrainerRequestDto;
import org.gym.crm.dto.responce.TrainerResponseDto;
import org.gym.crm.mapper.TrainerMapper;
import org.gym.crm.model.Trainer;
import org.gym.crm.service.TrainerService;
import org.gym.crm.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrainerServiceImpl implements TrainerService {
    @Autowired
    @Setter
    private TrainerDao trainerDao;

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private TrainerMapper trainerMapper;

    @Override
    public TrainerResponseDto create(Long id, TrainerRequestDto request) {
        Trainer trainer = trainerMapper.toEntity(request);

        String username = userProfileService.generateUsername(
                request.getFirstName(), request.getLastName());
        String password = userProfileService.generatePassword();

        Trainer trainerWithProfile = trainer.toBuilder()
                .username(username)
                .password(password)
                .build();

        return trainerMapper.toResponseDto(trainerDao.save(id, trainerWithProfile));
    }

    @Override
    public Optional<TrainerResponseDto> findById(Long id) {
        return trainerDao.findById(id)
                .map(trainerMapper::toResponseDto);
    }

    @Override
    public List<TrainerResponseDto> findAll() {
        return trainerDao.findAll().stream()
                .map(trainerMapper::toResponseDto)
                .toList();
    }

    @Override
    public TrainerResponseDto update(Long id, TrainerRequestDto request) {
        Trainer trainer = trainerMapper.toEntity(request);

        return trainerMapper.toResponseDto(trainerDao.update(id, trainer));
    }
}

package org.gym.crm.service.impl;

import lombok.Setter;
import org.gym.crm.dao.TrainingDao;
import org.gym.crm.dto.request.TrainingRequestDto;
import org.gym.crm.dto.responce.TrainingResponseDto;
import org.gym.crm.mapper.TrainingMapper;
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

    @Autowired
    private TrainingMapper trainingMapper;

    @Override
    public TrainingResponseDto create(TrainingRequestDto request) {
        Training training = trainingMapper.toEntity(request);

        return trainingMapper.toResponseDto(trainingDao.save(training));
    }

    @Override
    public Optional<TrainingResponseDto> findById(Long id) {
        return trainingDao.findById(id)
                .map(trainingMapper::toResponseDto);
    }

    @Override
    public List<TrainingResponseDto> findAll() {
        return trainingDao.findAll().stream()
                .map(trainingMapper::toResponseDto)
                .toList();
    }
}

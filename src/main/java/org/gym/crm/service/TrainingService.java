package org.gym.crm.service;

import org.gym.crm.dto.request.TrainingRequestDto;
import org.gym.crm.dto.responce.TrainingResponseDto;

import java.util.List;
import java.util.Optional;

public interface TrainingService {
    TrainingResponseDto create(TrainingRequestDto training);

    Optional<TrainingResponseDto> findById(Long id);

    List<TrainingResponseDto> findAll();
}

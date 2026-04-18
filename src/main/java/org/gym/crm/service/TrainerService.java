package org.gym.crm.service;

import org.gym.crm.dto.request.TrainerRequestDto;
import org.gym.crm.dto.responce.TrainerResponseDto;

import java.util.List;
import java.util.Optional;

public interface TrainerService {
    TrainerResponseDto create(Long id, TrainerRequestDto request);

    Optional<TrainerResponseDto> findById(Long id);

    List<TrainerResponseDto> findAll();

    TrainerResponseDto update(Long id, TrainerRequestDto request);
}

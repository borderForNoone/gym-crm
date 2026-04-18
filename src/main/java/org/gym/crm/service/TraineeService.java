package org.gym.crm.service;

import org.gym.crm.dto.request.TraineeRequestDto;
import org.gym.crm.dto.responce.TraineeResponseDto;

import java.util.List;
import java.util.Optional;

public interface TraineeService {
    TraineeResponseDto create(Long id, TraineeRequestDto request);

    Optional<TraineeResponseDto> findById(Long id);

    List<TraineeResponseDto> findAll();

    TraineeResponseDto update(Long id, TraineeRequestDto request);

    void delete(Long id);
}

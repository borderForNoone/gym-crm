package org.gym.crm.mapper;

import org.gym.crm.dto.request.TrainerRequestDto;
import org.gym.crm.dto.response.TrainerResponseDto;
import org.gym.crm.model.Trainer;
import org.springframework.stereotype.Component;

@Component
public class TrainerMapper {
    public Trainer toEntity(TrainerRequestDto request) {
        return Trainer.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .isActive(request.isActive())
                .specialization(request.getSpecialization())
                .build();
    }

    public TrainerResponseDto toResponseDto(Trainer trainer) {
        return TrainerResponseDto.builder()
                .id(trainer.getUserId())
                .username(trainer.getUsername())
                .firstName(trainer.getFirstName())
                .lastName(trainer.getLastName())
                .isActive(trainer.isActive())
                .specialization(trainer.getSpecialization())
                .build();
    }
}

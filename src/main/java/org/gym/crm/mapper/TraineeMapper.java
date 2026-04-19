package org.gym.crm.mapper;

import org.gym.crm.dto.request.TraineeRequestDto;
import org.gym.crm.dto.responce.TraineeResponseDto;
import org.gym.crm.model.Trainee;
import org.springframework.stereotype.Component;

@Component
public class TraineeMapper {
    public Trainee toEntity(TraineeRequestDto request) {
        return Trainee.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .isActive(request.isActive())
                .dateOfBirth(request.getDateOfBirth())
                .address(request.getAddress())
                .build();
    }

    public TraineeResponseDto toResponseDto(Trainee trainee) {
        return TraineeResponseDto.builder()
                .id(trainee.getUserId())
                .username(trainee.getUsername())
                .firstName(trainee.getFirstName())
                .lastName(trainee.getLastName())
                .isActive(trainee.isActive())
                .dateOfBirth(trainee.getDateOfBirth())
                .address(trainee.getAddress())
                .build();
    }
}

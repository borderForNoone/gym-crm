package org.gym.crm.mapper;

import org.gym.crm.dto.request.TraineeRequestDto;
import org.gym.crm.dto.response.TraineeResponseDto;
import org.gym.crm.dto.update.TraineeUpdateDTO;
import org.gym.crm.model.Trainee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TraineeMapper {
    @Mapping(target = "user.firstName", source = "firstName")
    @Mapping(target = "user.lastName", source = "lastName")
    @Mapping(target = "user.isActive", source = "active")
    @Mapping(target = "dateOfBirth", source = "dateOfBirth")
    @Mapping(target = "address", source = "address")
    Trainee toEntity(TraineeRequestDto dto);

    Trainee toEntity(TraineeUpdateDTO traineeUpdateDTO);

    @Mapping(target = "id", source = "userId")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "firstName", source = "user.firstName")
    @Mapping(target = "lastName", source = "user.lastName")
    @Mapping(target = "isActive", source = "user.isActive")
    @Mapping(target = "dateOfBirth", source = "dateOfBirth")
    @Mapping(target = "address", source = "address")
    TraineeResponseDto toDto(Trainee trainee);
}
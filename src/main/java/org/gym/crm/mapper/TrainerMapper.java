package org.gym.crm.mapper;

import org.gym.crm.dto.request.TrainerRequestDto;
import org.gym.crm.dto.response.TrainerResponseDto;
import org.gym.crm.dto.update.TrainerUpdateDTO;
import org.gym.crm.model.Trainer;
import org.gym.crm.model.TrainingType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TrainerMapper {
    Trainer toEntity(TrainerRequestDto dto);

    Trainer toEntity(TrainerUpdateDTO dto);

    @Mapping(target = "id", source = "userId")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "firstName", source = "user.firstName")
    @Mapping(target = "lastName", source = "user.lastName")
    @Mapping(target = "isActive", source = "user.isActive")
    @Mapping(target = "specialization", source = "specialization")
    TrainerResponseDto toDto(Trainer trainer);

    default TrainingType map(String type) {
        if (type == null) return null;

        TrainingType trainingType = new TrainingType();
        trainingType.setTrainingTypeName(type);
        return trainingType;
    }

    default String map(TrainingType type) {
        return type == null ? null : type.getTrainingTypeName();
    }
}

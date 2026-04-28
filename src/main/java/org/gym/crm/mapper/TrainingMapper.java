package org.gym.crm.mapper;

import org.gym.crm.dto.request.TrainingRequestDto;
import org.gym.crm.dto.response.TrainingResponseDto;
import org.gym.crm.model.Training;
import org.gym.crm.model.TrainingType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface TrainingMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "trainingType", ignore = true)
    @Mapping(target = "trainee", ignore = true)
    @Mapping(target = "trainer", ignore = true)
    Training toEntity(TrainingRequestDto dto);

    @Mapping(target = "trainingTypeName", source = "trainingType.trainingTypeName")
    TrainingResponseDto toDto(Training training);

    @Named("idToTrainingType")
    default TrainingType idToTrainingType(Long trainingTypeId) {
        if (trainingTypeId == null) {
            return null;
        }
        TrainingType trainingType = new TrainingType();
        trainingType.setId(trainingTypeId);
        return trainingType;
    }
}

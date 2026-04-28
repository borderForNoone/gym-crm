package org.gym.crm.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
public class TrainingRequestDto {
    private final Long traineeId;
    private final Long trainerId;
    private final String trainingName;
    private final Long trainingTypeId;
    private final LocalDate trainingDate;
    private final Integer trainingDuration;
}
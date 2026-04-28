package org.gym.crm.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
public class TrainingResponseDto {
    private final Long id;
    private final Long traineeId;
    private final Long trainerId;
    private final String trainingName;
    private final String trainingTypeName;
    private final LocalDate trainingDate;
    private final int trainingDuration;
}

package org.gym.crm.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.gym.crm.model.TrainingType;

@Getter
@Builder
@AllArgsConstructor
public class TrainerResponseDto {
    private final Long id;
    private final String username;
    private final String firstName;
    private final String lastName;
    private final boolean isActive;
    private final TrainingType specialization;
}

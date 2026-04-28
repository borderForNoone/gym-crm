package org.gym.crm.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TrainerRequestDto {
    private final String firstName;
    private final String lastName;
    private final boolean isActive;
    private String specialization;
}

package org.gym.crm.dto.responce;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
public class TraineeResponseDto {
    private final Long id;
    private final String username;
    private final String firstName;
    private final String lastName;
    private final boolean isActive;
    private final LocalDate dateOfBirth;
    private final String address;
}

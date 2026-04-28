package org.gym.crm.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
public class TraineeRequestDto {
    private final String firstName;
    private final String lastName;
    private final boolean active;
    private final LocalDate dateOfBirth;
    private final String address;
    private String trainingTypeName;
}

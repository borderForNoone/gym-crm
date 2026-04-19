package org.gym.crm.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode
@ToString
public abstract class User {
    private final String firstName;
    private final String lastName;
    private final String username;
    private final String password;
    private final boolean isActive;
}

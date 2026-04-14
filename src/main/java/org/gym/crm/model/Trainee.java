package org.gym.crm.model;

import java.time.LocalDate;

public class Trainee extends User {
    private final LocalDate dateOfBirth;
    private final String address;
    private final Long userId;

    private Trainee(Builder builder) {
        super(builder);
        this.dateOfBirth = builder.dateOfBirth;
        this.address = builder.address;
        this.userId = builder.userId;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public Long getUserId() {
        return userId;
    }

    public static class Builder extends User.Builder<Builder> {
        private LocalDate dateOfBirth;
        private String address;
        private Long userId;

        public Builder dateOfBirth(LocalDate dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Builder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        @Override
        public Trainee build() {
            return new Trainee(this);
        }
    }
}

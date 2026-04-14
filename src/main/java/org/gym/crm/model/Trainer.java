package org.gym.crm.model;

public class Trainer extends User {
    private final Specialization specialization;
    private final Long userId;

    private Trainer(Builder builder) {
        super(builder);
        this.specialization = builder.specialization;
        this.userId = builder.userId;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public Long getUserId() {
        return userId;
    }

    public static class Builder extends User.Builder<Builder> {
        private Specialization specialization;
        private Long userId;

        public Builder specialization(Specialization specialization) {
            this.specialization = specialization;
            return this;
        }

        public Builder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        @Override
        public Trainer build() {
            return new Trainer(this);
        }
    }
}

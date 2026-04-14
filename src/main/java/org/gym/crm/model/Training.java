package org.gym.crm.model;

import java.time.LocalDate;
import java.util.Objects;

public class Training {
    private final Long id;
    private final Long traineeId;
    private final Long trainerId;
    private final String trainingName;
    private final TrainingType trainingType;
    private final LocalDate trainingDate;
    private final int trainingDuration;

    private Training(Builder builder) {
        this.id = builder.id;
        this.traineeId = builder.traineeId;
        this.trainerId = builder.trainerId;
        this.trainingName = builder.trainingName;
        this.trainingType = builder.trainingType;
        this.trainingDate = builder.trainingDate;
        this.trainingDuration = builder.trainingDuration;
    }

    public Long getId() {
        return id;
    }

    public Long getTraineeId() {
        return traineeId;
    }

    public Long getTrainerId() {
        return trainerId;
    }

    public String getTrainingName() {
        return trainingName;
    }

    public TrainingType getTrainingType() {
        return trainingType;
    }

    public LocalDate getTrainingDate() {
        return trainingDate;
    }

    public int getTrainingDuration() {
        return trainingDuration;
    }

    public static class Builder {
        private Long id;
        private Long traineeId;
        private Long trainerId;
        private String trainingName;
        private TrainingType trainingType;
        private LocalDate trainingDate;
        private int trainingDuration;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder traineeId(Long traineeId) {
            this.traineeId = traineeId;
            return this;
        }

        public Builder trainerId(Long trainerId) {
            this.trainerId = trainerId;
            return this;
        }

        public Builder trainingName(String trainingName) {
            this.trainingName = trainingName;
            return this;
        }

        public Builder trainingType(TrainingType trainingType) {
            this.trainingType = trainingType;
            return this;
        }

        public Builder trainingDate(LocalDate trainingDate) {
            this.trainingDate = trainingDate;
            return this;
        }

        public Builder trainingDuration(int trainingDuration) {
            this.trainingDuration = trainingDuration;
            return this;
        }

        public Training build() {
            return new Training(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Training training = (Training) o;
        return trainingDuration == training.trainingDuration && Objects.equals(id, training.id) && Objects.equals(traineeId, training.traineeId) && Objects.equals(trainerId, training.trainerId) && Objects.equals(trainingName, training.trainingName) && trainingType == training.trainingType && Objects.equals(trainingDate, training.trainingDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, traineeId, trainerId, trainingName, trainingType, trainingDate, trainingDuration);
    }

    @Override
    public String toString() {
        return "Training{" +
                "id=" + id +
                ", traineeId=" + traineeId +
                ", trainerId=" + trainerId +
                ", trainingName='" + trainingName + '\'' +
                ", trainingType=" + trainingType +
                ", trainingDate=" + trainingDate +
                ", trainingDuration=" + trainingDuration +
                '}';
    }
}

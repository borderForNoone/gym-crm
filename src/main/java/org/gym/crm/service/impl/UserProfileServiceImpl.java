package org.gym.crm.service.impl;

import org.gym.crm.service.TraineeService;
import org.gym.crm.service.TrainerService;
import org.gym.crm.service.UserProfileService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class UserProfileServiceImpl implements UserProfileService {
    private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int PASSWORD_LENGTH = 10;
    private static final SecureRandom RANDOM = new SecureRandom();

    private final TraineeService traineeService;
    private final TrainerService trainerService;

    public UserProfileServiceImpl(@Lazy TraineeService traineeService,
                                  @Lazy TrainerService trainerService) {
        this.traineeService = traineeService;
        this.trainerService = trainerService;
    }

    @Override
    public String generateUsername(String firstName, String lastName) {
        String base = firstName + "." + lastName;

        long duplicates = countDuplicates(base);

        return duplicates == 0 ? base : base + duplicates;
    }

    @Override
    public String generatePassword() {
        StringBuilder password = new StringBuilder(PASSWORD_LENGTH);
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            password.append(CHARS.charAt(RANDOM.nextInt(CHARS.length())));
        }

        return password.toString();
    }

    private long countDuplicates(String base) {
        long inTrainees = traineeService.findAll().stream()
                .filter(t -> t.getUsername() != null)
                .filter(t -> t.getUsername().equals(base)
                        || t.getUsername().matches(base + "\\d+"))
                .count();

        long inTrainers = trainerService.findAll().stream()
                .filter(t -> t.getUsername() != null)
                .filter(t -> t.getUsername().equals(base)
                        || t.getUsername().matches(base + "\\d+"))
                .count();

        return inTrainees + inTrainers;
    }
}

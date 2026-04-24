package org.gym.crm.service.impl;

import io.micrometer.common.util.StringUtils;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.gym.crm.dao.TraineeDao;
import org.gym.crm.dao.TrainerDao;
import org.gym.crm.model.Trainee;
import org.gym.crm.model.Trainer;
import org.gym.crm.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.stream.Stream;

@Slf4j
@Service
public class UserProfileServiceImpl implements UserProfileService {
    private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int PASSWORD_LENGTH = 10;
    private static final SecureRandom RANDOM = new SecureRandom();

    @Autowired
    @Setter
    private TraineeDao traineeDao;

    @Autowired
    @Setter
    private TrainerDao trainerDao;

    @Override
    public String generateUsername(String firstName, String lastName) {
        String baseUsername = firstName + "." + lastName;

        if (!usernameExists(baseUsername)) {
            log.debug("Generated username='{}'", baseUsername);
            return baseUsername;
        }

        long suffix = 1;
        String candidate;
        do {
            candidate = baseUsername + suffix;
            suffix++;
        } while (usernameExists(candidate));

        log.debug("Generated username='{}' with suffix due to duplicates", candidate);
        return candidate;
    }

    @Override
    public String generatePassword() {
        StringBuilder password = new StringBuilder(PASSWORD_LENGTH);
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            password.append(CHARS.charAt(RANDOM.nextInt(CHARS.length())));
        }

        log.debug("Generated password of length={}", PASSWORD_LENGTH);
        return password.toString();
    }

    private long countDuplicates(String baseUsername) {
        return getExistingUsernames()
                .filter(StringUtils::isNotBlank)
                .map(username -> username.replaceAll("\\d+$", ""))
                .filter(username -> username.equals(baseUsername))
                .count();
    }

    private Stream<String> getExistingUsernames() {
        return Stream.concat(
                traineeDao.findAll().stream().map(Trainee::getUsername),
                trainerDao.findAll().stream().map(Trainer::getUsername)
        );
    }

    private boolean usernameExists(String username) {
        return getExistingUsernames()
                .filter(StringUtils::isNotBlank)
                .anyMatch(username::equals);
    }
}

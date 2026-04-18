package org.gym.crm.service;

public interface UserProfileService {
    String generateUsername(String firstName, String lastName);

    String generatePassword();
}

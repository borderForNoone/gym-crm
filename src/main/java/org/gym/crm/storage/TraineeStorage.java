package org.gym.crm.storage;

import lombok.Getter;
import org.gym.crm.model.Trainee;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Getter
@Component
public class TraineeStorage {
    private final Map<Long, Trainee> trainees = new HashMap<>();
}

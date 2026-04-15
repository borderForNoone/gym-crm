package org.gym.crm.storage;

import lombok.Getter;
import org.gym.crm.model.Trainer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Getter
@Component
public class TrainerStorage {
    private final Map<Long, Trainer> trainers = new HashMap<>();
}

package org.gym.crm.storage;

import lombok.Getter;
import org.gym.crm.model.Training;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Getter
@Component
public class TrainingStorage {
    private final Map<Long, Training> trainings = new HashMap<>();
}

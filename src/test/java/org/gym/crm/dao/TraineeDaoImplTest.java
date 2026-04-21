package org.gym.crm.dao;

import org.gym.crm.dao.impl.TraineeDaoImpl;
import org.gym.crm.model.Trainee;
import org.gym.crm.storage.Storage;
import org.gym.crm.storage.TraineeStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.gym.crm.util.TestConstants.FIRST_NAME;
import static org.gym.crm.util.TestConstants.ID;
import static org.gym.crm.util.TestConstants.LAST_NAME;
import static org.gym.crm.util.TestConstants.NON_EXISTING_ID;
import static org.gym.crm.util.TestConstants.NOT_FOUND_MESSAGE;
import static org.gym.crm.util.TestConstants.TRAINEE_NOT_FOUND_MESSAGE;
import static org.gym.crm.util.TestConstants.USERNAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TraineeDaoImplTest {
    @Mock
    private Storage storage;
    @Mock
    private TraineeStorage traineeStorage;

    private TraineeDao dao;
    private Map<Long, Trainee> trainees;
    private Trainee trainee;

    @BeforeEach
    void setUp() {
        trainees = new HashMap<>();
        trainee = buildTrainee();

        when(storage.getTraineeStorage()).thenReturn(traineeStorage);
        when(traineeStorage.getTrainees()).thenReturn(trainees);

        dao = new TraineeDaoImpl(storage);
    }

    @Test
    void save_shouldPutTraineeInStorageAndReturn() {
        Trainee actual = dao.save(ID, trainee);

        assertEquals(trainee, actual);
        assertEquals(trainee, trainees.get(ID));
    }

    @Test
    void findById_shouldReturnTrainee_whenExists() {
        trainees.put(ID, trainee);

        Optional<Trainee> actual = dao.findById(ID);

        assertTrue(actual.isPresent());
        assertEquals(trainee, actual.get());
    }

    @Test
    void findById_shouldReturnEmpty_whenNotExists() {
        Optional<Trainee> actual = dao.findById(NON_EXISTING_ID);

        assertTrue(actual.isEmpty());
    }

    @Test
    void findAll_shouldReturnAllTrainees() {
        trainees.put(ID, trainee);

        List<Trainee> actual = dao.findAll();

        assertEquals(1, actual.size());
        assertTrue(actual.contains(trainee));
    }

    @Test
    void findAll_shouldReturnEmptyList_whenNoTrainees() {
        List<Trainee> actual = dao.findAll();

        assertTrue(actual.isEmpty());
    }

    @Test
    void update_shouldUpdateTrainee_whenExists() {
        trainees.put(ID, trainee);
        Trainee expected = trainee.toBuilder().firstName("Jane").build();

        Trainee actual = dao.update(ID, expected);

        assertEquals(expected, actual);
        assertEquals(expected, trainees.get(ID));
    }

    @Test
    void update_shouldThrowException_whenNotExists() {
        IllegalArgumentException actual = assertThrows(
                IllegalArgumentException.class,
                () -> dao.update(NON_EXISTING_ID, trainee)
        );

        assertEquals(TRAINEE_NOT_FOUND_MESSAGE + NON_EXISTING_ID, actual.getMessage());
    }

    @Test
    void delete_shouldRemoveTrainee_whenExists() {
        trainees.put(ID, trainee);

        dao.delete(ID);

        assertFalse(trainees.containsKey(ID));
    }

    @Test
    void delete_shouldThrowException_whenNotExists() {
        IllegalArgumentException actual = assertThrows(
                IllegalArgumentException.class,
                () -> dao.delete(NON_EXISTING_ID)
        );

        assertEquals(TRAINEE_NOT_FOUND_MESSAGE + NON_EXISTING_ID, actual.getMessage());
    }

    private Trainee buildTrainee() {
        return Trainee.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .username(USERNAME)
                .isActive(true)
                .build();
    }
}
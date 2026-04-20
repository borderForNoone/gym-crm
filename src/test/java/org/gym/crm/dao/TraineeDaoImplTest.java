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

    private TraineeDaoImpl traineeDao;
    private Map<Long, Trainee> trainees;
    private Trainee trainee;

    @BeforeEach
    void setUp() {
        trainees = new HashMap<>();
        trainee = Trainee.builder()
                .firstName("John")
                .lastName("Smith")
                .username("John.Smith")
                .isActive(true)
                .build();

        when(storage.getTraineeStorage()).thenReturn(traineeStorage);
        when(traineeStorage.getTrainees()).thenReturn(trainees);

        traineeDao = new TraineeDaoImpl(storage);
    }

    @Test
    void save_shouldPutTraineeInStorageAndReturn() {
        Trainee result = traineeDao.save(1L, trainee);

        assertEquals(trainee, result);
        assertEquals(trainee, trainees.get(1L));
    }

    @Test
    void findById_shouldReturnTrainee_whenExists() {
        trainees.put(1L, trainee);

        Optional<Trainee> result = traineeDao.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(trainee, result.get());
    }

    @Test
    void findById_shouldReturnEmpty_whenNotExists() {
        Optional<Trainee> result = traineeDao.findById(99L);

        assertTrue(result.isEmpty());
    }

    @Test
    void findAll_shouldReturnAllTrainees() {
        trainees.put(1L, trainee);

        List<Trainee> result = traineeDao.findAll();

        assertEquals(1, result.size());
        assertTrue(result.contains(trainee));
    }

    @Test
    void findAll_shouldReturnEmptyList_whenNoTrainees() {
        List<Trainee> result = traineeDao.findAll();

        assertTrue(result.isEmpty());
    }

    @Test
    void update_shouldUpdateTrainee_whenExists() {
        trainees.put(1L, trainee);
        Trainee updated = trainee.toBuilder().firstName("Jane").build();

        Trainee result = traineeDao.update(1L, updated);

        assertEquals(updated, result);
        assertEquals(updated, trainees.get(1L));
    }

    @Test
    void update_shouldThrowException_whenNotExists() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> traineeDao.update(99L, trainee)
        );

        assertEquals("Trainee not found with id: 99", ex.getMessage());
    }

    @Test
    void delete_shouldRemoveTrainee_whenExists() {
        trainees.put(1L, trainee);

        traineeDao.delete(1L);

        assertFalse(trainees.containsKey(1L));
    }

    @Test
    void delete_shouldThrowException_whenNotExists() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> traineeDao.delete(99L)
        );

        assertEquals("Trainee not found with id: 99", ex.getMessage());
    }
}
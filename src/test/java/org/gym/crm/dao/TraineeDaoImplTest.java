package org.gym.crm.dao;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.gym.crm.dao.impl.TraineeDaoImpl;
import org.gym.crm.model.Trainee;
import org.gym.crm.model.User;
import org.gym.crm.storage.Storage;
import org.gym.crm.storage.TraineeStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.gym.crm.util.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
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
    private ListAppender<ILoggingEvent> listAppender;

    @BeforeEach
    void setUp() {
        trainees = new HashMap<>();
        trainee = buildTrainee();

        when(storage.getTraineeStorage()).thenReturn(traineeStorage);
        when(traineeStorage.getTrainees()).thenReturn(trainees);

        dao = new TraineeDaoImpl(storage);

        initLogger();
    }

    @Test
    void save_shouldPutTraineeInStorageAndReturn() {
        Trainee actual = dao.save(trainee);

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

        Trainee expected = trainee.toBuilder()
                .user(trainee.getUser().toBuilder()
                        .firstName("Jane")
                        .build())
                .build();

        Trainee actual = dao.update(ID, expected);

        assertEquals(expected, actual);
        assertEquals(expected, trainees.get(ID));
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

    @Test
    void update_shouldLogError_whenTraineeNotFound() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> dao.update(NON_EXISTING_ID, trainee)
        );

        assertThat(exception.getMessage())
                .isEqualTo(TRAINEE_NOT_FOUND_MESSAGE + NON_EXISTING_ID);

        assertThat(listAppender.list)
                .hasSize(1)
                .first()
                .satisfies(log -> {
                    assertThat(log.getLevel()).isEqualTo(Level.ERROR);
                    assertThat(log.getFormattedMessage())
                            .contains(String.valueOf(NON_EXISTING_ID));
                });
    }

    @Test
    void delete_shouldNotLogError_whenTraineeExists() {
        trainees.put(ID, trainee);

        dao.delete(ID);

        assertThat(listAppender.list)
                .extracting(ILoggingEvent::getLevel)
                .doesNotContain(Level.ERROR);
    }

    private Trainee buildTrainee() {
        return Trainee.builder()
                .user(User.builder()
                        .firstName(FIRST_NAME)
                        .lastName(LAST_NAME)
                        .username(USERNAME)
                        .isActive(true)
                        .build())
                .build();
    }

    private void initLogger() {
        Logger logger = (Logger) LoggerFactory.getLogger(TraineeDaoImpl.class);

        listAppender = new ListAppender<>();
        listAppender.start();

        logger.addAppender(listAppender);
    }
}
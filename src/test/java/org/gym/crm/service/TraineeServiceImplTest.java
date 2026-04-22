package org.gym.crm.service;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.gym.crm.dao.TraineeDao;
import org.gym.crm.model.Trainee;
import org.gym.crm.service.impl.TraineeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.gym.crm.util.TestConstants.FIRST_NAME;
import static org.gym.crm.util.TestConstants.ID;
import static org.gym.crm.util.TestConstants.LAST_NAME;
import static org.gym.crm.util.TestConstants.NON_EXISTING_ID;
import static org.gym.crm.util.TestConstants.PASSWORD;
import static org.gym.crm.util.TestConstants.USERNAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TraineeServiceImplTest {
    private final Trainee trainee = buildTrainee();

    @Mock
    private TraineeDao traineeDao;
    @Mock
    private UserProfileService userProfileService;
    @InjectMocks
    private TraineeServiceImpl traineeService;

    private Logger logger;
    private ListAppender<ILoggingEvent> listAppender;

    @BeforeEach
    void setUp() {
        logger = (Logger) LoggerFactory.getLogger(TraineeServiceImpl.class);

        listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);
    }

    @Test
    void create_shouldGenerateUsernameAndPasswordAndSave() {
        Trainee expected = trainee.toBuilder()
                .username(USERNAME)
                .password(PASSWORD)
                .build();

        when(userProfileService.generateUsername(FIRST_NAME, LAST_NAME)).thenReturn(USERNAME);
        when(userProfileService.generatePassword()).thenReturn(PASSWORD);
        when(traineeDao.save(ID, expected)).thenReturn(expected);

        Trainee actual = traineeService.create(ID, trainee);

        assertEquals(USERNAME, actual.getUsername());
        assertEquals(PASSWORD, actual.getPassword());
        verify(userProfileService).generateUsername(FIRST_NAME, LAST_NAME);
        verify(userProfileService).generatePassword();
        verify(traineeDao).save(ID, expected);
    }

    @Test
    void findById_shouldReturnTrainee_whenExists() {
        when(traineeDao.findById(ID)).thenReturn(Optional.of(trainee));

        Optional<Trainee> actual = traineeService.findById(ID);

        assertTrue(actual.isPresent());
        assertEquals(trainee, actual.get());
        verify(traineeDao).findById(ID);
    }

    @Test
    void findById_shouldReturnEmpty_whenNotExists() {
        when(traineeDao.findById(NON_EXISTING_ID)).thenReturn(Optional.empty());

        Optional<Trainee> actual = traineeService.findById(NON_EXISTING_ID);

        assertTrue(actual.isEmpty());
        verify(traineeDao).findById(NON_EXISTING_ID);
    }

    @Test
    void findAll_shouldReturnAllTrainees() {
        List<Trainee> expected = List.of(trainee);

        when(traineeDao.findAll()).thenReturn(expected);

        List<Trainee> actual = traineeService.findAll();

        assertEquals(expected.size(), actual.size());
        assertEquals(expected.getFirst(), actual.getFirst());
        verify(traineeDao).findAll();
    }

    @Test
    void update_shouldUpdateAndReturnTrainee() {
        when(traineeDao.update(ID, trainee)).thenReturn(trainee);

        Trainee actual = traineeService.update(ID, trainee);

        assertEquals(trainee, actual);
        verify(traineeDao).update(ID, trainee);
    }

    @Test
    void delete_shouldCallDaoDelete() {
        traineeService.delete(ID);

        verify(traineeDao).delete(ID);
    }

    @Test
    void create_shouldLogInfo_whenCreatingTrainee() {
        Trainee expected = trainee.toBuilder()
                .username(USERNAME)
                .password(PASSWORD)
                .build();

        when(userProfileService.generateUsername(FIRST_NAME, LAST_NAME)).thenReturn(USERNAME);
        when(userProfileService.generatePassword()).thenReturn(PASSWORD);
        when(traineeDao.save(ID, expected)).thenReturn(expected);

        traineeService.create(ID, trainee);

        assertThat(listAppender.list)
                .filteredOn(log -> log.getLevel() == Level.INFO)
                .hasSize(2)
                .extracting(ILoggingEvent::getFormattedMessage)
                .satisfies(messages -> {
                    assertThat(messages.get(0))
                            .contains(FIRST_NAME)
                            .contains(LAST_NAME);
                    assertThat(messages.get(1))
                            .contains(USERNAME);
                });
    }

    @Test
    void findById_shouldLogWarn_whenTraineeNotFound() {
        when(traineeDao.findById(NON_EXISTING_ID)).thenReturn(Optional.empty());

        traineeService.findById(NON_EXISTING_ID);

        assertThat(listAppender.list)
                .filteredOn(log -> log.getLevel() == Level.WARN)
                .hasSize(1)
                .first()
                .extracting(ILoggingEvent::getFormattedMessage)
                .asString()
                .contains(String.valueOf(NON_EXISTING_ID));
    }

    @Test
    void findById_shouldNotLogWarn_whenTraineeFound() {
        when(traineeDao.findById(ID)).thenReturn(Optional.of(trainee));

        traineeService.findById(ID);

        assertThat(listAppender.list)
                .filteredOn(log -> log.getLevel() == Level.WARN)
                .isEmpty();
    }

    private Trainee buildTrainee() {
        return Trainee.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .isActive(true)
                .build();
    }
}
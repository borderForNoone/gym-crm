package org.gym.crm.dao;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.gym.crm.dao.impl.TraineeDaoImpl;
import org.gym.crm.model.Trainee;
import org.gym.crm.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.query.criteria.JpaCriteriaQuery;
import org.hibernate.query.criteria.JpaRoot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.gym.crm.util.TestConstants.FIRST_NAME;
import static org.gym.crm.util.TestConstants.ID;
import static org.gym.crm.util.TestConstants.LAST_NAME;
import static org.gym.crm.util.TestConstants.NON_EXISTING_ID;
import static org.gym.crm.util.TestConstants.TRAINEE_NOT_FOUND_MESSAGE;
import static org.gym.crm.util.TestConstants.USERNAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TraineeDaoImplTest {
    @Mock
    private SessionFactory sessionFactory;
    @Mock
    private Session session;
    @Mock
    private HibernateCriteriaBuilder criteriaBuilder;
    @Mock
    private JpaCriteriaQuery<Trainee> criteriaQuery;
    @Mock
    private JpaRoot<Trainee> root;
    @Mock
    private Query<Trainee> query;

    @InjectMocks
    private TraineeDaoImpl dao;

    private Trainee trainee;
    private ListAppender<ILoggingEvent> listAppender;

    @BeforeEach
    void setUp() {
        trainee = buildTrainee();

        when(sessionFactory.getCurrentSession()).thenReturn(session);

        initLogger();
    }

    @Test
    void save_shouldPersistTraineeAndReturn() {
        Trainee actual = dao.save(trainee);

        verify(session).persist(trainee);
        assertEquals(trainee, actual);
    }

    @Test
    void findById_shouldReturnTrainee_whenExists() {
        when(session.get(Trainee.class, ID)).thenReturn(trainee);

        Optional<Trainee> actual = dao.findById(ID);

        assertTrue(actual.isPresent());
        assertEquals(trainee, actual.get());
    }

    @Test
    void findById_shouldReturnEmpty_whenNotExists() {
        when(session.get(Trainee.class, NON_EXISTING_ID)).thenReturn(null);

        Optional<Trainee> actual = dao.findById(NON_EXISTING_ID);

        assertTrue(actual.isEmpty());
    }

    @Test
    void findAll_shouldReturnAllTrainees() {
        when(session.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Trainee.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Trainee.class)).thenReturn(root);
        when(session.createQuery(criteriaQuery)).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(trainee));

        List<Trainee> actual = dao.findAll();

        assertEquals(1, actual.size());
        assertTrue(actual.contains(trainee));
    }

    @Test
    void findAll_shouldReturnEmptyList_whenNoTrainees() {
        when(session.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Trainee.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Trainee.class)).thenReturn(root);
        when(session.createQuery(criteriaQuery)).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of());

        List<Trainee> actual = dao.findAll();

        assertTrue(actual.isEmpty());
    }

    @Test
    void update_shouldMergeAndReturnTrainee() {
        Trainee updated = trainee.toBuilder()
                .user(trainee.getUser().toBuilder()
                        .firstName("Jane")
                        .build())
                .build();

        when(session.merge(updated)).thenReturn(updated);

        Trainee actual = dao.update(updated);

        verify(session).merge(updated);
        assertEquals(updated, actual);
    }

    @Test
    void delete_shouldRemoveTrainee_whenExists() {
        when(session.get(Trainee.class, ID)).thenReturn(trainee);

        dao.delete(ID);

        verify(session).remove(trainee);
    }

    @Test
    void delete_shouldThrowException_whenNotExists() {
        when(session.get(Trainee.class, NON_EXISTING_ID)).thenReturn(null);

        IllegalArgumentException actual = assertThrows(
                IllegalArgumentException.class,
                () -> dao.delete(NON_EXISTING_ID)
        );

        assertEquals(TRAINEE_NOT_FOUND_MESSAGE + NON_EXISTING_ID, actual.getMessage());
        verify(session, never()).remove(any());
    }

    @Test
    void delete_shouldLogError_whenTraineeNotFound() {
        when(session.get(Trainee.class, NON_EXISTING_ID)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> dao.delete(NON_EXISTING_ID));

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
        when(session.get(Trainee.class, ID)).thenReturn(trainee);

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
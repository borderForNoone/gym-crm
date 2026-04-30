package org.gym.crm.dao;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.gym.crm.dao.impl.TrainerDaoImpl;
import org.gym.crm.model.Trainee;
import org.gym.crm.model.Trainer;
import org.gym.crm.model.TrainingType;
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

import java.util.List;
import java.util.Optional;

import static org.gym.crm.util.TestConstants.FITNESS;
import static org.gym.crm.util.TestConstants.ID;
import static org.gym.crm.util.TestConstants.NON_EXISTING_ID;
import static org.gym.crm.util.TestConstants.TRAINER_FIRST_NAME;
import static org.gym.crm.util.TestConstants.TRAINER_LAST_NAME;
import static org.gym.crm.util.TestConstants.TRAINER_USERNAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrainerDaoImplTest {
    @Mock
    private SessionFactory sessionFactory;
    @Mock
    private Session session;
    @Mock
    private HibernateCriteriaBuilder criteriaBuilder;
    @Mock
    private JpaCriteriaQuery<Trainer> criteriaQuery;
    @Mock
    private JpaRoot<Trainer> root;
    @Mock
    private Query<Trainer> query;

    @InjectMocks
    private TrainerDaoImpl dao;

    private Trainer trainer;

    @BeforeEach
    void setUp() {
        trainer = buildTrainer();
        when(sessionFactory.getCurrentSession()).thenReturn(session);
    }

    @Test
    void save_shouldPersistTrainerAndReturn() {
        Trainer actual = dao.save(trainer);

        verify(session).persist(trainer);
        assertEquals(trainer, actual);
    }

    @Test
    void findById_shouldReturnTrainer_whenExists() {
        when(session.get(Trainer.class, ID)).thenReturn(trainer);

        Optional<Trainer> actual = dao.findById(ID);

        assertTrue(actual.isPresent());
        assertEquals(trainer, actual.get());
    }

    @Test
    void findById_shouldReturnEmpty_whenNotExists() {
        when(session.get(Trainer.class, NON_EXISTING_ID)).thenReturn(null);

        Optional<Trainer> actual = dao.findById(NON_EXISTING_ID);

        assertTrue(actual.isEmpty());
    }

    @Test
    void findAll_shouldReturnAllTrainers() {
        when(session.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Trainer.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Trainer.class)).thenReturn(root);
        when(session.createQuery(criteriaQuery)).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(trainer));

        List<Trainer> actual = dao.findAll();

        assertEquals(1, actual.size());
        assertTrue(actual.contains(trainer));
    }

    @Test
    void findAll_shouldReturnEmptyList_whenNoTrainers() {
        when(session.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Trainer.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Trainer.class)).thenReturn(root);
        when(session.createQuery(criteriaQuery)).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of());

        List<Trainer> actual = dao.findAll();

        assertTrue(actual.isEmpty());
    }

    @Test
    void update_shouldMergeAndReturnTrainer() {
        Trainer updated = trainer.toBuilder()
                .user(trainer.getUser().toBuilder()
                        .firstName("John")
                        .build())
                .build();

        when(session.merge(updated)).thenReturn(updated);

        Trainer actual = dao.update(updated);

        verify(session).merge(updated);
        assertEquals(updated, actual);
    }

    private TrainingType fitnessType() {
        TrainingType fitness = new TrainingType();
        fitness.setTrainingTypeName(FITNESS);

        return fitness;
    }

    private Trainer buildTrainer() {
        return Trainer.builder()
                .user(User.builder()
                        .firstName(TRAINER_FIRST_NAME)
                        .lastName(TRAINER_LAST_NAME)
                        .username(TRAINER_USERNAME)
                        .isActive(true)
                        .build())
                .specialization(fitnessType())
                .build();
    }
}
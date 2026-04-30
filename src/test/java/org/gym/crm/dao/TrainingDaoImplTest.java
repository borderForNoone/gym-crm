package org.gym.crm.dao;

import org.gym.crm.dao.impl.TrainingDaoImpl;
import org.gym.crm.model.Training;
import org.gym.crm.model.TrainingType;
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

import static org.gym.crm.util.TestConstants.DURATION;
import static org.gym.crm.util.TestConstants.FITNESS;
import static org.gym.crm.util.TestConstants.ID;
import static org.gym.crm.util.TestConstants.NON_EXISTING_ID;
import static org.gym.crm.util.TestConstants.TRAINING_DATE;
import static org.gym.crm.util.TestConstants.TRAINING_NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrainingDaoImplTest {
    @Mock
    private SessionFactory sessionFactory;
    @Mock
    private Session session;
    @Mock
    private HibernateCriteriaBuilder criteriaBuilder;
    @Mock
    private JpaCriteriaQuery<Training> criteriaQuery;
    @Mock
    private JpaRoot<Training> root;
    @Mock
    private Query<Training> query;

    @InjectMocks
    private TrainingDaoImpl dao;

    private Training training;

    @BeforeEach
    void setUp() {
        training = buildTraining();

        when(sessionFactory.getCurrentSession()).thenReturn(session);
    }

    @Test
    void save_shouldPersistTrainingAndReturn() {
        Training actual = dao.save(training);

        verify(session).persist(training);
        assertEquals(training, actual);
    }

    @Test
    void findById_shouldReturnTraining_whenExists() {
        when(session.get(Training.class, ID)).thenReturn(training);

        Optional<Training> actual = dao.findById(ID);

        assertTrue(actual.isPresent());
        assertEquals(training, actual.get());
    }

    @Test
    void findById_shouldReturnEmpty_whenNotExists() {
        when(session.get(Training.class, NON_EXISTING_ID)).thenReturn(null);

        Optional<Training> actual = dao.findById(NON_EXISTING_ID);

        assertTrue(actual.isEmpty());
    }

    @Test
    void findAll_shouldReturnAllTrainings() {
        when(session.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Training.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Training.class)).thenReturn(root);
        when(session.createQuery(criteriaQuery)).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(training));

        List<Training> actual = dao.findAll();

        assertEquals(1, actual.size());
        assertTrue(actual.contains(training));
    }

    @Test
    void findAll_shouldReturnEmptyList_whenNoTrainings() {
        when(session.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Training.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Training.class)).thenReturn(root);
        when(session.createQuery(criteriaQuery)).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of());

        List<Training> actual = dao.findAll();

        assertTrue(actual.isEmpty());
    }

    private TrainingType fitnessType() {
        TrainingType fitness = new TrainingType();
        fitness.setTrainingTypeName(FITNESS);

        return fitness;
    }

    private Training buildTraining() {
        return Training.builder()
                .id(ID)
                .traineeId(ID)
                .trainerId(ID)
                .trainingName(TRAINING_NAME)
                .trainingType(fitnessType())
                .trainingDate(TRAINING_DATE)
                .trainingDuration(DURATION)
                .build();
    }
}
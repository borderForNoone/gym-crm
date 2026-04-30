package org.gym.crm.dao.impl;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gym.crm.dao.TrainingDao;
import org.gym.crm.model.Training;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TrainingDaoImpl implements TrainingDao {
    private final SessionFactory sessionFactory;

    @Override
    public Training save(Training training) {
        sessionFactory.getCurrentSession().persist(training);

        log.info("Saved training with id={}", training.getId());
        return training;
    }

    @Override
    public Optional<Training> findById(Long id) {
        return Optional.ofNullable(sessionFactory.getCurrentSession().get(Training.class, id));
    }

    @Override
    public List<Training> findAll() {
        Session session = sessionFactory.getCurrentSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Training> criteriaQuery = criteriaBuilder.createQuery(Training.class);

        Root<Training> root = criteriaQuery.from(Training.class);
        criteriaQuery.select(root);

        List<Training> result = session.createQuery(criteriaQuery).getResultList();

        log.debug("Fetching all trainings, count={}", result.size());
        return result;
    }
}

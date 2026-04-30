package org.gym.crm.dao.impl;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gym.crm.dao.TrainerDao;
import org.gym.crm.model.Trainer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TrainerDaoImpl implements TrainerDao {
    private final SessionFactory sessionFactory;

    @Override
    public Trainer save(Trainer trainer) {
        sessionFactory.getCurrentSession().persist(trainer);

        log.debug("Saved trainer with id={}", trainer.getUserId());
        return trainer;
    }

    @Override
    public Optional<Trainer> findById(Long id) {
        return Optional.ofNullable(sessionFactory.getCurrentSession().get(Trainer.class, id));
    }

    @Override
    public List<Trainer> findAll() {
        Session session = sessionFactory.getCurrentSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Trainer> criteriaQuery = criteriaBuilder.createQuery(Trainer.class);

        Root<Trainer> root = criteriaQuery.from(Trainer.class);
        criteriaQuery.select(root);

        List<Trainer> result = session.createQuery(criteriaQuery).getResultList();

        log.debug("Fetching all trainers, count={}", result.size());
        return result;
    }

    @Override
    public Trainer update(Trainer trainer) {
        Trainer merged = sessionFactory.getCurrentSession().merge(trainer);

        log.debug("Updated trainer with id={}", merged.getUserId());
        return merged;
    }
}

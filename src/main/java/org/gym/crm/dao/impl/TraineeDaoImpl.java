package org.gym.crm.dao.impl;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gym.crm.dao.TraineeDao;
import org.gym.crm.model.Trainee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TraineeDaoImpl implements TraineeDao {
    private static final String TRAINEE_NOT_FOUND_MESSAGE = "Trainee not found with id: ";

    private final SessionFactory sessionFactory;

    @Override
    public Trainee save(Trainee trainee) {
        sessionFactory.getCurrentSession().persist(trainee);

        log.debug("Saved trainee with id={}", trainee.getUserId());
        return trainee;
    }

    @Override
    public Optional<Trainee> findById(Long id) {
        return Optional.ofNullable(sessionFactory.getCurrentSession().get(Trainee.class, id));
    }

    @Override
    public List<Trainee> findAll() {
        Session session = sessionFactory.getCurrentSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Trainee> criteriaQuery = criteriaBuilder.createQuery(Trainee.class);

        criteriaQuery.from(Trainee.class);

        List<Trainee> result = session.createQuery(criteriaQuery).getResultList();

        log.debug("Fetching all trainees, count={}", result.size());
        return result;
    }

    @Override
    public Trainee update(Trainee trainee) {
        Trainee merged = sessionFactory.getCurrentSession().merge(trainee);

        log.debug("Updated trainee with id={}", merged.getUserId());
        return merged;
    }

    @Override
    public void delete(Long id) {
        var session = sessionFactory.getCurrentSession();
        Trainee trainee = session.get(Trainee.class, id);

        if (trainee == null) {
            log.error("Failed to delete trainee, id not found={}", id);
            throw new IllegalArgumentException(TRAINEE_NOT_FOUND_MESSAGE + id);
        }

        session.remove(trainee);
        log.info("Trainee deleted successfully id={}", id);
    }
}

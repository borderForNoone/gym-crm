package org.gym.crm.config;

import org.gym.crm.model.Trainee;
import org.gym.crm.model.Trainer;
import org.gym.crm.model.Training;
import org.gym.crm.model.TrainingType;
import org.gym.crm.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestAppConfig.class, DataSourceConfig.class, LiquibaseConfig.class, HibernateConfig.class})
@TestPropertySource("classpath:application-test.properties")
@ActiveProfiles("test")
class HibernateConfigTest {
    @Autowired
    private SessionFactory factory;

    @Test
    void shouldCreateSessionFactory() {
        assertNotNull(factory);
    }

    @Test
    void shouldOpenSession() {
        try (Session session = factory.openSession()) {
            assertNotNull(session);
            assertTrue(session.isOpen());
        }
    }

    @Test
    void shouldContainAllEntities() {
        var metamodel = factory.getMetamodel();

        assertNotNull(metamodel.entity(User.class));
        assertNotNull(metamodel.entity(Trainee.class));
        assertNotNull(metamodel.entity(Trainer.class));
        assertNotNull(metamodel.entity(Training.class));
        assertNotNull(metamodel.entity(TrainingType.class));
    }
}

package org.gym.crm.config;

import org.gym.crm.model.Trainee;
import org.gym.crm.model.Trainer;
import org.gym.crm.model.Training;
import org.gym.crm.model.TrainingType;
import org.gym.crm.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class HibernateConfig {
    @Value("${hibernate.hbm2ddl.auto}")
    private String hbm2ddlAuto;

    @Bean
    public SessionFactory sessionFactory(DataSource dataSource) {
        Properties properties = new Properties();
        properties.put("hibernate.hbm2ddl.auto", hbm2ddlAuto);
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.format_sql", "true");
        properties.put("hibernate.connection.datasource", dataSource);

        return new org.hibernate.cfg.Configuration()
                .addProperties(properties)
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Trainer.class)
                .addAnnotatedClass(Trainee.class)
                .addAnnotatedClass(Training.class)
                .addAnnotatedClass(TrainingType.class)
                .buildSessionFactory();
    }
}

package org.gym.crm;

import org.gym.crm.config.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class GymApplication {

    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context =
                     new AnnotationConfigApplicationContext(AppConfig.class)) {
            System.out.println("Spring context started successfully");
        }
    }

}

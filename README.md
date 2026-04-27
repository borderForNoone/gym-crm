# Gym CRM application
**CRM system for gym management.**

![Build](https://github.com/borderForNoone/gym-crm/actions/workflows/ci.yml/badge.svg?branch=develop)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=borderForNoone_gym-crm&metric=coverage)](https://sonarcloud.io/summary/overall?id=borderForNoone_gym-crm)
[![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=borderForNoone_gym-crm&metric=alert_status)](https://sonarcloud.io/summary/overall?id=borderForNoone_gym-crm)
# Getting Started (Local Setup)

1. ## Database Setup (MySQL)
Before the first run, make sure to create a database and user with proper privileges:
```
CREATE DATABASE gym_crm;
CREATE USER 'gcauser'@'localhost' IDENTIFIED BY 'gcauser';
GRANT ALL PRIVILEGES ON gym_crm.* TO 'gcauser'@'localhost';
```

2. ## Environment Variables
Create a .env file in the root directory of the project with the following configuration:

# Database Configuration
DB_USERNAME=gcauser
DB_PASSWORD=gcauser
DB_URL=jdbc:mysql://localhost:3306/gym_crm


3. ## Run the Application

### Build the project
```
mvn clean compile
```

### Run tests (requires Docker to be running)
```
mvn test
```
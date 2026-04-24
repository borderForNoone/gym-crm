# gym crm

# Getting Started (Local Setup)

1. ## Database Setup (MySQL)
Before the first run, make sure to create a database and user with proper privileges:

CREATE DATABASE gym_crm;
CREATE USER 'gcauser'@'localhost' IDENTIFIED BY 'gcauser';
GRANT ALL PRIVILEGES ON gym_crm.* TO 'gcauser'@'localhost';
2. ## Environment Variables
Create a .env file in the root directory of the project with the following configuration:

# Database Configuration
DB_USERNAME=gcauser
DB_PASSWORD=gcauser
DB_URL=jdbc:mysql://localhost:3306/gym_crm


3. ## Run the Application
```bash
# Build the project
mvn clean compile

# Run tests (requires Docker to be running)
mvn test
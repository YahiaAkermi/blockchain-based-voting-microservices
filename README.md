# CloudSync_microservices
CloudSync Voting Microservices
A distributed voting application with two microservices: Voting Management (MySQL) and Vote Casting (MongoDB). The application leverages Spring Cloud Config, Spring Cloud Bus with RabbitMQ, and Actuator for dynamic, real-time configuration updates. Configurations are stored in a centralized GitHub repo, and webhooks automatically trigger updates across hundreds of microservices.


# CloudSync Voting Microservices

## Project Overview

This project is a microservices-based voting system that supports real-time configuration updates and scalability. It consists of two primary services:
- **Voting Management Service**: Manages voting sessions, candidates, and user registrations. Backed by **MySQL**.
- **Vote Casting Service**: Manages the actual voting process and records votes. Backed by **MongoDB**.

The app uses **Spring Cloud Config** for centralized configuration management, **Spring Cloud Bus** with **RabbitMQ** for auto-refreshing configurations across services, and **Spring Boot Actuator** for monitoring.

## Microservices Architecture

1. **Voting Management Microservice (MySQL)**:
   - Handles all voting-related entities like candidates, voting sessions, and user applications.
   - Data is stored in a **MySQL** relational database.

2. **Vote Casting Microservice (MongoDB)**:
   - Handles the vote casting process and efficiently records votes in a **MongoDB** database.
   - NoSQL storage is used for handling large datasets, ensuring scalability during high voting loads.

## Dynamic Configuration Management

### Centralized Configuration with Spring Cloud Config:
- **Spring Cloud Config Server** provides a central place to manage and serve configurations across all microservices.
- Configurations are stored in a **GitHub repository**, allowing for secure, version-controlled management of application properties.

### Automatic Configuration Updates with Spring Cloud Bus:
- **Spring Cloud Bus** integrates with **RabbitMQ** to propagate configuration changes to all microservices in real-time.
- Each time a configuration change is committed to the GitHub repository, a **GitHub Webhook** triggers a **POST request** to the `/monitor` endpoint in the **Config Server**.
- Spring Cloud Bus and RabbitMQ notify all connected microservices to refresh their configurations dynamically without needing to restart them.

### Webhooks and Local Development via Hookdeck:
- The GitHub repository is integrated with **webhooks**, ensuring that every configuration change triggers an update in the application.
- In local development, since the **Config Server** is not deployed externally, we use **Hookdeck** to redirect the webhook's POST requests to the local environment, simulating real-time configuration updates.

### Monitoring with Spring Boot Actuator:
- **Spring Boot Actuator** is enabled for real-time application monitoring, exposing important endpoints like `/actuator/health`, `/actuator/bus-refresh`, and `/actuator/metrics` to track system performance and health.
- **/monitor** endpoint is used to trigger configuration refresh manually or via webhooks.

## Technology Stack

- **Spring Boot** – Main framework for building microservices.
- **Spring Cloud Config Server** – Centralized configuration management.
- **Spring Cloud Config Client** – Used by microservices to fetch and apply configurations from the Config Server.
- **Spring Cloud Bus with RabbitMQ** – To propagate configuration changes across all microservices.
- **Spring Boot Actuator** – For monitoring and health checks.
- **RabbitMQ** – Message broker used by Spring Cloud Bus for configuration auto-refresh.
- **MySQL** – For relational data in the Voting Management service.
- **MongoDB** – For NoSQL data storage in the Vote Casting service.
- **GitHub Webhooks** – To trigger configuration updates automatically.
- **Hookdeck** – Used to redirect webhook requests to a local environment for testing.

## How the Configuration Auto-Refresh Works

1. **Configuration Storage**: All configurations are stored in a **GitHub repository**.
2. **Webhook Trigger**: Any change in the repository triggers a webhook to notify the **Config Server**.
3. **RabbitMQ Propagation**: The **Config Server** uses **Spring Cloud Bus** and **RabbitMQ** to notify all connected microservices of the configuration update.
4. **Automatic Refresh**: Each microservice connected as a **Spring Cloud Config Client** refreshes its configurations dynamically without the need for restarts.

## How to Run the Application

1. **Set up MySQL** and **MongoDB** databases.
2. Clone the repository and configure your GitHub webhook with **Hookdeck** (for local testing).
3. Start RabbitMQ, Config Server, and each microservice.
4. **Test Configuration Updates**: Modify a configuration file in the GitHub repository and observe how the webhook triggers a refresh across all microservices.

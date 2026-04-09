# Planner Java Application ✈️🌐

A trip planning system built with Java and Spring Boot, designed to help users organize their trips, activities, participants, and relevant links. This project was developed following modern software engineering practices, including domain separation (API, Domain, Infra) and automated testing.

## 🚀 Technologies & Libraries

This project leverages a modern Java ecosystem:

*   **Java 21**: Utilizing the latest LTS features.
*   **Spring Boot 3.4.1**: Core framework for web and dependency injection.
*   **Spring Data JPA**: For seamless database interaction.
*   **Hibernate**: JPA implementation.
*   **Flyway**: Database migration management.
*   **H2 Database**: Lightweight in-memory database for development and testing.
*   **Project Lombok**: Reducing boilerplate code with annotations.
*   **Springdoc OpenAPI (Swagger)**: Automatic API documentation and interactive UI.
*   **JUnit 5 & Mockito**: For robust automated unit testing.

## 🏗️ Project Architecture

The project follows a clean domain-oriented structure:

```text
com.nlw.planner
├── activity
│   ├── api (Controllers/DTOs)
│   ├── domain (Entity/Service)
│   └── infra (Repository)
├── link
│   ├── api
│   ├── domain
│   └── infra
├── participant
│   ├── api
│   ├── domain
│   └── infra
└── trip
    ├── api
    ├── domain
    └── infra
```

## 📊 Core Entities

The system orbits around these four main domains:

1.  **Trip**: The core of the application. Stores destination, start/end dates, and confirmation status.
2.  **Participant**: Manages people invited to a trip, including their confirmation status.
3.  **Activity**: Allows scheduling specific tasks or events during the trip.
4.  **Link**: Stores relevant web links (hotels, restaurants, etc.) for a specific trip.

## 🛠️ How to Run

### Prerequisites
*   Java 21
*   Gradle 8.x

### Execution
1.  Clone the repository.
2.  Run the application using Gradle:
    ```bash
    ./gradlew bootRun
    ```
3.  The API will be available at `http://localhost:8080`.

### API Documentation
Once the application is running, you can explore the endpoints via Swagger UI:
*   [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## 🧪 Testing

To run the automated tests:
```bash
./gradlew test
```

---
*Developed with 💜 during Rocketseat's NLW with the support of Antigravity.*

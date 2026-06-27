# Itaú Backend Challenge - Transaction API
![Kotlin](https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
![JUnit5](https://img.shields.io/badge/JUnit5-25A162?style=for-the-badge&logo=junit5&logoColor=white)

RESTful API developed for the [Itaú Unibanco Programming Challenge](https://github.com/feltex/desafio-itau-backend)

## Technologies and Architectural Decisions

For this project, I chose to focus heavily on **performance, concurrency safety, and engineering best practices**:

* **In-Memory Storage (Thread-Safe):** As required by the challenge, no database was used.
* To ensure safety in a concurrent environment (multiple simultaneous requests), the transaction list was implemented using `CopyOnWriteArrayList`.
* **Optimized O(N) Calculation:** Statistics were calculated using the Java Streams API combined with `DoubleSummaryStatistics`, ensuring precision and low memory allocation.
* **Validation and Error Handling:** Implementation of a global `@RestControllerAdvice` to intercept exceptions—such as future dates or negative values—and return clean responses (`422 Unprocessable Entity` and `400 Bad Request` with no body) in compliance with the challenge requirements.
* **Observability:** Structured logging using the SLF4J library within services to ensure traceability of events and business logic.
* **Automated Testing:** Unit test coverage using JUnit 5 for statistics and transaction business logic, validating the behavior of the 60-second time window.
* **Containerization:** Optimized Dockerfile using a *Multi-stage Build* (compilation with JDK) to ensure a production-ready image.

## How to Run

You can run the application in two ways:

### Option 1: Using Docker (Recommended)
Ensure Docker is installed and running. In the terminal, at the project root:
```bash
# 1. Build the image
docker build -t itau-challenge-api .

# 2. Run the container
docker run -p 8080:8080 itau-challenge-api

### Option 2: Using local Gradle
./gradlew bootRun
The API is available at http://localhost:8080.
```

## Endpoints
Create Transaction
-    POST /transacao
````json
{
"valor": 150.50,
"dataHora": "2026-03-07T12:00:00.000-03:00"
}

Responses:
201 Created (Success)
422 Unprocessable Entity (Business rule violated)
400 Bad Request (Invalid JSON)
````

2. Get Statistics (Last 60 seconds)
- GET /estatistica
````json
{
"count": 1,
"sum": 150.50,
"avg": 150.50,
"min": 150.50,
"max": 150.50
}

Response:
200 OK
````

3. Clear Transactions
- DELETE /transacao
````json
Response: 200 OK
````

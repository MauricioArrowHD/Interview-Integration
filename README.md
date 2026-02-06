# Inteview Integration Challenge - Portfolio Aggregator

## Overview
This is a Spring Boot project designed to test a candidate's ability to implement a fast, resilient, and reactive API that aggregates data from multiple sources.

## The Challenge
The candidate needs to complete the `PortfolioAggregatorService` to:
1.  Fetch data from 3 "External" services (simulated by `ExternalServicesMockController`) in parallel.
2.  Handle latency and failures gracefully (e.g., if Crypto service times out, return partial results).
3.  Audit the result asynchronously using R2DBC.

## Getting Started

### Prerequisites
- Java 21
- Docker (for Testcontainers and local DB)
- Maven

### Running the Project
1.  **Start Database:**
    ```bash
    docker-compose up -d
    ```
2.  **Run Application:**
    ```bash
    mvn spring-boot:run
    ```

### Running Tests
The project includes an integration test verifying the setup and the mocks:
```bash
mvn test
```

## Structure
- `com.interview.integration.mock`: Contains the "Problem" (Slow/Flaky services).
- `com.interview.integration.service.PortfolioAggregatorService`: Where the candidate works.
- `com.interview.integration.model`: DTOs and Entities.

## Candidate Instructions
Look for `TODO` comments in `PortfolioAggregatorService.java`.

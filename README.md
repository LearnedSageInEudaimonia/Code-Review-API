# Smart Code Review API

## Overview

Smart Code Review & Complexity Analyzer is a **backend-focused Spring Boot project** that performs static analysis on Java source code to compute **complexity metrics, maintainability scores, code smells, and quality bands**. The system is designed with **industry-grade architecture**, clean separation of concerns, and persistence-first querying to reflect **real backend engineering practices**.

This project intentionally avoids UI-heavy or AI-driven shortcuts and focuses on **core backend correctness, extensibility, and production readiness**.

---

## Key Features

* Java source code parsing using AST
* Cyclomatic Complexity analysis (method & class level)
* Maintainability Index calculation
* Code Smell detection engine
* Quality Banding based on computed metrics
* Persistent storage of analysis runs (JPA)
* Fetch-only query APIs (no re-analysis on read)
* Global exception handling with stable error contracts
* Role-Based Access Control (RBAC)
* Clean DTO–Entity separation

---

## Tech Stack

* **Language:** Java
* **Framework:** Spring Boot
* **Parsing:** JavaParser (AST)
* **Persistence:** Spring Data JPA
* **Database:** H2 (dev), PostgreSQL (prod-ready)
* **Security:** Spring Security (RBAC)
* **Build Tool:** Maven
* **Utilities:** Lombok

---

## Architecture Overview

The project follows a **layered, domain-driven backend architecture**:

* **Controller Layer** – Exposes REST APIs
* **Service Layer** – Orchestrates analysis and query flows
* **Analysis Engine** – Metrics, complexity, smells, quality
* **Persistence Layer** – JPA entities & repositories
* **Query Layer** – Fetch-only read models
* **Exception Layer** – Centralized error handling

Key architectural principles:

* No entity leakage to controllers
* Services throw domain exceptions, not HTTP responses
* Queries fetch persisted results only
* Stable API contracts

---

## Analysis Workflow

1. Java source file is uploaded
2. AST is generated from source code
3. Metrics are computed:

   * Method count
   * Cyclomatic complexity
   * Maintainability score
4. Code smells are detected
5. Quality band is derived
6. Results are persisted as an analysis run
7. Subsequent requests fetch results from DB only

---

## API Capabilities

* Submit Java file for analysis
* Fetch analysis results by run ID
* Retrieve class-level metrics and smells
* Consistent error responses via global exception handler

---

## Role-Based Access Control (RBAC)

Defined roles:

* **USER** – View own analysis runs
* **REVIEWER** – View all analysis runs (read-only)
* **ADMIN** – Full access (view, manage, delete)

RBAC is applied at the API level using Spring Security annotations.

---

## Exception Handling

* Centralized `@RestControllerAdvice`
* Custom domain exceptions
* Stable `ErrorResponse` contract
* Proper HTTP status mapping

---

## Testing & Deployment

* Repository and service-level testing supported
* Dockerization planned for production-like deployment
* Database profiles for dev and prod environments

---

## Project Status

✔ Core analysis engine completed
✔ Persistence & query layer completed
✔ API contracts finalized
✔ RBAC implemented
⏳ Testing & Dockerization

---

## Why This Project?

This project demonstrates:

* Strong backend fundamentals
* Real-world architectural decisions
* Clean exception and persistence handling


It is intentionally designed to be **defensible in technical interviews** and **aligned with industry backend expectations**.

---

## License

This project is for educational and portfolio purposes.

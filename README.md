# GradClear Backend

## Project Information

- **Student:** Mbonyumugara Mushi
- **Student ID:** 27906
- **Course:** INSY 8322 — Web Technology and Internet
- **Project:** GradClear
- **Institution:** Adventist University of Central Africa (AUCA)
- **Lecturer:** Patrick DUSHIMIMANA
- **Academic Year:** 2026–2027

## Overview

GradClear is a REST API for managing university graduation clearance. It allows students to submit clearance requests, officers to process them, and approved requests to receive clearance documents.

## Technologies

- Java
- Spring Boot and Spring Web
- Spring Data JPA and Hibernate
- PostgreSQL
- Docker Compose
- Maven
- IntelliJ IDEA
- Postman

## Features

- CRUD operations for students, officers, departments, clearance types, and clearance requests.
- Search and filtering using derived queries and JPQL.
- Clearance status transition validation.
- Officer assignment and department management.
- Clearance document creation, history, and deletion.
- Request statistics and bulk updates.
- Optional dashboard and pagination.

## Prerequisites

- Compatible JDK
- IntelliJ IDEA
- Docker and Docker Compose
- Postman

## Setup and Execution

### 1. Clone the Repository

```bash
git clone https://github.com/Mugara250/grad-clear-backend.git
cd grad-clear-backend
```

### 2. Start the PostgreSQL Container

GradClear uses PostgreSQL running in a Docker container managed by Docker Compose.

Start the container using the existing Compose configuration:

```bash
docker compose up -d
```

Verify that the container is running:

```bash
docker ps
```

To stop the container:

```bash
docker compose down
```

### 3. Run the Application

Open the project in IntelliJ IDEA, allow Maven to load the dependencies, and run the main Spring Boot application class.

Alternatively, use the Maven wrapper:

**Windows**

```bash
mvnw.cmd spring-boot:run
```

**Linux/macOS**

```bash
./mvnw spring-boot:run
```

The API runs at `http://localhost:8080` by default.

## API Endpoints

| Resource | Base Path |
|---|---|
| Clearance Types | `/api/clearance-types` |
| Departments | `/api/departments` |
| Officers | `/api/officers` |
| Students | `/api/students` |
| Clearance Requests | `/api/clearance-requests` |
| Clearance Documents | `/api/clearance-documents` |
| Dashboard (bonus) | `/api/dashboard/summary` |

The main resources support CRUD operations. Additional endpoints handle searching, filtering, status changes, officer assignments, document history, statistics, and pagination.

## Business Rules

- New clearance requests start as `PENDING`.
- Request dates cannot be in the past.
- An officer cannot have multiple non-rejected requests on the same date.
- Status changes must follow the permitted transition rules.
- Clearance documents can only be issued for approved requests.
- Requests with associated documents cannot be deleted.
- Officers cannot hold duplicate clearance types or share a department with another officer.

## Testing

Use Postman to test the endpoints, request bodies, HTTP status codes, and error responses.

Enter the required seed data through the application's save endpoints:

- 4 clearance types
- 4 departments
- 3 officers
- 5 students
- 10 clearance requests

Ensure the data satisfies the examination's additional conditions, including all four request statuses and at least two approved requests.

Export the Postman collection to the repository root as:

`grad-clear-midterm.postman_collection.json`

Name each request using its examination requirement ID, such as `A2`, `B1`, `C4`, or `D2`.

## Project Structure

```text
grad-clear-backend/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── .../
│   │   │       ├── entity/
│   │   │       ├── repository/
│   │   │       ├── service/
│   │   │       └── controller/
│   │   └── resources/
│   └── test/
├── compose.yaml
├── grad-clear-midterm.postman_collection.json
├── pom.xml
└── README.md
```

## Author

**Mbonyumugara Mushi** — Student ID: 27906  
Adventist University of Central Africa (AUCA)  
INSY 8322 — Web Technology and Internet

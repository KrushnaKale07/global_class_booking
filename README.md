# Global Class Offering Booking System

Backend Engineering Assignment

A backend service for managing global live-learning classes where teachers create course offerings and parents/students can book them across different timezones.

---

## Project Overview

This application allows:

### Teachers

- Create course offerings
- Add sessions to offerings
- View their offerings and sessions

### Parents / Students

- View available offerings
- Book offerings
- View booked offerings
- Receive session schedules in their local timezone

---

## Tech Stack

### Backend

- Java 21
- Spring Boot 4
- Spring Data JPA
- Hibernate ORM

### Database

- MySQL 8

### Documentation

- Swagger / OpenAPI 3
- Postman Collection

### Utilities

- Lombok
- Maven

---

## Architecture

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
MySQL Database
```

Project follows a layered architecture:

```text
controller
service
repository
entity
dto
exception
config
util
```

---

## Core Domain Model

### Teacher

Represents instructors who create offerings.

### Parent

Represents students/parents booking offerings.

### Course

Examples:

- Python Coding
- Art Drawing
- Public Speaking

### Offering

A schedulable version of a course.

Examples:

- Saturday Batch
- Evening Batch
- Summer Camp

### Session

Actual meeting times belonging to an offering.

### Booking

Represents a parent's enrollment into an offering.

---

## Database Design

### Tables

```text
teachers
parents
courses
offerings
sessions
bookings
```

---

### Relationships

```text
Teacher
   |
   | 1:N
   |
Offering
   |
   | N:1
   |
Course

Offering
   |
   | 1:N
   |
Session

Parent
   |
   | 1:N
   |
Booking
   |
   | N:1
   |
Offering
```

---

## Timezone Handling

### Requirement

Teachers create sessions in their own timezone.

Parents may belong to different timezones.

---

### Approach

All session timestamps are stored in UTC.

Flow:

```text
Teacher Local Time
        ↓
Convert To UTC
        ↓
Store In Database
        ↓
Convert To Parent Timezone
        ↓
Display To Parent
```

---

### Benefits

- Consistent storage
- Global timezone support
- Avoid daylight saving issues

---

## Booking Rules

### Rule 1

Booking occurs at the offering level.

A parent books all sessions belonging to an offering.

---

### Rule 2

A parent cannot book another offering if any session overlaps with an already booked session.

---

### Rule 3

System must safely handle concurrent booking requests.

---

## Conflict Detection Logic

Session overlap is detected using:

```java
existingStart < newEnd
&&
existingEnd > newStart
```

If true:

```text
Booking Rejected
HTTP 409 CONFLICT
```

---

## Duplicate Booking Prevention

The system prevents:

```text
Parent
  ↓
Books Same Offering Multiple Times
```

Protection layers:

### Application Layer

```java
existsByParentIdAndOfferingId(...)
```

### Database Layer

Unique Constraint:

```text
(parent_id, offering_id)
```

---

## Concurrency Handling

Booking requests execute inside a database transaction.

```java
@Transactional
```

Parent records are locked during booking validation using:

```java
@Lock(LockModeType.PESSIMISTIC_WRITE)
```

Benefits:

- Prevent race conditions
- Prevent duplicate bookings
- Maintain schedule consistency
- Serialize concurrent booking requests

---

## API Documentation

Swagger UI:

```text
http://localhost:8080/swagger-ui/index.html
```

OpenAPI:

```text
http://localhost:8080/v3/api-docs
```

---

# Teacher APIs

## Create Offering

```http
POST /api/teachers/offerings
```

---

## Add Session

```http
POST /api/teachers/offerings/{offeringId}/sessions
```

---

## Get Teacher Offerings

```http
GET /api/teachers/{teacherId}/offerings
```

---

# Parent APIs

## Get Available Offerings

```http
GET /api/parents/offerings
```

---

## Book Offering

```http
POST /api/parents/bookings
```

---

## Get Bookings

```http
GET /api/parents/{parentId}/bookings
```

---

## Validation

Request DTOs use Bean Validation:

```java
@NotNull
@NotBlank
@Email
```

Validation errors return:

```json
{
  "success": false,
  "message": "Validation failed"
}
```

---

## Error Handling

Centralized exception handling using:

```java
@RestControllerAdvice
```

Handled Exceptions:

- ResourceNotFoundException
- BookingConflictException
- DuplicateBookingException
- DataIntegrityViolationException
- Validation Exceptions

---

## Assumptions

1. Booking occurs at the offering level.
2. All sessions within an offering are booked together.
3. Session times are immutable after booking.
4. UTC is the canonical storage timezone.
5. Teachers and parents have valid timezone identifiers.
6. A parent cannot book overlapping sessions.

---

## Running Locally

### Clone Repository

```bash
git clone <repository-url>
```

---

### Configure Database

Create database:

```sql
CREATE DATABASE global_class_booking;
```

---

### Update application.properties

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/global_class_booking
spring.datasource.username=root
spring.datasource.password=your_password
```

---

### Run Application

```bash
mvn spring-boot:run
```

Application:

```text
http://localhost:8080
```

---

## Environment Variables

Currently using application.properties.

Database variables:

```properties
DB_URL
DB_USERNAME
DB_PASSWORD
```

can be externalized for deployment.

---

## Future Improvements

- Docker Support
- JWT Authentication
- Role-Based Authorization
- Integration Testing
- CI/CD Pipeline
- Flyway Database Migrations
- Redis Caching

---

## Author

Backend Engineering Assignment Submission
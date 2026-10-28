# Bookstore — Application Architecture project

## Status
Starter project for the fictitious bookstore assignment. Verify every feature locally before submission. This is a learning project, not a production-ready shop.

## Stack
- Java 17+
- Spring Boot 3.5.x
- Spring Web, Spring Data JPA, Bean Validation
- MySQL
- HTML, CSS, JavaScript
- JUnit 5 / Spring Boot Test

## Project structure
- `backend/`: Spring Boot REST API, services, repositories, entities, tests
- `frontend/`: plain HTML/CSS/JavaScript client
- `docs/`: architecture, planning, test scenarios

## Prerequisites
- JDK 17 or newer
- MySQL 8+
- Maven 3.6.3+
- Git

## 1. Create the database
Run this in MySQL:

```sql
CREATE DATABASE bookstore_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER 'bookstore_user'@'localhost' IDENTIFIED BY 'change_me';
GRANT ALL PRIVILEGES ON bookstore_db.* TO 'bookstore_user'@'localhost';
FLUSH PRIVILEGES;
```

Change the username/password if you prefer. Never commit real credentials.

## 2. Configure backend
In `backend/src/main/resources/application.properties`, set the database username/password to match your local MySQL setup.

From the `backend` directory run:

```bash
mvn spring-boot:run
```

The API should start at `http://localhost:8080`. JPA creates tables on startup for local development (`ddl-auto=update`).

## 3. Open frontend
Open `frontend/index.html` in your browser. If browser CORS restrictions occur when opening a `file://` page, use a simple local static server, for example from the `frontend` folder:

```bash
python -m http.server 5500
```

Then visit `http://localhost:5500`. The backend has CORS enabled for local development.

## 4. Run tests
From `backend`:

```bash
mvn test
```

The unit tests for the reservation service use Mockito and do not require a running MySQL server. To run the full application, MySQL must be running.

## Main API endpoints
- `GET /api/books` — list books
- `GET /api/books/{id}` — book details
- `POST /api/books` — create a book
- `POST /api/users/register` — register a user
- `POST /api/users/login` — check credentials (demo only; no session/token is issued)
- `POST /api/reservations` — reserve a quantity of a book and decrease stock transactionally
- `GET /api/users/{id}/reservations` — reservations for a user
- `POST /api/books/{id}/reviews` — create a review
- `GET /api/books/{id}/reviews` — list reviews

Example book JSON:
```json
{
  "isbn": "9780132350884",
  "title": "Clean Code",
  "authors": "Robert C. Martin",
  "description": "A handbook of agile software craftsmanship.",
  "price": 34.99,
  "stock": 5
}
```

Example reservation JSON:
```json
{
  "userId": 1,
  "bookId": 1,
  "quantity": 1
}
```

Example review JSON:
```json
{
  "userId": 1,
  "score": 5,
  "text": "Clear and useful."
}
```

## Important limitations to disclose
- This is a small academic demo, not a production application.
- Login validates credentials but does not issue a secure session or JWT. Do not use it for real accounts.
- The reservation API records a reservation and reduces stock; the assignment assumes reservations are collected.
- The demo review endpoint checks that the user/book exist but does not enforce purchase history yet. Add this rule if time permits.
- `ddl-auto=update` is convenient for local development, not a production migration strategy.
- The UI is intentionally basic and does not yet implement every account-management flow.

## Planning
See `docs/planning.md`.

## Architecture
See `docs/architecture.md`.

## Tests
See `docs/testing.md`. Tests should be run and their actual result recorded before submission; do not claim they passed unless you ran them.

## References and tools
The project structure and implementation were prepared with assistance from ChatGPT (OpenAI), used for code scaffolding, explanations and documentation. The student must review, understand, test and adapt the code before submission.

- Spring guide — Building a RESTful Web Service: https://spring.io/guides/gs/rest-service/
- Spring Boot system requirements: https://docs.spring.io/spring-boot/system-requirements.html
- Spring Boot guide — Building an Application: https://spring.io/guides/gs/spring-boot/
- MySQL Connector/J Maven setup: https://dev.mysql.com/doc/connector-j/en/connector-j-installing-maven.html
- JUnit 5 user guide: https://junit.org/junit5/docs/current/user-guide/

Accessed: 28 October 2026.

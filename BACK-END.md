# Museum Codenames – Back-end

## Overview

The back-end is built using **Spring Boot 4.0.2** and is responsible for:

- Game logic (hint phase ↔ selection phase)
- Managing game state
- Integration with the external museum API (AIC)
- Database communication (MySQL)
- Exposing REST endpoints to the front-end

---

## Tech Stack

- Java 21 (BellSoft Liberica JDK Full)
- Spring Boot 4.0.2
- Maven
- MySQL 8.4
- Docker (for database)

---

## Running the Back-end

### 1. Start the database

From the project root:

```bash
docker compose up -d
````

---

### 2. ???

## Configuration

Application configuration is handled via:

```
src/main/resources/application.properties
```

> Environment variable setup is currently **TBD** and will be documented once finalized.

---


## Future Improvements (TBD)

* API documentation
* Docker setup

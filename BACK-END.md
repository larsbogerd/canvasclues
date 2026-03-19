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

To wipe and compose a fresh database, use the provided scripts from the project root:

- **Mac/Linux:** `./clean-db.sh`
- **Windows:** `clean-db.bat`

---
### 2. Run the application

Open the project in IntelliJ and run `NamesApplication`.
The back-end starts on port **8082**.

---

## Package Structure

### `art` – Artwork fetching
Calls the Art Institute of Chicago API, maps responses to `ArtWork` entities, and exposes a random artwork endpoint (16 IIIF image URLs).

### `game` – Game logic & persistence
Creates games by pulling random artworks into `GameCard` entities, persists them to MySQL, and retrieves them by `gameId`.

### `helloworld` – Legacy/sandbox
Used for initial setup and testing. Not part of the game flow. Contains the `ArtPiece` entity and endpoints.

### `utils` – Utility classes
Shared utilities used across the application.

---

## API Endpoints

### Game

| Method | Endpoint                | Description                                                                                             |
|--------|-------------------------|---------------------------------------------------------------------------------------------------------|
| POST   | `/api/v1/game/start`    | Start a new game. Creates 16 cards, returns them with a shared `gameId` and stores them in the database |
| GET    | `/api/v1/game/{gameId}` | Retrieve an existing game's cards (ordered by position)                                                 |
| PATCH  | `/api/v1/game/updatecards` | Update card selections. Accepts `{ cardIds: [...], spymasterPick: bool }` — sets whichever field is provided |
| POST   | `/api/v1/game/checkcards`  | Compare spy and operative card choices and return validation results |

### Hint

| Method | Endpoint                 | Description                                                        |
|--------|--------------------------|--------------------------------------------------------------------|
| GET    | `/api/v1/hints/{gameId}` | Retrieve the hint for a specific game. Returns 404 if none exists  |
| POST   | `/api/v1/hints`          | Create a hint. Accepts `{ gameId: int, hintContent: string }`      |

### Artwork (testing purposes)

| Method | Endpoint                    | Description                                                               |
|--------|-----------------------------|---------------------------------------------------------------------------|
| GET    | `/api/v1/artworks/test?size=25` | Fetch artworks from AIC API using ArtWork model. Size defaults to 25 |

---

## Configuration

Application configuration is handled via:

```
src/main/resources/application.properties
```

Key properties:
- `spring.datasource.url` – MySQL connection URL
- `spring.jpa.hibernate.ddl-auto=update` – Hibernate auto-creates/updates tables
- `spring.sql.init.mode=always` + `spring.jpa.defer-datasource-initialization=true` – runs `data.sql` after Hibernate sets up the schema

### Database Seeding

`src/main/resources/data.sql` seeds the database on every startup after Hibernate has created/updated the tables from the entity classes.

---


## Spring Data JPA – Query Methods

Spring Data generates SQL queries from repository method names. No manual SQL needed.

Examples:
- `findByGameId(int gameId)` → `SELECT * FROM game_cards WHERE game_id = ?`
- `findByGameIdOrderByPositionAsc(int gameId)` → same, ordered by position
- `findByGameIdAndIsSpymasterPick(int gameId, boolean isSpymasterPick)` → filter by game and spymaster selection

Documentation: https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html

---

## Future Improvements (TBD)

* Guess/operative phase logic
* Card color assignment (`GameCardColor` enum is ready)
* API documentation

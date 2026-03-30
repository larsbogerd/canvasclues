# Museum Codenames – Back-end

## Overview

The back-end is built using **Spring Boot 4.0.2** and is responsible for:

- Game setup and persistence
- Managing game state transitions
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
```

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
Calls the Art Institute of Chicago API, maps responses to internal DTOs and models, and exposes a test endpoint for fetching random artworks.

### `game` – Game logic & persistence
Contains controllers, DTOs, mappers, entities, repositories, services, and exception handling for:
- `Game`
- `Card`
- `Hint`
- `GameState`
- `CardType`

### `utils` – Utility classes
Shared utilities used across the application.

---

## Domain Model

- `Game` uses an integer primary key and tracks `state`, `maxScore`, `createdAt`, related `cards`, and related `hints`.
- `Card` uses a UUID primary key and belongs to a `Game`.
- `Hint` uses a UUID primary key and belongs to a `Game`.
- `GameState` currently supports `CREATING`, `READY`, and `ARCHIVED`.
- `CardType` currently supports `TEAM1`, `TEAM2`, `NEUTRAL`, and `ASSASSIN`.

The branch refactors renamed older `GameCard` and phase naming to the current `Card` and `GameState` model.

---

## API Endpoints

### Game

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/v1/game/start` | Creates a new `Game`, fetches 16 artworks, stores them as `Card` records, and returns a `CardResponse[]` |
| GET | `/api/v1/game/{gameId}` | Returns the stored `CardResponse[]` for a game |
| PATCH | `/api/v1/game/updatecards` | Updates selected cards using `{ "cardIds": ["uuid"], "spymasterPick": true }` |
| POST | `/api/v1/game/checkcards` | Accepts a JSON array of card UUIDs and returns a map of `cardId -> isSpymasterPick` |

### Hint

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/hints/{gameId}` | Returns the hint for a game as `HintResponse`; responds with `404` if none exists |
| POST | `/api/v1/hints` | Creates a hint using `{ "gameId": 1, "content": "museum" }` and moves the game to `READY` |

### Artwork (testing purposes)

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/artworks/test?size=25` | Fetches artwork data from the AIC API. `size` defaults to `25` |

---

## Configuration

Application configuration is handled via:

```
src/main/resources/application.properties
```

Key properties:
- `spring.datasource.url` – MySQL connection URL
- `spring.datasource.username` / `spring.datasource.password` – database credentials
- `server.port=8082` – application port
- `spring.jpa.hibernate.ddl-auto=create` – recreates tables on startup
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

- Assign `CardType` values during game creation
- Add scoring system that write the `Game`
- Replace generic runtime exceptions with structured API errors
- Add integration tests for the game and hint endpoints
- Add OpenAPI or similar API documentation

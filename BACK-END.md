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

### `art` – Artwork fetching & persistence
Calls the Art Institute of Chicago API, maps responses to the `Artwork` entity, and persists artworks so their gameplay usage (times loaded, times picked by spymaster, correct/bad guesses, first/last used) can be tracked for the admin dashboard.

### `game` – Game logic & persistence
Contains controllers, DTOs, mappers, entities, repositories, services, and exception handling for:
- `Game`
- `Card` (now references an `Artwork` by UUID rather than inlining its fields)
- `Hint`
- `GameState`
- `CardType`

A dedicated `SubmitService` handles the unified spymaster submit flow (cards + hint + max score in one call).

### `utils` – Utility classes
Shared utilities used across the application.

---

## Domain Model

- `Game` uses an integer primary key and tracks `state`, `maxScore`, `playCount`, `createdAt`, related `cards`, and related `hints`.
- `Card` uses a UUID primary key, belongs to a `Game`, and references an `Artwork` via `artwork_id`.
- `Hint` uses a UUID primary key and belongs to a `Game`.
- `Artwork` uses a UUID primary key (the ArtIC `image_id`) and stores the artwork's metadata (title, artist, date, medium, place of origin, dimensions, department) plus usage counters (`timesLoaded`, `timesSpymasterPick`, `timesCorrectGuess`, `timesBadGuess`) and timestamps (`firstUsedAt`, `lastUsedAt`).
- `GameState` currently supports `CREATING`, `READY`, and `ARCHIVED`.
- `CardType` currently supports `TEAM1`, `TEAM2`, `NEUTRAL`, and `ASSASSIN`.

`Artwork` was split off from `Card` so the same artwork can accumulate usage stats.

---

## API Endpoints

### Game

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/v1/game/start` | Creates a new `Game`, fetches 16 artworks, stores them as `Card` records, and returns a `CardResponse[]` |
| GET | `/api/v1/game/{gameId}` | Returns the stored `CardResponse[]` for a game and increments its `playCount` |
| GET | `/api/v1/game/list` | Returns all games in `READY` state as `GameResponse[]` (used by the operative hub) |
| POST | `/api/v1/game/{gameId}/submit` | Unified spymaster submit. Body: `{ "cardIds": ["uuid"], "maxScore": 3, "hintContent": "museum" }`. Flags picked cards, sets `maxScore`, creates the `Hint`, and moves the game to `READY`. |
| POST | `/api/v1/game/checkcards` | Accepts a JSON array of card UUIDs and returns a map of `cardId -> isSpymasterPick` |

### Hint

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/hints/{gameId}` | Returns the hint for a game as `HintResponse`; responds with `404` if none exists |

### Artwork

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/artwork/test?size=25` | Fetches random artworks from the AIC API for testing. `size` defaults to `25` |
| GET | `/api/v1/artwork/statslist` | Returns every persisted `Artwork` as `ArtworkStatsListResponse[]`, including usage counters and `pickPercentage`. Used by the admin dashboard table. |
| GET | `/api/v1/artwork/{id}/stats` | Returns a single artwork's full detail + usage stats as `ArtworkStatsResponse`. Used by the admin detail page. |

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
- Replace generic runtime exceptions with structured API errors
- Add more integration tests for the game and hint endpoints
- Add OpenAPI or similar API documentation

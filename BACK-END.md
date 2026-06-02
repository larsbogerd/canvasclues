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
Calls the Art Institute of Chicago API, maps responses to the `Artwork` entity, and persists artworks so their gameplay usage (times loaded, times picked by spymaster, correct/bad guesses, first/last used) can be tracked for the statistics dashboard.

### `game` – Game logic & persistence
Contains controllers, DTOs, mappers, entities, repositories, services, and exception handling for:
- `Game`
- `Card` (now references an `Artwork` by UUID rather than inlining its fields)
- `Hint`
- `Session` (one row per Operative round — `started_at`, `finished_at`, `score`, FK to `Game`, nullable FK to `Hint`)
- `GameState`
- `CardType`

The `game.service` package is split into focused services:
- `GameService` – owns the `Game` aggregate (`createGame`, `markReady`, `updateMaxScore`, `getGameList`)
- `CardService` – board building and card lookup/mutation (`buildBoard`, `updateCards`, `getCardInGame`)
- `HintService` – hint creation and lookup
- `StartGameService` – orchestrates `GameService` + `ArtworkService` + `CardService` for `POST /start`
- `BoardSubmitService` – orchestrates the unified spymaster submit flow (flag cards → set maxScore → create hint → record artwork usage → mark game ready)
- `SessionService` – owns the Operative session lifecycle: `start` creates a `Session` for a game (links the current hint, increments `playCount`) and bundles the board, hint, and Spymaster-pick count into a single response; `getActiveSession` validates a session is still in progress; `recordGuess` increments the score on a correct guess; `finish` stamps `finishedAt` and flips state to `FINISHED`
- `GuessSubmitService` – orchestrates per-guess persistence (validate session in progress → validate card belongs to the session's game → `SessionService.recordGuess` + `ArtworkService.recordGuessUsage`)

API errors follow a small polymorphic hierarchy in `game.exception`:
- `ResourceNotFoundException` (abstract → 404) — `GameNotFoundException`, `SessionNotFoundException`, `CardNotFoundException`, `HintNotFoundException`, `ArtworkNotFoundException`
- `ConflictException` (abstract → 409) — `SessionAlreadyFinishedException`
- `BadRequestException` (abstract → 400) — `CardNotInSessionGameException`

Game difficulty is set by the classes EasyGameModeService & HardGameModeService using the same methods set by interface GameMode:
Add another gamemode by duplicating a previous GameModeService class and adjust the methods to whatever the gamemode needs
- `gameModeName`
- `BoardSize`
- `getCardDistribution`
- `handleSpyScore`
- `calcOperativeScore`
- `getMultiplier`
- `recordGuess`



`game.handlers.ApiExceptionHandler` has one `@ExceptionHandler` per abstract parent. Spring dispatches on the runtime subclass, so adding a new concrete exception needs no handler change.

### `art/util` – Utility classes
`IiifUrlBuilder` builds the IIIF image URL from an artwork id; used by both `ArtworkMapper` and `CardMapper` so the URL format lives in one place.

---

## Domain Model

- `Game` uses a `Long` primary key and tracks `state`, `maxScore`, `playCount`, `createdAt`, related `cards`, and related `hints`.
- `Card` uses a UUID primary key, belongs to a `Game`, and references an `Artwork` via `artwork_id`.
- `Hint` uses a UUID primary key and belongs to a `Game`.
- `Session` uses a UUID primary key, belongs to a `Game` (required), and optionally references the `Hint` that was active at start. `startedAt` is set by Hibernate via `@CreationTimestamp`. `state` (`SessionState` enum: `IN_PROGRESS` / `FINISHED`) starts at `IN_PROGRESS` and flips to `FINISHED` when the Operative ends their attempt. `score` is incremented server-side on each correct guess via the per-guess endpoint, so it's authoritative without the client ever sending a total. `finishedAt` is stamped when `finish` is called.
- `Artwork` uses a UUID primary key (the ArtIC `image_id`) and stores the artwork's metadata (title, artist, date, medium, place of origin, dimensions, department) plus usage counters (`timesLoaded`, `timesSpymasterPick`, `timesCorrectGuess`, `timesBadGuess`) and timestamps (`firstUsedAt`, `lastUsedAt`).
- `GameState` currently supports `CREATING`, `READY`, and `ARCHIVED`.
- `CardType` currently supports `ASSASIN`, `PLAYABLE`.
- `SessionState` currently supports `IN_PROGRESS` and `FINISHED`.

`Artwork` was split off from `Card` so the same artwork can accumulate usage stats.

---

## API Endpoints

### Game

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/v1/game/start` | Creates a new `Game`, fetches 16 artworks, stores them as `Card` records, and returns a `CardResponse[]` |
| GET | `/api/v1/game/list` | Returns all games in `READY` state as `GameResponse[]` (used by the operative hub) |
| POST | `/api/v1/game/{gameId}/submit` | Unified spymaster submit. Body: `{ "cardIds": ["uuid"], "maxScore": 3, "hintContent": "museum" }`. Flags picked cards, sets `maxScore`, creates the `Hint`, and moves the game to `READY`. |

### Session

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/v1/session/{gameId}/start` | Creates a new `Session` for the given game, increments `playCount`, and returns a `SessionResponse` with `sessionId`, `cards`, the active `hint` (nullable), and `spymasterPickCount`. Used by the Operative to open a board. Each call creates a new session row. `404` if `gameId` doesn't exist. |
| POST | `/api/v1/session/{sessionId}/guess/{cardId}` | Persists a single Operative guess. Increments `session.score` if correct, and bumps `artwork.timesCorrectGuess` / `timesBadGuess`. Returns `{ correct, score }`. `404` if session or card not found, `400` if the card doesn't belong to the session's game, `409` if the session is already finished. |
| POST | `/api/v1/session/{sessionId}/finish` | Stamps `finishedAt` and flips state to `FINISHED`. No body. `404` if `sessionId` doesn't exist, `409` if the session is already finished. |

### Hint

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/hints/{gameId}` | Returns the hint for a game as `HintResponse`; responds with `404` if none exists |

### Artwork

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/artwork/test?size=25` | Fetches random artworks from the AIC API for testing. `size` defaults to `25` |
| GET | `/api/v1/artwork/statslist` | Returns every persisted `Artwork` as `ArtworkStatsListResponse[]`, including usage counters and `pickPercentage`. Used by the statistics dashboard table. |
| GET | `/api/v1/artwork/{id}/stats` | Returns a single artwork's full detail + usage stats as `ArtworkStatsResponse`. Used by the statistics detail page. |

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

### Setup your configuration

#### Create .env file:
- `Step 1:` create .env file called: .env instead of database_info.env in project root folder.
- `Step 2:` Add the variables from the example.env.
- `Step 3:` Fill in the proper credentials.

#### Setup for application.properties:
Fix notes for bug #173256: Please follow these steps once again and reroute to the
.env file instead of database_info.env
- `Step 1:` Head to your current run configuration in my case that is `NameApplication`
- `Step 2:` Click more actions the triple dot next to. `NameApplication`
- `Step 3:` Click on edit at the bottom. `
- A window should pop-up called Run/Debug configurations
- `Step 4:` Use the Hotkey on windows alt + E or on MacOS I assume CMD + E 
- `Step 5:` A new bar should pop-up with environment variables.
- `Step 6:` Choose the folder icon -> + in the pop-up window after
- `Step 7:` Navigate to the project folder on your system en select the created .env file.


### Database Seeding

`src/main/resources/data.sql` seeds the database on every startup after Hibernate has created/updated the tables from the entity classes.

---


## Spring Data JPA – Query Methods

Spring Data generates SQL queries from repository method names. No manual SQL needed.

Examples:
- `findByGame_Id(Long gameId)` → `SELECT * FROM card WHERE game_id = ?` (used in `CardRepository` and `HintRepository`; the underscore tells Spring Data to navigate the `Card.game` relation and match on `Game.id`)
- `findByState(GameState state)` → `SELECT * FROM game WHERE state = ?` (used in `GameRepository` for the operative hub list)

Documentation: https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html

---

## Future Improvements (TBD)

- Add more integration tests for the game and hint endpoints
- Add OpenAPI or similar API documentation
- Wire the unused `HintNotFoundException` and `ArtworkNotFoundException` into the services that fetch those entities

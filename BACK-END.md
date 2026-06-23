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
- `GameService` – owns the `Game` aggregate (create, mark ready, game list, like/dislike quality rating)
- `CardService` – board building and card lookup/mutation (`buildBoard`, `updateCards`, `getCardInGame`)
- `HintService` – hint creation, lookup, and hint occurrence statistics
- `ScoreService` – implements the `Scoring` interface: spymaster score calculation/validation and per-guess score updates (combo streak, wrong/assassin guess counters)
- `SessionService` – owns the Operative session lifecycle: `start` creates a `Session` for a game (links the current hint, increments `playCount`) and bundles the board, hint, and Spymaster-pick count into a single response; `randomStart` picks a random `READY` game for a given difficulty; `finish` stamps `finishedAt` and flips state to `FINISHED`
- `GameModeRegistry` – resolves a `GameMode` strategy by name; throws `UnknownGameModeException` (400) for unknown modes

Flows that span multiple services live in `game.orchestrator`:
- `StartGameOrchestrator` – orchestrates `GameService` + `ArtworkService` + `CardService` for `POST /game/start`
- `BoardSubmitOrchestrator` – orchestrates the unified spymaster submit flow (flag cards → validate & set spy score → create hint → record artwork usage → mark game ready)
- `GuessSubmitOrchestrator` – orchestrates per-guess persistence (validate session in progress → validate card belongs to the session's game → update score/streak via `ScoreService` → record artwork usage)

API errors follow a small polymorphic hierarchy in `game.exception`:
- `ResourceNotFoundException` (abstract → 404) — `GameNotFoundException`, `SessionNotFoundException`, `CardNotFoundException`, `HintNotFoundException`, `ArtworkNotFoundException`
- `ConflictException` (abstract → 409) — `SessionAlreadyFinishedException`
- `BadRequestException` (abstract → 400) — `CardNotInSessionGameException`, `MinimumSelectedException`, `NullScoreException`, `UnknownGameModeException`

Game difficulty uses the strategy pattern. The `GameMode` interface (`game.interfaces`) is implemented by `EasyModeStrategy`, `MediumModeStrategy`, and `HardModeStrategy` (`game.strategy`), registered by name (`makkelijk`, `gemiddeld`, `moeilijk`) in `GameModeRegistry`. Each strategy defines:
- `gameMode()` – the mode name used in the API and database
- `boardSize()` – number of cards on the board (currently 16 for all modes)
- `getCardDistribution()` – the list of `CardType`s to deal (e.g. how many assassins)

Scoring rules live in the separate `Scoring` interface (`handleSpyScore`, `calcOperativeScore`, `getMultiplier`, `recordGuess`), implemented by `ScoreService`.

Add another game mode by creating a new strategy class that implements `GameMode`; Spring injects all implementations into the registry automatically, so no other code changes are needed.



`game.handlers.ApiExceptionHandler` has one `@ExceptionHandler` per abstract parent. Spring dispatches on the runtime subclass, so adding a new concrete exception needs no handler change.

### `art/util` – Utility classes
`IiifUrlBuilder` builds the IIIF image URL from an artwork id; used by both `ArtworkMapper` and `CardMapper` so the URL format lives in one place.

---

## Domain Model

- `Game` uses a `Long` primary key and tracks `state`, `spyScore`, `playCount`, `gameMode`, `likes`, `dislikes`, `createdAt`, related `cards`, and related `hints`.
- `Card` uses a UUID primary key, belongs to a `Game`, and references an `Artwork` via `artwork_id`.
- `Hint` uses a UUID primary key and belongs to a `Game`.
- `Session` uses a UUID primary key, belongs to a `Game` (required), and optionally references the `Hint` that was active at start. `startedAt` is set by Hibernate via `@CreationTimestamp`. `state` (`SessionState` enum: `IN_PROGRESS` / `FINISHED`) starts at `IN_PROGRESS` and flips to `FINISHED` when the Operative ends their attempt. `score` is incremented server-side on each correct guess via the per-guess endpoint, so it's authoritative without the client ever sending a total. The session also tracks `comboStreak`, `wrongGuesses`, and `assassinGuesses`. `finishedAt` is stamped when `finish` is called.
- `Artwork` uses a UUID primary key (the ArtIC `image_id`) and stores the artwork's metadata (title, artist, date, medium, place of origin, dimensions, department) plus usage counters (`timesLoaded`, `timesSpymasterPick`, `timesCorrectGuess`, `timesBadGuess`) and timestamps (`firstUsedAt`, `lastUsedAt`).
- `GameState` currently supports `CREATING`, `READY`, and `ARCHIVED`.
- `CardType` currently supports `ASSASSIN`, `PLAYABLE`.
- `SessionState` currently supports `IN_PROGRESS` and `FINISHED`.

`Artwork` was split off from `Card` so the same artwork can accumulate usage stats.

---

## API Endpoints

### Game

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/v1/game/start?gameMode={mode}` | Creates a new `Game` for the given mode (`makkelijk` / `gemiddeld` / `moeilijk`), fetches artworks via the mode's card distribution, stores them as `Card` records, and returns a `CardResponse[]`. `400` for an unknown mode. |
| GET | `/api/v1/game/list` | Returns all games in `READY` state as `GameResponse[]` including `spyScore`, `playCount`, `qualityRatio`, and `gameMode` (used by the operative hub) |
| POST | `/api/v1/game/{gameId}/submit` | Unified spymaster submit. Body: `{ "cardIds": ["uuid"], "spyScore": 3, "hintContent": "museum" }`. Flags picked cards, sets the spy score, creates the `Hint`, and moves the game to `READY`. |
| PATCH | `/api/v1/game/game-quality/submit` | Records a like/dislike for a game. Body: `{ "gameId": 1, "rating": true }` (`true` = like, `false` = dislike). Used by the post-game rating modal. |

### Session

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/v1/session/{gameId}/start` | Creates a new `Session` for the given game, increments `playCount`, and returns a `SessionResponse` with `sessionId`, `cards`, the active `hint` (nullable), and `spymasterPickCount`. Used by the Operative to open a board. Each call creates a new session row. `404` if `gameId` doesn't exist. |
| POST | `/api/v1/session/start/random/{difficulty}` | Picks a random `READY` game for the given difficulty and returns its `gameId`. Used by the "random game" entry points in the operative hub. |
| POST | `/api/v1/session/{sessionId}/guess/{cardId}` | Persists a single Operative guess. Updates `score`, `comboStreak`, `wrongGuesses`, and `assassinGuesses`, and bumps `artwork.timesCorrectGuess` / `timesBadGuess`. Returns `{ correct, score, comboStreak, wrongGuesses, assassinGuesses, cardType }`. `404` if session or card not found, `400` if the card doesn't belong to the session's game, `409` if the session is already finished. |
| POST | `/api/v1/session/{sessionId}/finish` | Stamps `finishedAt` and flips state to `FINISHED`. No body. Returns `{ score, wrongGuesses, assassinGuesses }`. `404` if `sessionId` doesn't exist, `409` if the session is already finished. |

### Hint

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/hints/{gameId}` | Returns the hint for a game as `HintResponse`; responds with `404` if none exists |
| GET | `/api/v1/hints/stats` | Returns hint occurrence statistics as `HintStatsListResponse[]`. Used by the hint statistics dashboard. |

### Artwork

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/artwork/test?size=25` | Fetches random artworks from the AIC API for testing. `size` defaults to `25` |
| GET | `/api/v1/artwork/statslist` | Returns every persisted `Artwork` as `ArtworkStatsListResponse[]`, including usage counters and `pickPercentage`. Used by the statistics dashboard table. |
| GET | `/api/v1/artwork/{id}/stats` | Returns a single artwork's full detail + usage stats as `ArtworkStatsResponse`. Used by the statistics detail page. |
| GET | `/api/v1/artwork/{id}/details` | Returns full artwork metadata as `ArtworkDetailsResponse`. Used by the art-info popup on the board. |
| GET | `/api/v1/artwork/details?ids=a,b,c` | Batch variant of the details endpoint; used by the front-end to prefetch details for a whole board. |

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

#### Create a .env file
- `Step 1:` Copy `.env.example` in the project root to a new file called `.env`.
- `Step 2:` Fill in the proper credentials.

#### Load the .env file in IntelliJ
- `Step 1:` Open the run configuration for `NamesApplication` (Run → Edit Configurations…).
- `Step 2:` In the **Environment variables** field, click the folder icon and select the `.env` file in the project root.
- `Step 3:` Apply and run the application.


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

# Museum Codenames – Front-end

## Overview

The front-end is built using **Vue** and is responsible for:

- rendering the landing page, game hub, operative hub, admin hub, and phase screens
- browsing available puzzles with sorting and search
- starting a new game for the spymaster flow
- sending selected cards and hints to the back-end
- opening and finishing an Operative session for an existing game

---

## Tech Stack

- html 5
- css 3
- javascript ES6+
- vue v3
- vite
- vue-router (hash-based routing)
- axios (HTTP client)

---

## Running the Front-end

### 1. Start the front-end

From the project root:
> make sure the back-end is running
```bash
cd src/front-end
npm install 
npm run dev
````

---

## Configuration

front-end configuration is handled via:

```
src/front-end/vite.config.js
```

---

## Routing

Routes are defined in `src/front-end/src/router.js` using hash-based routing (`createWebHashHistory`).

| Route                        | Component        | Description |
|------------------------------|------------------|-------------|
| `/`                          | `StartingPage`   | Landing page with the project intro and rules popup |
| `/game`                      | `GameHub`        | Role selection screen for spymaster or operative |
| `/game/spymaster`            | `SpymasterPhase` | Starts a new game, shows the 4x4 grid, lets the spymaster select cards and submit a one-word hint |
| `/game/operative`            | `OperativeHub`   | Browse available puzzles with sorting (newest, popular, difficulty) and search |
| `/game/operative/:gameId`    | `OperativePhase` | Loads a game's grid and hint, then lets the operative validate selected cards |
| `/admin`                     | `AdminHub`       | Admin landing page |
| `/admin/artworks`            | `AdminArtworks`  | Lists every persisted artwork with usage counters, search, pagination, and PDF export |
| `/admin/artworks/:artworkId` | `ArtworkStats`   | Detail view for a single artwork: image, metadata, usage stats, and boards it appeared in |

---

## Current Flow

### Spymaster phase

- On mount, the page calls `POST /api/v1/game/start`.
- The returned cards are rendered in the grid.
- On submit, selected card IDs, max score, and hint text are sent together in one call to `POST /api/v1/game/{gameId}/submit`, which flags the picks, stores the hint, and moves the game to `READY`.

### Operative hub

- On mount, the page calls `GET /api/v1/game/list` to fetch all available puzzles.
- Games can be sorted by newest, popularity (`playCount`), or difficulty (`maxScore`).
- A search field filters puzzles by hint text (case-insensitive).
- Clicking a puzzle navigates to `/game/operative/:gameId`.

### Operative phase

- On mount, the page calls `POST /api/v1/session/{gameId}/start`. The single response carries the new `sessionId`, the board (`cards`), the active `hint`, and `spymasterPickCount` (the number of cards the Operative needs to find). No separate hint call is made.
- The `sessionId` is held in a local ref for the finish call.
- Selected card IDs are checked with `POST /api/v1/game/checkcards`.
- On "Beëindig poging", `POST /api/v1/session/{sessionId}/finish` is called with the final score before the result modal is shown.

### Admin dashboard

- `/admin/artworks` calls `GET /api/v1/artwork/statslist` on mount and renders every persisted artwork in a paginated, filterable table with usage counters and pick-ratio.
- Clicking a row navigates to `/admin/artworks/:artworkId`, which calls `GET /api/v1/artwork/{id}/stats` and renders the full-size image, metadata (date, medium, dimensions, department, origin), and usage stats.

---

## Back-end Connection

The current views call the API directly with `axios` against:

```text
http://localhost:8082/api/v1
```

If the back-end host or port changes, update the request URLs in the page components.

---

## Future Improvements (TBD)

* Changelog
* mobile support
* dark mode
* sound


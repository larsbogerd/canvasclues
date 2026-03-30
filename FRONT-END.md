# Museum Codenames – Front-end

## Overview

The front-end is built using **Vue** and is responsible for:

- rendering the landing page, game hub, and phase screens
- starting a new game for the spymaster flow
- sending selected cards and hints to the back-end
- loading an existing game's cards and hint for the operative flow

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

| Route        | Component        | Description |
|--------------|------------------|-------------|
| `/`          | `StartingPage`   | Landing page with the project intro and rules popup |
| `/gamehub`   | `GameHub`        | Role selection screen for spymaster or operative |
| `/spy`       | `SpymasterPhase` | Starts a new game, shows the 4x4 grid, lets the spymaster select cards and submit a one-word hint |
| `/operative` | `OperativePhase` | Loads a game's grid and hint, then lets the operative validate selected cards |

---

## Current Flow

### Spymaster phase

- On mount, the page calls `POST /api/v1/game/start`.
- The returned cards are rendered in the grid.
- Selected card IDs are sent to `PATCH /api/v1/game/updatecards`.
- The clue is sent to `POST /api/v1/hints`.

### Operative phase

- The page requests the hint from `GET /api/v1/hints/{gameId}`.
- The grid is requested from `GET /api/v1/game/{gameId}`.
- Selected card IDs are checked with `POST /api/v1/game/checkcards`.

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


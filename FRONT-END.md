# Museum Codenames – Front-end

## Overview

The front-end is built using **Vue** and is responsible for:

- rendering the game
- role management
- communicating with the back-end

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

### 1. Start the Front end

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

| Route         | Component        | Description               |
|---------------|------------------|---------------------------|
| `/`           | StartingPage     | Landing / start page      |
| `/Spy`        | SpymasterPhase   | Spymaster hint phase      |
| `/Operative`  | OperativePhase   | Operative guessing phase  |

---

## Future Improvements (TBD)

* Changelog
* mobile support
* dark mode
* sound


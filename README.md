# Canvas Clues

Canvas Clues is a word-association guessing game played with pictures from real museum
objects instead of words. Artworks are dynamically loaded via an external museum API.

The game is played on a 4x4 grid containing 16 museum object images.  
Each round consists of two phases:

1. **HINT Phase (Spymaster)** – The Spymaster analyzes the grid, selects one or more related images, and provides a single-word clue. After confirming, the game transitions automatically to the next phase.

2. **PICK Phase (Operative)** – The Operative sees the clue and the number of linked images, then selects the images they believe match the hint before ending the turn.

Games are created in one of three difficulty modes — *makkelijk*, *gemiddeld*, or *moeilijk* — which determine the card distribution (such as the number of assassin cards) and the scoring. After finishing a puzzle, players can rate it with a like or dislike, and a statistics dashboard tracks how artworks and hints are used across all games.

The goal of this project is to combine external API integration, back-end game logic, and a modern front-end into one interactive web application.

---

# Getting Started

This section explains how to set up the project locally and run it on your own system.

## Tech Stack

### Front-end
- **Framework:** Vue 3 + Vite  
- **Language:** JavaScript  
- **Routing:** Vue Router  
- **UI:** HTML5 + CSS3

### Back-end
- **Framework:** Spring Boot 4.0.2  
- **Java Version:** BellSoft Liberica JDK 21 (Full)  
- **Build Tool:** Maven  
- **Database:** MySQL 8.4  

---

## Required Software

Make sure the following tools are installed:

- **Docker**
- **Node.js**

---

## Software Dependencies

Back-end dependencies are managed via **Maven** (`pom.xml`).  
Front-end dependencies are managed via **npm** (`package.json`).

---

## API references

For the MVP we use image and object metadata from the **Art Institute of Chicago (AIC) API**.
AIC exposes images using the **IIIF Image API**, which makes it easy to scale later to other IIIF-compatible collections (same structure, different source).

Documentation:

* AIC API documentation: [https://api.artic.edu/docs/](https://api.artic.edu/docs/)

* IIIF Image API (v3.0): [https://iiif.io/api/image/3.0/](https://iiif.io/api/image/3.0/)

---

# Build and Test

There are two ways to run the project:

- **Local development** — back-end in IntelliJ + the database in Docker + the Vite dev
  server. Best for working on the code (hot reload, debugger). Steps 1–3 below.
- **Full stack in Docker** — everything (database, back-end, front-end) in one command.
  Best for just running the app. See [Run the full stack with Docker](#run-the-full-stack-with-docker).

Detailed setup and architecture notes for each app are documented in:

- `/docs/BACK-END.md`
- `/docs/FRONT-END.md`


## 1) Start the database (Docker)

From the root of the repository (where `compose.yaml` is located):

```bash
docker compose up -d
```

To wipe and compose a fresh database, use the provided scripts from the project root:

- **Mac/Linux:** `./clean-db.sh`
- **Windows:** `clean-db.bat`

This stops containers (if still running), cleans the database, and starts fresh.

---

## 2) Run the back-end (Spring Boot)

Open the project in IntelliJ and run `NamesApplication`.
The back-end starts on port **8082**.

---

## 3) Run the front-end (Vue)

From the project root:
> Make sure the back-end is running first.

```bash
cd src/front-end
npm install
npm run dev
```

The Vite dev server runs on port **5173** by default.

---
Stop the containers when finished:

```bash
docker compose down
```
---

## Run the full stack with Docker

Instead of the three steps above, you can build and run the **whole app** (database +
back-end + front-end) with a single command. First copy `.env.example` to `.env` and
fill it in, then from the project root:

```bash
docker compose -f docker-compose.prod.yaml up --build
```

- `-f docker-compose.prod.yaml` selects the full-stack compose file (instead of the
  default `compose.yaml`, which only starts the database).
- `--build` (re)builds the images from source — run it again after pulling new code.
- Add `-d` to run in the background.

Then open **http://localhost:8080**. The front-end is served by nginx, which also
proxies `/api` calls to the back-end, so no separate Vite server is needed.

Stop it with:

```bash
docker compose -f docker-compose.prod.yaml down
```

---

# Contribute

## Branching

Create a branch from `main` using the correct prefix folder:

* `/feat`
* `/fix`
* `/chore`
* `/docs`
* `/refactor`

Include the **Azure DevOps User Story ID** and a clear scope description.

Examples:

* `feat/158187-inputfield-handling`
* `fix/158233-null-pointer-on-startup`
* `chore/158120-update-readme`
* `refactor/158301-clean-game-service`
* `docs/158099-add-api-documentation`

If possible, open the branch directly from the Azure Board user story so the ID is included automatically.

---

### Commits

We follow the **Conventional Commits** standard to keep history structured and readable.

Format: `type(scope): short description`

Examples:

* `feat(game): add museum card selection logic`
* `fix(database): resolve MySQL connection issue`
* `chore(docker): update docker compose configuration`
* `docs(readme): improve setup instructions`
* `refactor(service): split game state handling`

More information:
https://www.conventionalcommits.org/en/v1.0.0/#summary

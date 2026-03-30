# Museum Codenames

In this project we will build a digital version of the board game **Codenames**.  
Instead of using words, the game uses pictures from real museum objects that are dynamically loaded via an external museum API.

The game is played on a 4x4 grid containing 16 museum object images.  
Each round consists of two phases:

1. **HINT Phase (Spymaster)** – The Spymaster analyzes the grid, selects one or more related images, and provides a single-word clue. After confirming, the game transitions automatically to the next phase.

2. **PICK Phase (Operative)** – The Operative sees the clue and the number of linked images, then selects the images they believe match the hint before ending the turn.

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

Detailed setup and architecture notes for each app are documented in:

- `BACK-END.md`
- `FRONT-END.md`


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

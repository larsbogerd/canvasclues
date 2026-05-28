<script setup>
import PageHeader from "@/components/header/PageHeader.vue";
import router from "@/router.js";
import { ref, onMounted } from "vue";
import { getArtworkStats } from "@/assets/composables/AdminStatsService.js";

const artworkId = router.currentRoute.value.params.artworkId;

const artwork = ref(null);

onMounted(async () => {
  artwork.value = await getArtworkStats(artworkId);
});

function fullsizeUrl(id) {
  return `https://www.artic.edu/iiif/2/${id}/full/1000,/0/default.jpg`;
}

function formatDate(iso) {
  if (!iso) return "—";
  return new Date(iso).toLocaleString("nl-NL", {
    day: "2-digit",
    month: "2-digit",
    year: "numeric",
    hour: "2-digit",
    minute: "2-digit",
  });
}

</script>

<template>
  <div class="artwork-stats">
    <PageHeader username="Admin" />

    <main class="content">
      <button class="back-link" @click="router.push('/admin/artworks')">
        &larr; Terug naar kunstwerken
      </button>

      <div v-if="artwork" class="detail-grid">
        <section class="image-panel">
          <img
              class="artwork-blur"
              :src="fullsizeUrl(artwork.id)"
              alt=""
              aria-hidden="true"
          />
          <img
              class="artwork-img"
              :src="fullsizeUrl(artwork.id)"
              :alt="artwork.title"
          />
        </section>

        <section class="info-panel">
          <header class="title-block">
            <h1>{{ artwork.title }}</h1>
            <p class="artist">{{ artwork.artistDisplay }}</p>
          </header>

          <div class="stat-row">
            <div class="stat-card">
              <p class="stat-card-label">Pick-ratio</p>
              <p class="stat-card-value">
                {{ artwork.pickPercentage != null ? `${artwork.pickPercentage}%` : "—" }}
              </p>
              <div class="stat-card-breakdown">
                <span><strong>{{ artwork.timesLoaded }}</strong> x getoond</span>
                <span><strong>{{ artwork.timesSpymasterPick }}</strong> x gekozen</span>
              </div>
            </div>
            <div class="stat-card">
              <p class="stat-card-label">Raadbaarheid</p>
              <p class="stat-card-value">
                {{ artwork.guessPercentage != null ? `${artwork.guessPercentage}%` : "—" }}
              </p>
              <div class="stat-card-breakdown">
                <span><strong>{{ artwork.timesCorrectGuess }}</strong> x goed</span>
                <span><strong>{{ artwork.timesBadGuess }}</strong> x fout</span>
              </div>
            </div>
          </div>

          <div class="trait-card">
            <h2>Details</h2>
            <dl class="traits">
              <div class="trait">
                <dt>Datum</dt>
                <dd>{{ artwork.dateDisplay ?? "—" }}</dd>
              </div>
              <div class="trait">
                <dt>Techniek</dt>
                <dd>{{ artwork.mediumDisplay ?? "—" }}</dd>
              </div>
              <div class="trait">
                <dt>Afmetingen</dt>
                <dd>{{ artwork.dimensions ?? "—" }}</dd>
              </div>
              <div class="trait">
                <dt>Herkomst</dt>
                <dd>{{ artwork.placeOfOrigin ?? "—" }}</dd>
              </div>
              <div class="trait">
                <dt>Afdeling</dt>
                <dd>{{ artwork.departmentTitle ?? "—" }}</dd>
              </div>
              <div class="trait">
                <dt>Eerst gebruikt</dt>
                <dd>{{ formatDate(artwork.firstUsedAt) }}</dd>
              </div>
              <div class="trait">
                <dt>Laatst gebruikt</dt>
                <dd>{{ formatDate(artwork.lastUsedAt) }}</dd>
              </div>
            </dl>
          </div>
        </section>
      </div>

      <section v-if="artwork" class="boards-card">
        <h2>Gebruikt in boards</h2>
        <table>
          <thead>
          <tr>
            <th>Game-ID</th>
            <th>Hint</th>
            <th>Uitkomst</th>
            <th>Datum</th>
          </tr>
          </thead>
          <tbody>
          <tr class="empty">
            <td colspan="4">Nog geen data</td>
          </tr>
          </tbody>
        </table>
      </section>
    </main>
  </div>
</template>

<style scoped>
.artwork-stats {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
  background-color: var(--color-bg-base);
}

.content {
  max-width: 1200px;
  width: 100%;
  margin: 0 auto;
  padding: 40px 32px;
  box-sizing: border-box;
}

.back-link {
  background: none;
  border: none;
  padding: 0;
  margin: 0 0 16px 0;
  color: var(--text-primary);
  opacity: 0.7;
  font-family: var(--font-secondary), sans-serif;
  font-size: 15px;
  cursor: pointer;
}

.back-link:hover {
  opacity: 1;
}

/* ---- Two-column detail ---- */
.detail-grid {
  display: grid;
  grid-template-columns: minmax(0, 1fr) minmax(0, 1fr);
  gap: 28px;
  margin-bottom: 28px;
}

@media (max-width: 860px) {
  .detail-grid {
    grid-template-columns: 1fr;
  }
}

/* ---- Image panel ---- */
.image-panel {
  position: relative;
  border: 1px solid var(--button-border);
  border-radius: 28px;
  box-shadow: 0 18px 38px var(--secondary-shadow);
  padding: 24px;
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 420px;
  box-sizing: border-box;
  overflow: hidden;
}

.artwork-blur {
  position: absolute;
  inset: 0;
  width: 100%;
  height: 100%;
  object-fit: cover;
  filter: blur(36px) saturate(1.15);
  transform: scale(1.15);
  opacity: 0.55;
  pointer-events: none;
  user-select: none;
}

.artwork-img {
  position: relative;
  max-width: 100%;
  max-height: 560px;
  border-radius: 12px;
  box-shadow: 0 10px 28px rgba(124, 97, 62, 0.25);
  object-fit: contain;
  background: var(--color-secondary);
}

/* ---- Info panel ---- */
.info-panel {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.title-block {
  display: flex;
  flex-direction: column;
  gap: 6px;
}

.info-panel h1 {
  font-family: var(--font-main), serif;
  font-size: 2rem;
  font-weight: 500;
  color: var(--text-primary);
  margin: 0;
  line-height: 1.2;
}

.artist {
  font-family: var(--font-secondary), sans-serif;
  color: var(--text-primary);
  opacity: 0.75;
  margin: 0;
  white-space: pre-line;
}

/* ---- Stat cards ---- */
.stat-row {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 12px;
}

@media (max-width: 520px) {
  .stat-row {
    grid-template-columns: 1fr;
  }
}

.stat-card {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 8px;
  background: var(--color-secondary);
  border: 1px solid var(--button-border);
  border-radius: 20px;
  padding: 18px 16px;
  box-shadow: 0 18px 38px var(--secondary-shadow);
}

.stat-card-label {
  font-family: var(--font-secondary), sans-serif;
  font-size: 0.7rem;
  letter-spacing: 0.08em;
  text-transform: uppercase;
  color: var(--text-primary);
  opacity: 0.55;
  margin: 0;
}

.stat-card-value {
  font-family: var(--font-main), serif;
  font-size: 2rem;
  font-weight: 500;
  color: var(--text-primary);
  margin: 0;
  line-height: 1;
}

.stat-card-breakdown {
  display: flex;
  gap: 16px;
  font-family: var(--font-secondary), sans-serif;
  font-size: 0.85rem;
  color: var(--text-primary);
  opacity: 0.7;
}

.stat-card-breakdown strong {
  font-weight: 600;
  opacity: 1;
}

/* ---- Traits card ---- */
.trait-card,
.boards-card {
  background: var(--color-secondary);
  border: 1px solid var(--button-border);
  border-radius: 28px;
  box-shadow: 0 18px 38px var(--secondary-shadow);
  padding: 20px 24px;
  box-sizing: border-box;
}

.trait-card {
  flex: 1;
}

.boards-card {
  margin-top: 4px;
}

.trait-card h2,
.boards-card h2 {
  font-family: var(--font-main), serif;
  font-size: 1.25rem;
  font-weight: 500;
  color: var(--text-primary);
  margin: 0 0 12px 0;
}

.traits {
  margin: 0;
  display: grid;
  grid-template-columns: 1fr;
  gap: 2px;
}

.trait {
  display: grid;
  grid-template-columns: 140px 1fr;
  padding: 8px 0;
  border-bottom: 1px solid var(--hub-border-bottom);
  gap: 16px;
}

.trait:last-child {
  border-bottom: none;
}

.trait dt {
  font-family: var(--font-secondary), sans-serif;
  font-size: 0.8rem;
  letter-spacing: 0.06em;
  text-transform: uppercase;
  color: var(--text-primary);
  opacity: 0.55;
  margin: 0;
  align-self: center;
}

.trait dd {
  font-family: var(--font-secondary), sans-serif;
  color: var(--text-primary);
  margin: 0;
  align-self: center;
}

/* ---- Boards table ---- */
.boards-card table {
  width: 100%;
  border-collapse: collapse;
  font-family: var(--font-secondary), sans-serif;
}

.boards-card th,
.boards-card td {
  text-align: left;
  padding: 10px 12px;
  color: var(--text-primary);
  border-bottom: 1px solid var(--hub-border-bottom);
}

.boards-card tbody tr:last-child td {
  border-bottom: none;
}

.boards-card tr.empty td {
  text-align: center;
  padding: 32px 16px;
  opacity: 0.5;
  font-style: italic;
}

.outcome {
  display: inline-block;
  padding: 3px 10px;
  border-radius: 999px;
  font-size: 0.75rem;
}

.outcome-correct {
  background-color: #ebf4ea;
  border-color: #cfe3cd;
  color: #436b3f;
}

.outcome-wrong {
  background-color: #f7e8e6;
  border-color: #e6c8c3;
  color: #8a4239;
}

.outcome-skipped {
  background-color: #f1ebdc;
  border-color: #dfd2b6;
  color: #7a6a46;
}

</style>

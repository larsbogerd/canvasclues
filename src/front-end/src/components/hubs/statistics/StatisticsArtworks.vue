<script setup>
import PageHeader from "@/components/header/PageHeader.vue";
import ArtworkStatsTable from "@/components/hubs/statistics/ArtworkStatsTable.vue";
import router from "@/router.js";
import { onMounted, ref } from "vue";
import { getArtworkStatsList } from "@/assets/composables/StatisticsService.js";

const artworks = ref([]);

onMounted(async () => {
  artworks.value = await getArtworkStatsList();
});
</script>

<template>
  <div class="statistics-artworks">
    <PageHeader />
    <main class="content">
      <button class="back-link"
              @click="router.push('/statistics')">
        &larr; Terug naar hub
      </button>
      <header class="page-header">
        <div>
          <h1>Kunstwerken</h1>
          <p class="subtitle">Populariteit, pick-ratio's en gebruiksgeschiedenis</p>
        </div>
      </header>

      <section class="highlights">
        <div class="highlight-card">
          <h3>Meest gekozen</h3>
          <p class="placeholder">Top 5 placeholder</p>
        </div>
        <div class="highlight-card">
          <h3>Meest verwarrend</h3>
          <p class="placeholder">Laagste correct-ratio placeholder</p>
        </div>
        <div class="highlight-card">
          <h3>Vergeten</h3>
          <p class="placeholder">Langst ongebruikt placeholder</p>
        </div>
      </section>

      <ArtworkStatsTable :rows="artworks" />
    </main>
  </div>
</template>

<style scoped>
.statistics-artworks {
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

.page-header {
  margin-bottom: 28px;
}

h1 {
  font-family: var(--font-main), serif;
  color: var(--text-primary);
  margin: 0;
  font-size: 36px;
}

.subtitle {
  font-family: var(--font-secondary), sans-serif;
  color: var(--text-primary);
  opacity: 0.7;
  margin: 4px 0 0 0;
}

.highlights {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(240px, 1fr));
  gap: 16px;
  margin-bottom: 32px;
}

.highlight-card {
  background: var(--scrolbox-background-gradient);
  border: 1px solid var(--scrollbox-border-secondary);
  border-radius: 28px;
  padding: 20px;
  box-shadow: 0 18px 38px var(--secondary-shadow);
}

.highlight-card h3 {
  margin: 0 0 8px 0;
  font-family: var(--font-main), serif;
  color: var(--text-primary);
  font-size: 18px;
}

.placeholder {
  margin: 0;
  font-family: var(--font-secondary), sans-serif;
  color: var(--text-primary);
  opacity: 0.5;
  font-style: italic;
}
</style>

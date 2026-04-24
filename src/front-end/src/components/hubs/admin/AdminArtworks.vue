<script setup>
import Header from "@/components/header/Header.vue";
import ArtworkStatsTable from "@/components/hubs/admin/ArtworkStatsTable.vue";
import router from "@/router.js";
import { onMounted, ref } from "vue";
import { getArtworkStatsList } from "@/assets/composables/AdminStatsService.js";

const artworks = ref([]);

onMounted(async () => {
  artworks.value = await getArtworkStatsList();
});
</script>

<template>
  <div class="admin-artworks">
    <Header username="Admin" />
    <main class="content">
      <button class="back-link" @click="router.push('/admin')">
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
.admin-artworks {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
  background-color: var(--background-color);
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
  color: var(--text-color);
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
  color: var(--text-color);
  margin: 0;
  font-size: 36px;
}

.subtitle {
  font-family: var(--font-secondary), sans-serif;
  color: var(--text-color);
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
  background: linear-gradient(180deg, rgba(255, 255, 255, 0.78), rgba(255, 255, 255, 0.62));
  border: 1px solid rgba(219, 210, 195, 0.95);
  border-radius: 28px;
  padding: 20px;
  box-shadow: 0 18px 38px rgba(124, 97, 62, 0.12);
}

.highlight-card h3 {
  margin: 0 0 8px 0;
  font-family: var(--font-main), serif;
  color: var(--text-color);
  font-size: 18px;
}

.placeholder {
  margin: 0;
  font-family: var(--font-secondary), sans-serif;
  color: var(--text-color);
  opacity: 0.5;
  font-style: italic;
}
</style>

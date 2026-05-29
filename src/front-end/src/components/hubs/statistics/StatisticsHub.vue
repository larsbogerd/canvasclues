<script setup>
import PageHeader from "@/components/header/PageHeader.vue";
import router from "@/router.js";
</script>

<template>
  <div class="statistics-hub">
    <PageHeader />
    <main class="content">
      <button class="back-link"
              @click="router.push('/')">
        &larr; Terug naar start
      </button>
      <h1 class="text-color">Inzichten & statistieken</h1>
      <div class="cards">
        <button class="card"
                @click="router.push('/statistics/artworks')">
          <h2>Kunstwerken</h2>
          <p>Populariteit, pick-ratio's en gebruiksgeschiedenis</p>
        </button>
        <button class="card disabled" disabled>
          <h2>Hints</h2>
          <p>Binnenkort beschikbaar</p>
        </button>
      </div>
    </main>
  </div>
</template>

<style scoped>
.text-color {
  color: var(--text-primary);
}

.statistics-hub {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
  background-color: var(--color-bg-base);
}

.content {
  max-width: 1100px;
  width: 100%;
  margin: 0 auto;
  padding: 48px 32px;
  box-sizing: border-box;
}

.back-link {
  background: none;
  border: none;
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

h1 {
  margin: 0 0 32px 0;
  font-size: 40px;
}

.cards {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(260px, 1fr));
  gap: 20px;
}

.card {
  text-align: left;
  font-family: inherit;
  color: inherit;
  overflow: hidden;
  background: var(--button-gradient);
  border: 2px solid var(--button-border);
  border-radius: 28px;
  padding: 2rem 2.25rem;
  box-shadow: 0 18px 38px var(--secondary-shadow);
  cursor: pointer;
  transition: transform 0.12s ease, border-color 180ms ease;
}

.card:hover:not(.disabled) {
  border-color: var(--color-primary);
  box-shadow: 0 10px 24px color-mix(in srgb, var(--color-primary) 22%, transparent);
}

.card.disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.card h2 {
  margin: 0 0 8px 0;
  color: var(--text-primary);
}

.card p {
  margin: 0;
  font-family: var(--font-secondary), sans-serif;
  color: var(--text-primary);
  opacity: 0.7;
}
</style>

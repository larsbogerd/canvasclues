<script setup>
import { getStatisticsHints} from '@/assets/composables/StatisticsHintStatsService.js';
import {onMounted, ref} from "vue";
import PageHeader from "@/components/header/PageHeader.vue";
import router from "@/router.js";
import HintStatsTable from "@/components/hubs/statistics/hint/HintStatsTable.vue";

const hintOccurrenceList = ref([]);

onMounted(async () => {
  hintOccurrenceList.value = await getStatisticsHints();

})

</script>

<template>
  <div class="admin-artworks">
    <PageHeader />

    <main class="content">

      <button class="back-link"
              @click="router.push('/statistics')">
        &larr; Terug naar hub
      </button>

      <header class="page-header">
        <h1>Hints</h1>
        <p class="subtitle"> Hoe vaak een hint is voorgekomen</p>
      </header>

<HintStatsTable :rows="hintOccurrenceList" />
    </main>
  </div>
</template>

<style scoped>
.admin-artworks {
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

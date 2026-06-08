<script setup>
import OperativeHubCard from '@/components/hubs/operativehub/OperativeHubCard.vue';
import GameModeCard from "@/components/hubs/operativehub/GameModeCard.vue";
import {computed, onMounted, ref} from "vue";
import {fetchGameListService} from "@/assets/composables/FetchGameListService.js";
import PageHeader from "@/components/header/PageHeader.vue";
const list = ref([]);
const gameIdList = [];
const sortBy = ref('newest');
const searchHint = ref('');
const difficultyOptions = ['makkelijk', 'gemiddeld', 'moeilijk'];
const selectedDifficulties = ref([]);

const sortedList = computed(() => {
  return [...list.value]
      .filter(card => selectedDifficulties.value.length === 0
          || selectedDifficulties.value.includes(card.gameMode.toLowerCase()))
      .filter(game => game.hint.toLowerCase().includes(searchHint.value.toLowerCase()))
      .sort((a, b) => {
        switch (sortBy.value) {
          case 'newest':
            return new Date(b.createdAt) - new Date(a.createdAt);
          case 'popular':
            return b.playCount - a.playCount;
          case 'difficulty':
            return b.modeName - a.modeName;
          default:
            return 0;
        }
      });
});

async function fillGameIdList(){
  list.value.map(Object.values).forEach((Array) =>{
    gameIdList.push(Array[0]);
  });
}

onMounted(async () => {
  list.value = await fetchGameListService()
  await fillGameIdList()
})

</script>

<template>
  <div class="operative-hub-page">
    <PageHeader username="ClueKiller12">
    </PageHeader>
    <p class=" page-header hub-title">
      Kies een puzzel
    </p>
    <div class="operative-hub">
      <div class="operative-hub_header">
        <GameModeCard game-mode="Willekeurige clue"
                      description="Laat Canvas Clues een puzzel voor je kiezen"
                      :game-ids-to-randomize="gameIdList"/>

        <GameModeCard game-mode="Dagelijkse clue"
                      description="Speel de dagelijkse hint"/>
      </div>
      <div class="operative-hub_scroll_box_container">
        <div class="operative-hub_scroll_box_header">
          <div class="sort-group">
            <p class="sort-label">Sorteer op:</p>
            <select class="text-color" v-model="sortBy">
              <option value="newest">Nieuwste</option>
              <option value="popular">Populariteit</option>
              <option value="difficulty">Moeilijkheid</option>
            </select>
          </div>

          <div>
            <input v-for="difficulty in difficultyOptions"
                   :key="difficulty"
                   type="checkbox"
                   :value="difficulty"
                   v-model="selectedDifficulties" />
          </div>

          <div class="search-group">
            <input class="text-color" v-model="searchHint"
                   type="text"
                   placeholder="Zoek op hint..." />
          </div>
        </div>
        <div class="operative-hub_scroll_box_shell">
          <div class="operative-hub_scroll_box">
            <OperativeHubCard v-for="(card,index) in sortedList"
                              :game-id="card.gameId"
                              :title="card.hint"
                              user="John Doe"
                              :score="card.spyScore"
                              :game-mode="card.gameMode"
                              :key="index"/>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>

.text-color {
  color: var(--text-primary);
}

.operative-hub-page {
  min-height: 100vh;
  background-color: var(--color-bg-base);
}

.operative-hub {
  width: 100%;
  padding: 20px 20px 32px;
  box-sizing: border-box;

  display: flex;
  gap: 2rem;
  flex-direction: column;
  align-items: center;
}

.operative-hub_header {
  display: flex;
  gap: 1.25rem;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: center;
}

.operative-hub_scroll_box_container {
  width: min(1120px, 92vw);
  height: min(64vh, 760px);
  min-height: 420px;
  background: var(--scrolbox-background-gradient);
  border-radius: 28px;
  box-shadow: 0 18px 38px var(--secondary-shadow);
  border: 1px solid var(--scrollbox-border-secondary);
  padding: 18px;
  box-sizing: border-box;
}

.operative-hub_scroll_box_header {
  display: grid;
  grid-template-columns: 1fr auto 1fr;
  align-items: center;
  width: 100%;
  padding: 0 0.72rem 0.9rem;
  gap: 1rem;
}

.sort-label {
  font-family: var(--font-secondary),sans-serif;
  font-size: 1rem;
  margin: 0;
  color: var(--text-primary)
}

.sort-group {
  display: flex;
  align-items: center;
  gap: 8px;
}

.search-group {
  display: flex;
  gap: 0.5rem;
  justify-content: flex-end;
}

.operative-hub_scroll_box_header select,
.operative-hub_scroll_box_header input {
  font-family: var(--font-secondary),sans-serif;
  font-size: 0.9rem;
  padding: 12px 16px;
  border: 1px solid var(--secondary-border);
  border-radius: 20px;
  outline: none;
  background-color: var(--color-secondary);
  transition: border-color 180ms ease;
}

.operative-hub_scroll_box_header select:focus,
.operative-hub_scroll_box_header input:focus {
  border-color: var(--color-primary);
}

.operative-hub_scroll_box_header input {
  width: 140px;
  min-width: 0;
}

.operative-hub_scroll_box_shell {
  width: 100%;
  height: calc(100% - 72px);
  border-radius: 24px;
  background-color: var(--color-bg-base);
  border: 1px solid var(--scrollbox-border);
  box-shadow: inset 0 1px 0 var(--shadow-scrollbox);
  overflow: hidden;
}

.operative-hub_scroll_box{
  width: 100%;
  height: 100%;
  scrollbar-color: var(--scrollbar-color) transparent;
  padding: 0.72rem 0.95rem 0.72rem 0.72rem;
  box-sizing: border-box;

  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  align-content: start;
  align-items: start;
  overflow-y: auto;
  overflow-x: hidden;
  gap: 1.1rem;
}

.hub-title{
  font-family: var(--font-main),serif;
  font-size: 3rem;
  font-weight: 500;
  margin: 0;
  color: var(--text-primary);
}

.page-header{
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 18px 10px 0;
}

@media (max-width: 1200px) {
  .operative-hub_scroll_box {
    grid-template-columns: repeat(2, minmax(0, 1fr));
  }
}

@media (max-width: 760px) {
  .operative-hub_scroll_box {
    grid-template-columns: 1fr;
  }

  .operative-hub_scroll_box_header {
    grid-template-columns: 1fr;
    justify-items: center;
  }

  .search-group {
    justify-content: center;
  }
}
</style>
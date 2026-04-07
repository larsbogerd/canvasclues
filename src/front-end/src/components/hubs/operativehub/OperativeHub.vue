<script setup>
import Card from '@/components/hubs/operativehub/Card.vue';
import GameModeCard from "@/components/hubs/operativehub/GameModeCard.vue";
import axios from "axios";
import {computed, onMounted, ref} from "vue";
const list = ref([]);
const sortBy = ref('newest');
const search = ref('');

const sortedList = computed(() => {
  return [...list.value]
    .filter(game => game.hint.toLowerCase().includes(search.value.toLowerCase()))
    .sort((a, b) => {
    switch (sortBy.value) {
      case 'newest':
        return new Date(b.createdAt) - new Date(a.createdAt);
      case 'popular':
        return b.playCount - a.playCount;
      case 'difficulty':
        return b.maxScore - a.maxScore;
      default:
        return 0;
    }
  });
});

onMounted(() => {fetchGameList()})

async function fetchGameList() {
  const response = await axios.get(`http://localhost:8082/api/v1/game/list`);
  list.value = response.data;
}


</script>

<template>
  <p class=" page-header hub-title">
    Kies een puzzel
  </p>
  <div class="operative-hub">
      <div class="operative-hub_header">
        <GameModeCard game-mode="Willekeurige clue" description="Laat Canvas Clues een puzzel voor je kiezen"/>
        <GameModeCard game-mode="Dagelijkse clue" description="Speel de dagelijkse hint"/>
      </div>
      <div class="operative-hub_scroll_box_container">
        <div class="operative-hub_scroll_box_header">
          <div class="sort-group">
            <p class="sort-label">Sorteer op:</p>
            <select v-model="sortBy">
              <option value="newest">Nieuwste</option>
              <option value="popular">Populariteit</option>
              <option value="difficulty">Moeilijkheid</option>
            </select>
          </div>
          <p class="scrollbox-title">
            Beschikbare clues
          </p>
          <input v-model="search" type="text" placeholder="Zoek op hint..." />
        </div>
        <div class="operative-hub_scroll_box">
          <Card v-for="i in sortedList" :game-id="i.gameId" :title="i.hint" user="John Doe" game-mode="Classic" :score="i.maxScore" />
        </div>
      </div>
  </div>
</template>

<style scoped>

.operative-hub {
  padding: 20px;
  display: flex;
  gap: 10%;
  flex-direction: column;
  align-items: center;
  width: 97vw;
  height: 88vh;
  overflow: hidden;
}

.operative-hub_header {
  display: flex;
  gap: 2%;
  flex-direction: row;
}

.operative-hub_scroll_box_container {
  width: 60vw;
  height: 50vh;
  object-fit: contain;
}

.operative-hub_scroll_box_header{
  justify-content: space-between;
  align-items: center;
  display: flex;
  padding: 10px;
  width: 100%;
}

.sort-label {
  font-family: var(--font-secondary);
  font-size: 1rem;
  margin: 0;
}

.sort-group {
  display: flex;
  align-items: center;
  gap: 8px;
}

.operative-hub_scroll_box_header select,
.operative-hub_scroll_box_header input {
  font-family: var(--font-secondary);
  font-size: 0.9rem;
  padding: 8px 12px;
  border: 1px solid #D1D5DB;
  border-radius: 8px;
  outline: none;
  background-color: #FAFAFA;
}

.operative-hub_scroll_box{
  width: 100%;
  height: 100%;
  padding: 10px;
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
  overflow-y: auto;
  overflow-x: hidden;
  gap: 1rem;
  border-radius: 8px;
  border: 2px solid black;
}

.hub-title{
  font-family: var(--font-main);
  font-size: 2.5rem;
  font-weight: 500;
  padding-bottom: 10px;
  margin: 0;
}
.scrollbox-title{
  font-family: var(--font-main);
  font-size: 1.5rem;
  font-weight: 500;
  padding-bottom: 10px;
  margin: 0;
}

.page-header{
  padding: 10px;
  display: flex;
  flex-direction: column;
  align-items: center;
}
</style>
<script setup>

import { useToggle } from '@vueuse/core'
import {onMounted, ref} from "vue";
import {fetchGameListService} from "@/assets/composables/FetchGameListService.js";
import PageHeader from "@/components/header/PageHeader.vue";
const list = ref([]);
import OperativeScrollBox from "@/components/hubs/operativehub/OperativeScrollBox.vue";
import GameModeCard from "@/components/hubs/operativehub/GameModeCard.vue";
import OperativeHubButton from "@/components/hubs/operativehub/OperativeHubButton.vue";
import router from "@/router.js";
import {fetchRandomGameId} from "@/assets/composables/SessionService.js";

const [showScrollBox, toggle] = useToggle()

let randomId = 0;

onMounted(async () => {
  list.value = await fetchGameListService()
})


async function goToRandomOperativePhase(difficulty) {
  randomId = await fetchRandomGameId(difficulty);
  await router.push(`/game/bezoeker/${randomId}`)
}

async function goToDailyOperativePhase(randomId) {
  await router.push(`/game/bezoeker/${randomId}`)
}


</script>

<template>
  <div class="operative-hub-page">
    <PageHeader>
    </PageHeader>
    <div class="operative-hub">
    <p class=" page-header hub-title">
      Kies een puzzel
    </p>

<div class="operative-hub_container">
  <div class="operative-hub_header">
      <GameModeCard game-mode="Willekeurige clue"
                    description="Laat Canvas Clues een puzzel voor je kiezen">
        <div class="difficulty-select-container">
          <OperativeHubButton class="difficulty-button"
                              @GoToOperative="goToRandomOperativePhase('makkelijk')"
                              text="Makkelijk"/>

          <OperativeHubButton class="difficulty-button"
                              @GoToOperative="goToRandomOperativePhase('gemiddeld')"
                              text="Normaal"/>

          <OperativeHubButton class="difficulty-button"
                              @GoToOperative="goToRandomOperativePhase('moeilijk')"
                              text="Moeilijk"/>
        </div>
      </GameModeCard>

      <GameModeCard game-mode="Dagelijkse clue"
                    description="Speel de dagelijkse hint">
        <OperativeHubButton @GoToOperative="goToDailyOperativePhase(1)"
                            text="Speel"/>
      </GameModeCard>
  </div>

  <button class="toggle-puzzles-button"
          @click="toggle()">
    Bekijk alle puzzels
  </button>
</div>
      <OperativeScrollBox :list="list"
                 v-if="showScrollBox"/>
    </div>
  </div>
</template>

<style scoped>

.difficulty-select-container {
  display: flex;
  flex-direction: row;
  gap: 10px;
}

.difficulty-button{
  align-self: flex-start;
}

.operative-hub-page {
  min-height: 100vh;
  background-color: var(--color-bg-base);
}

.operative-hub {
  min-height: 89vh;
  width: 100%;
  padding: 20px 20px 32px;
  box-sizing: border-box;
  display: flex;
  gap: 2rem;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.operative-hub_container{
  display: flex;
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

.toggle-puzzles-button{
  outline: none;
  color: var(--color-secondary);
  background-color: var(--color-primary);
  border: none;
  font-family: var(--font-secondary),sans-serif;
  border-radius: 20px;
  padding: 10px 18px;
  margin: 10px 0 0;
  min-width: 100px;
  cursor: pointer;
  font-weight: 700;
  box-shadow: 0 10px 20px color-mix(in srgb, var(--color-primary) 28%, transparent);
  transition: transform 180ms ease, box-shadow 180ms ease, filter 180ms ease;
}

.toggle-puzzles-button:hover,
.toggle-puzzles-button:focus-visible{
  transform: translateY(-1px);
  filter: saturate(1.02);
  box-shadow: 0 14px 24px color-mix(in srgb, var(--color-primary) 38%, transparent);
}

</style>

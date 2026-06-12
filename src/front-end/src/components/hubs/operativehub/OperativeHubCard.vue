<script setup>
import OperativeHubButton from "@/components/hubs/operativehub/OperativeHubButton.vue";
import DifficultyIcon from "@/assets/images/svg-components/DifficultyIcon.vue";
import {computed} from "vue";
import router from "@/router.js";

const props = defineProps({
  gameId: Number,
  title: String,
  gameMode: String,
  score: Number,
  playCount: Number,
  qualityRatio: Number,
})

const DIFFICULTY_LEVEL = { makkelijk: 1, gemiddeld: 2, moeilijk: 3 }
const difficultyLevel = computed(() => DIFFICULTY_LEVEL[props.gameMode])

const qualityLabel = computed(() =>
    props.qualityRatio == null ? null : `${Math.round(props.qualityRatio)}%`)

const playLabel = computed(() =>
    props.playCount > 0 ? `${props.playCount}× gespeeld` : 'Nog niet gespeeld')

function goToOperativePhase() {
  router.push(`/game/bezoeker/${props.gameId}`)
}

</script>

<template>
  <div class="game-hub-card">
    <div class="text-block">
      <div class="card-header">
        <p class="hub-title">{{ props.title }}</p>
        <span v-if="qualityLabel" class="quality-badge" :title="`Kwaliteit ${qualityLabel}`">
          <span class="quality-emoji">👍</span>{{ qualityLabel }}
        </span>
        <span v-else class="quality-badge quality-badge--empty">nieuw</span>
      </div>
      <DifficultyIcon class="hub-difficulty" :level="difficultyLevel" :title="props.gameMode" />
      <p class="hub-meta">{{ playLabel }}</p>
      <p class="hub-meta">Hoogste score: {{ props.score }}</p>
      <OperativeHubButton @GoToOperative="goToOperativePhase"
                          text="Speel" />
    </div>
  </div>
</template>

<style scoped>
.game-hub-card{
  width: 100%;
  box-sizing: border-box;
  background-color: var(--color-secondary);
  padding: 14px;
  border-radius: 22px;
  border: 1px solid var(--button-border);
  transition: border-color 180ms ease;
}

.game-hub-card:hover {
  border-color: var(--button-border-gradient);
}

.text-block{
  display: flex;
  flex-direction: column;
  padding: 20px 20px 10px 20px;
  gap: 6px;
}

.card-header{
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  gap: 10px;
}

.hub-title{
  font-family: var(--font-main),serif;
  font-size: 1.5rem;
  font-weight: 500;
  text-transform: capitalize;
  margin: 0;
  overflow-wrap: break-word;
  word-break: break-word;
  hyphens: auto;
  color: var(--text-primary)
}

.quality-badge{
  display: inline-flex;
  align-items: center;
  gap: 5px;
  flex-shrink: 0;
  font-family: var(--font-secondary),sans-serif;
  font-size: 0.82rem;
  font-weight: 700;
  white-space: nowrap;
  color: var(--text-primary);
  border: 1px solid var(--secondary-border);
  border-radius: 999px;
  padding: 3px 10px;
}

.quality-emoji{
  font-size: 0.9rem;
  line-height: 1;
}

.quality-badge--empty{
  font-weight: 500;
  color: var(--text-tertiary);
}

.hub-difficulty{
  align-self: flex-start;
  height: 26px;
  width: auto;
  margin: 4px 0;
}

.hub-meta{
  font-family: var(--font-secondary),sans-serif;
  font-size: 0.9rem;
  margin: 0;
  color: var(--text-tertiary);
}
</style>

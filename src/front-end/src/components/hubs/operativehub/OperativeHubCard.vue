<script setup>
import OperativeHubButton from "@/components/hubs/operativehub/OperativeHubButton.vue";

const props = defineProps({
  gameId: Number,
  title: String,
  user: String,
  gameMode: String,
  score: Number,
})

</script>

<template>
  <div class="game-hub-card">
    <div class="text-block">
      <p class="hub-title">{{ props.title }}</p>
      <p class="hub-description">Gamemode: {{ props.gameMode }}</p>
      <p class="hub-description">Best score: {{ props.score }}</p>
      <OperativeHubButton :game-id="props.gameId" />
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

.hub-title{
  font-family: var(--font-main),serif;
  font-size: 1.5rem;
  font-weight: 500;
  text-transform: capitalize;
  margin: 0;
  padding-bottom: 10px;
  overflow-wrap: break-word;
  word-break: break-word;
  hyphens: auto;
  color: var(--text-primary)
}

.hub-description{
  font-family: var(--font-secondary),sans-serif;
  font-size: 1rem;
  margin: 0;
  color: var(--text-tertiary);
}
</style>

<script setup>
import Button from "@/components/hubs/operativehub/Button.vue";

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
      <p class="hub-title">
        {{ props.title }}
      </p>
      <p class="hub-description">
        Spymaster: {{props.user}}
      </p>
      <p class="hub-description">
        Gamemode: {{props.gameMode}}
      </p>
      <p class="hub-description">
        Best score: {{props.score}}
      </p>
    </div>
    <Button :game-id="props.gameId"/>
  </div>
</template>

<style scoped>

.game-hub-card{
  width: 100%;
  box-sizing: border-box;
  background-color: white;
  padding: 14px;
  border-radius: 22px;
  border: 1px solid #e2d8c8;
  transition: border-color 180ms ease;
}

.game-hub-card:hover {
  border-color: color-mix(in srgb, var(--primary-color) 40%, #ddd5c9);
}

.text-block{
  display: flex;
  flex-direction: column;
  padding: 20px 20px 10px 20px;
  gap: 6px;
}

.hub-title{
  font-family: var(--font-main);
  font-size: 1.5rem;
  font-weight: 500;
  text-transform: capitalize;
  margin: 0;
  padding-bottom: 10px;
}

.hub-description{
  font-family: var(--font-secondary);
  font-size: 1rem;
  margin: 0;
  color: #4c4c4c;
}

</style>

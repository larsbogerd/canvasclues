<script setup>
import Button from "@/components/hubs/operativehub/Button.vue";

let props = defineProps({
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
  object-fit: contain;
  width: 30%;
  height: auto;
  background-color: #FAFAFA;
  padding: 10px;
  border-radius: 8px;
  border: 1px solid #D1D5DB;
  min-width: 200px;
}

.text-block{
  display: flex;
  flex-direction: column;
  padding: 20px 20px 10px 20px;
  gap: 4px;
}

.hub-title{
  font-family: var(--font-main);
  font-size: 1.5rem;
  font-weight: 500;
  text-transform: capitalize;
  padding-bottom: 10px;
  margin: 0;
}

.hub-description{
  font-family: var(--font-secondary);
  font-size: 1rem;
  margin: 0;
}

</style>
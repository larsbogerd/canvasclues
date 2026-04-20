<script setup>
import Button from "@/components/hubs/operativehub/Button.vue";

const props = defineProps({
  description: String,
  gameMode: String,
})

</script>

<template>
  <div class="game-hub-card">
    <div class="text-block">
      <p class="hub-title">
        {{props.gameMode}}
      </p>
      <p class="hub-description">
        {{props.description}}
      </p>
    </div>
    <Button/>
  </div>
</template>

<style scoped>

.game-hub-card{
  display: flex;
  flex-direction: column;
  width: 300px;
  min-height: 220px;
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
  flex: 1;
  padding: 20px 20px 10px 20px;
  gap: 4px;
}

.hub-title{
  font-family: var(--font-main);
  font-size: 1.5rem;
  font-weight: 500;
  margin: 0;
  padding-bottom: 10px;
}

.hub-description{
  font-family: var(--font-secondary);
  font-size: 1rem;
  margin: 0;
  color: #4c4c4c;
}

.game-hub-card :deep(button) {
  align-self: flex-start;
}

</style>

<script setup>
import Button from "@/components/hubs/operativehub/Button.vue";

let props = defineProps({
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
  object-fit: contain;
  width: 300px;
  height: auto;
  background-color: #FAFAFA;
  padding: 10px;
  border-radius: 8px;
  border: 1px solid #D1D5DB;
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
  padding-bottom: 10px;
  margin: 0;
}

.hub-description{
  font-family: var(--font-secondary);
  font-size: 1rem;
  margin: 0;
}

</style>
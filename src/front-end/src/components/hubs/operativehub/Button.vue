<script setup>
import router from "@/router.js";

const props = defineProps({
  gameId: Number,
})

function goToOperativePhase() {
  router.push(`/game/operative/${props.gameId}`)
}
</script>

<template>
<button @click="goToOperativePhase">
  Speel
</button>
</template>

<style scoped>

button{
  outline: none;
  color: white;
  background-color: var(--primary-color);
  border: none;
  font-family: var(--font-secondary);
  border-radius: 8px;
  padding: 10px;
  margin: 10px;
  width: 100px;
  cursor: pointer;
}

</style>
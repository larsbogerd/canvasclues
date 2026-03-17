<script setup>
import SpymasterPhase from "@/components/pages/SpymasterPhase.vue";
import OperativePhase from "@/components/pages/OperativePhase.vue";
import {onUpdated, ref} from "vue";

let gameId = ref()
onUpdated(() => {
  console.log(gameId.value)
});

</script>

<template>
  <div class="app">
    <spymaster-phase @game-started="(id) => gameId = id"/>
    <operative-phase :gameId="gameId"/>
  </div>
</template>

<style scoped>

.app{
  display: flex;
  flex-direction: column;
  gap: 20px;
}
</style>

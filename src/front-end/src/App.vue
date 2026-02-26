<script setup>
import SpymasterPhase from "@/components/pages/SpymasterPhase.vue";
import OperativePhase from "@/components/pages/OperativePhase.vue";
import {onUpdated, ref} from "vue";

let gameID = ref()
onUpdated(() => {
  console.log(gameID.value)
});

</script>

<template>
  <div class="app">
    <spymaster-phase @game-started="(id) => gameID = id"/>
    <operative-phase :gameID="gameID"/>
  </div>
</template>

<style scoped>

.app{
  display: flex;
  flex-direction: column;
  gap: 20px;
}
</style>

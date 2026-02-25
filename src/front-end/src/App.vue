<script setup>
import SelectFase from "@/components/pages/SelectFase.vue";
import HintFase from "@/components/pages/HintFase.vue";
import {onUpdated, ref} from "vue";

let gameID = ref()
onUpdated(() => {
  console.log(gameID.value)
});

</script>

<template>
  <div class="app">
    <select-fase :gameID="gameID"/>
    <hint-fase @game-started="(id) => gameID = id"/>
  </div>
</template>

<style scoped>

.app{
  display: flex;
  flex-direction: column;
  gap: 20px;
}
</style>

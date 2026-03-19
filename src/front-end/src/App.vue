<script setup>
import SpymasterPhase from "@/components/pages/SpymasterPhase.vue";
import OperativePhase from "@/components/pages/OperativePhase.vue";
import StartingPage from "@/components/pages/StartingPage.vue";
import {onUpdated, ref} from "vue";

let gameId = ref()
onUpdated(() => {
  console.log(gameId.value)
});

</script>
<!--
<spymaster-phase @game-started="(id) => gameID = id"/>
<operative-phase :gameID="gameID"/>
-->
<template>
    <RouterView></RouterView>
</template>

<style scoped>

.app{
  display: flex;
  flex-direction: column;
  gap: 20px;
}
</style>

<script setup>

import Card from "@/components/card.vue";
import {onMounted, ref} from "vue";
import axios from "axios";
const cards = ref([]);

onMounted(startGame);

async function startGame() {
  try {
    const response = await axios.post('http://localhost:8082/api/v1/game/start');
    cards.value = response.data;
    console.log("Fetched hint:", cards.value);
  } catch(error) {
    console.log(error);
  }
}


</script>

<template>
  <div class="grid">
    <card v-for="(card,index) in cards" :img-url="card.imageUrl"  :alt-text="card.altText" :key="index"/>
  </div>
</template>

<style scoped>
*{
  --grid-size:4;
}
.grid{
  max-width: 872px;
  max-height: 872px;
  display: grid;
  grid: repeat(var(--grid-size),200px) / repeat(var(--grid-size),200px);
  gap: 24px;
}

</style>
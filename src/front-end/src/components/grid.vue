<script setup>

import Card from "@/components/card.vue";
const prop = defineProps({
      cards: Array,
})


</script>

<template>
  <div class="grid">
    <card v-for="(card,index) in prop.cards" :img-url="card.imageUrl"  :alt-text="card.altText" :key="index"/>
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
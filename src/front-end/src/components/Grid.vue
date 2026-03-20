<script setup>

import Card from "@/components/Card.vue";
const prop = defineProps({
      cards: Array,
})

const emit = defineEmits(['card-clicked'])

</script>

<template>
  <div class="grid">
    <card v-for="(card,index) in prop.cards" :id="card.id" :img-url="card.imageUrl"  :alt-text="card.altText" :color="card.color" :key="index" @card-clicked="(id, clicked) => emit('card-clicked', id, clicked)"/>
  </div>
</template>

<style scoped>
*{
  --grid-size:4;
  --cell-size: min(200px, calc((100vh - 80px) / var(--grid-size) - 16px), calc((50vw - 80px) / var(--grid-size) - 16px));
}
.grid{
  display: grid;
  grid: repeat(var(--grid-size), var(--cell-size)) / repeat(var(--grid-size), var(--cell-size));
  gap: 16px;
}

</style>
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
}
.grid{
  max-width: 872px;
  max-height: 872px;
  display: grid;
  grid: repeat(var(--grid-size),200px) / repeat(var(--grid-size),200px);
  gap: 24px;
}

</style>
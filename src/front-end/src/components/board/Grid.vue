<script setup>

import Card from "@/components/board/Card.vue";
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
.grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  grid-template-rows: repeat(4, 1fr);
  gap: 16px;
  height: calc(100vh - 160px);
  aspect-ratio: 1;
  max-width: 100%;
}
</style>
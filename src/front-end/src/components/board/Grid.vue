<script setup>

import Card from "@/components/board/Card.vue";
const prop = defineProps({
      cards: Array,
      activeInfoId: String,
      phase: String,
})

const emit = defineEmits(['card-clicked', 'info-clicked'])

</script>

<template>
  <div class="grid">
    <card v-for="(card,index) in prop.cards" :phase="prop.phase" :id="card.id" :img-url="card.imageUrl"
          :alt-text="card.altText" :color="card.color" :type="card.type" :info-active="card.id === prop.activeInfoId" :key="index"
          @card-clicked="(id, clicked) => emit('card-clicked', id, clicked)"
          @info-clicked="(id) => emit('info-clicked', id)"/>
  </div>
</template>

<style scoped>
.grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  grid-template-rows: repeat(4, auto);
  gap: 16px;
  width: 100%;
  align-content: start;
  max-width: 100%;
}
</style>

<script setup>
 import {ref} from "vue";

 const clicked = ref(false);

 const cards = defineProps({
    cardArray: {
     type: Object
   }
 })

 const checkStatus = (card) => {
   if (card.status === true) {
     card.bgColor = "#a0ffa0";
   } else {
     card.bgColor = "#ffa0a0";
   }
 }
const reset = (card) => {
  
}
</script>
<template>
  <div
      class="card-base"
      @mousedown="clicked = true; checkStatus(card)"
      @mouseup="clicked = false; reset(card)"
      :class="{ active: clicked}"
      v-for="(card, index) in cards"
      :key="index"
      :style="{ backgroundColor: card.bgColor }"
  >
    {{card["id"]}}
  </div>
</template>
<style scoped>
.active {
  border: 1px solid #555555;
  box-shadow: 7px 6px 28px 1px rgba(0, 0, 0, 0.24);
  transform: translateY(4px);
}
</style>
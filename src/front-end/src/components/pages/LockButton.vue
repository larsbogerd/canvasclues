<script setup lang="ts">
import {inject, ref} from "vue";

const props = defineProps<{
  id: string
}>()
const isLockedIn = ref(false);
const submitFn = inject<(cardId) => void>('submitFn')!
const handleCardClickedFn = inject<() => void>("cardClickedFn");

function lockIn(){
  submitFn(props.id);
  console.log(props.id)
}
</script>

<template>
  <button @click="lockIn()" :class="isLockedIn ? 'visible' : 'hidden'">Lock in</button>
</template>

<style scoped>

.hidden{
 visibility: hidden;
 display: none;
 position: absolute;
 bottom: 10px;
 height: 20%;
 width: 100%;
}

.visible{
  visibility: visible;
  display: block;
  position: absolute;
  bottom: 10px;
  height: 20%;
  width: 100%;
}
</style>
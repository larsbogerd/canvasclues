<script setup lang="ts">
import {inject} from "vue";
import lockInIcon from '@/assets/images/Icons/lock-in.svg'

const props = defineProps<{
  id: string
}>()
const submitFn = inject<(cardId) => void>('submitFn')!

function lockIn(){
  submitFn(props.id);
  console.log(props.id)
}
</script>

<template>
  <button class="lock-btn" @click.stop="lockIn()">
    <img :src="lockInIcon" alt="Lock in" draggable="false" />
  </button>
</template>

<style scoped>

.lock-btn {
  position: absolute;
  top: 5%;
  left: 5%;
  width: clamp(48px, 30%, 96px);
  height: clamp(48px, 30%, 96px);
  padding: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: rgba(255, 255, 255, 0.85);
  border: none;
  border-radius: 50%;
  cursor: pointer;
  opacity: 0;
  pointer-events: none;
  transition: opacity 140ms ease 140ms, background-color 160ms ease;
}

.lock-btn img {
  width: 55%;
  height: 55%;
  transform: rotate(-15deg);
  transition: filter 160ms ease;
}

.lock-btn:hover,
.lock-btn:focus-visible {
  background-color: var(--primary-color);
  outline: none;
}

.lock-btn:hover img,
.lock-btn:focus-visible img {
  filter: brightness(0) invert(1);
}

</style>
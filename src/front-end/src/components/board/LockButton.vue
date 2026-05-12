<script setup>
import {inject} from "vue";
import lockInIcon from '@/assets/images/svg-components/lock-in.vue'

const props = defineProps({
  id: String
})

const submitFn = inject('submitFn')

function lockIn(){
  submitFn(props.id);
  console.log(props.id)
}
</script>

<template>
  <button class="lock-btn"
          @click.stop="lockIn()">
<lock-in-icon class="lock-icon"/>
  </button>
</template>

<style scoped>
.lock-icon{
  color:var(--icon-primary);
}

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
  background-color: var(--bg-light-opacity);
  border: none;
  border-radius: 50%;
  cursor: pointer;
  opacity: 0;
  pointer-events: none;
  transition: opacity 140ms ease 140ms, background-color 160ms ease;
}

.lock-btn svg {
  width: 55%;
  height: 55%;
  transform: rotate(-15deg);
  transition: filter 160ms ease;
}

.lock-btn:hover,
.lock-btn:focus-visible {
  background-color: var(--color-primary);
  outline: none;
}

.lock-btn:hover svg,
.lock-btn:focus-visible svg {
  filter: brightness(0) invert(1);
}

</style>
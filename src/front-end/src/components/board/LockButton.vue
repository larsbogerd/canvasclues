<script setup>
import {inject} from "vue";
import lockIcon from '@/assets/images/svg-components/Plaque.vue'

const props = defineProps({
  id: String
})

const submitFn = inject('submitFn')

function lockIn(){
  submitFn(props.id);
}
</script>

<template>
  <button class="lock-btn"
          @click.stop="lockIn()">
    <lock-icon class="lock-icon"/>
    <span class="lock-label">Bevestig</span>
  </button>
</template>

<style scoped>
.lock-icon{
  color: var(--color-tertiary);
  position: absolute;
  inset: 0;
  filter: drop-shadow(0 3px 6px rgba(0, 0, 0, 0.35));
  transition: filter 160ms ease, color 160ms ease;
}

.lock-label {
  position: relative;
  color: var(--color-secondary);
  font-family: var(--font-display), sans-serif;
  font-weight: 400;
  font-size: 14cqw;
  letter-spacing: 0.08em;
  text-transform: uppercase;
  text-shadow: 0 1px 2px rgba(0, 0, 0, 0.35);
  pointer-events: none;
}

.lock-btn {
  position: absolute;
  bottom: 8%;
  left: 50%;
  transform: translateX(-50%);
  width: clamp(100px, 80%, 300px);
  height: clamp(32px, 18%, 64px);
  container-type: inline-size;
  padding: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  background: none;
  border: none;
  cursor: pointer;
  opacity: 0;
  pointer-events: none;
  transition: opacity 160ms ease;
}

.lock-btn svg {
  width: 100%;
  height: 100%;
}

.lock-btn:focus-visible {
  outline: none;
}

.lock-btn:hover .lock-icon,
.lock-btn:focus-visible .lock-icon {
  filter: drop-shadow(0 4px 8px rgba(0, 0, 0, 0.45));
  color: var(--color-tertiary-active);
}

</style>
<script setup>
import {ref} from "vue";

  const visible = ref(false);

  function show() {
    visible.value = true;
  }

  function hide(){
    visible.value = false;
  }

  defineExpose ({
    show,
    hide,
    visible
  });
</script>

<template>
  <div v-show="visible">
    <div class="overlay"
         @click="hide">
      <div class="popup">
        <button  class="close"
                 @click="hide">
          ✕
        </button>
        <slot></slot>
      </div>
    </div>
  </div>
</template>

<style scoped>
.overlay {
  position: fixed;
  inset: 0;
  background: var(--modal-background-blur);
  z-index: 1;
  display: flex;
  align-items: center;
  justify-content: center;
}

.popup {
  min-width: 320px;
  max-width: 70vw;
  background: var(--color-secondary);
  border-radius: 20px;
  position: relative;
  box-shadow: 0 8px 32px var(--secondary-shadow);
  overflow: hidden;
}

.close {
  position: absolute;
  top: clamp(10px, 1.5vw, 20px);
  right: clamp(10px, 1.5vw, 20px);
  background: none;
  border: none;
  font-size: clamp(1rem, 1.2vw, 1.4rem);
  color: var(--text-eyebrow);
  cursor: pointer;
  transition: color 180ms ease;
}

.close:hover {
  color: var(--text-primary);
}
</style>
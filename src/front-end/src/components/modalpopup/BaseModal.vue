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
    <div class="overlay" @click="hide">
      <div class="popup">
        <button  class="close" @click="hide">
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
  background: rgba(0,0,0,0.5);
  z-index: 1;
  display: flex;
  align-items: center;
  justify-content: center;
}

.popup {
  min-width: 320px;
  width: 28vw;
  background: white;
  border-radius: 20px;
  padding: clamp(24px, 3vw, 48px) clamp(32px, 4vw, 64px);
  position: relative;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.12);
}

.close {
  position: absolute;
  top: clamp(10px, 1.5vw, 20px);
  right: clamp(10px, 1.5vw, 20px);
  background: none;
  border: none;
  font-size: clamp(1rem, 1.2vw, 1.4rem);
  color: #8b7f70;
  cursor: pointer;
  transition: color 180ms ease;
}

.close:hover {
  color: var(--text-color);
}
</style>
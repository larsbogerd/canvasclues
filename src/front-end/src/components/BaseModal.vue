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
    hide
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
  min-width: 400px;
  width: 40vw;
  background: white;
  border-radius: 8px;
  padding: 20px 40px;
  position: relative;
}

.close {
  position: absolute;
  top: 10px;
  right: 10px;
}
</style>
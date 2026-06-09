<script setup>
import {onBeforeUnmount, ref, watch} from "vue";

  const visible = ref(false);

  function show() {
    visible.value = true;
  }

  function hide(){
    visible.value = false;
  }

  function onKeydown(e) {
    if (e.key === 'Escape') hide();
  }

  watch(visible, (isVisible) => {
    if (isVisible) {
      window.addEventListener('keydown', onKeydown);
    } else {
      window.removeEventListener('keydown', onKeydown);
    }
  });

  onBeforeUnmount(() => {
    window.removeEventListener('keydown', onKeydown);
  });

  defineExpose ({
    show,
    hide,
    visible
  });
</script>

<template>
  <Teleport to="body">
    <Transition name="modal">
      <div v-if="visible"
           class="overlay"
           @click.self="hide">
        <div class="popup"
             @click.stop>
          <button type="button"
                  class="close"
                  @click="hide">
            ✕
          </button>
          <slot></slot>
        </div>
      </div>
    </Transition>
  </Teleport>
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

.modal-enter-active,
.modal-leave-active {
  transition: opacity 200ms ease;
}
.modal-enter-from,
.modal-leave-to {
  opacity: 0;
}

.modal-enter-active .popup,
.modal-leave-active .popup {
  transition: transform 220ms cubic-bezier(0.2, 0.8, 0.2, 1), opacity 220ms ease;
}
.modal-enter-from .popup,
.modal-leave-to .popup {
  transform: scale(0.96);
  opacity: 0;
}
</style>
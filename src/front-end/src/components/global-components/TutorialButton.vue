<script setup>
import questionIcon from "@/assets/images/question-icon.svg";
import BaseModal from "@/components/modalpopup/BaseModal.vue";
import {ref} from "vue";
const modal = ref(null)
</script>

<template>
  <BaseModal ref="modal"
             @click="modal?.hide()">
    <slot></slot>
  </BaseModal>

  <button @click="modal?.show()"
          :class="{ active: modal?.visible }">
    <img alt="ModalStart"
         :src="questionIcon" />
  </button>
</template>

<style scoped>
button{
  display: flex;
  align-items: center;
  justify-content: center;
  width: 40px;
  height: 40px;
  background: var(--color-secondary);
  border: 2px solid var(--button-border);
  border-radius: 50%;
  padding: 0;
  cursor: pointer;
  outline: inherit;
  box-shadow: 0 14px 28px var(--tertiary-shadow);
  transition: border-color 180ms ease, box-shadow 180ms ease, transform 180ms ease;
}

button:hover {
  border-color: var(--color-primary);
  box-shadow: 0 10px 24px color-mix(in srgb, var(--color-primary) 22%, transparent);
  transform: scale(1.05);
}

button.active {
  border-color: var(--color-primary);
  box-shadow: 0 10px 24px color-mix(in srgb, var(--color-primary) 22%, transparent);
}

img{
  height: 18px;
  opacity: 0.5;
  transition: opacity 180ms ease;
}

button:hover img,
button.active img {
  opacity: 0.8;
}


</style>
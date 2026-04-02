<script setup>
import questionIcon from "@/assets/images/question-icon.png";
import BaseModal from "@/components/BaseModal.vue";
import {ref} from "vue";
const modal = ref(null)
</script>

<template>
  <BaseModal ref="modal" @click="modal?.hide()">
    <slot></slot>
  </BaseModal>

  <button @click="modal?.show()">
    <img alt="ModalStart" :src="questionIcon" />
  </button>
</template>

<style scoped>
button{
  opacity: 60%;
  background: none;
  color: inherit;
  border: none;
  padding: 0;
  font: inherit;
  cursor: pointer;
  outline: inherit;
}

img{
  height: 24px;
}
</style>
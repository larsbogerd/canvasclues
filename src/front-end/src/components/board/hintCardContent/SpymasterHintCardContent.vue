<script setup>
const props = defineProps({
  showInputErrorPopup: Boolean,
  inputErrorMessage: String,

})

</script>

<template>
    <div
        v-if="showInputErrorPopup"
        class="input-error-popup">
      {{ inputErrorMessage }}
    </div>

</template>

<style scoped>

.input-error-popup {
  position: relative;
  padding: 8px 12px;
  color: var(--game-wrong);
  margin-top: 4px;
  box-sizing: border-box;
  font-family: var(--font-secondary), sans-serif;
}
</style>
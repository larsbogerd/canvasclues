<script setup>
const props = defineProps({
  fase: String
})
</script>

<template>
  <div class="game-rules">
    <slot>
    </slot>
  </div>
</template>

<style scoped>
.game-rules {
  border-radius: 20px;
  background: var(--color-secondary);
  width: 17vw;
  min-width: 280px;
  max-width: 400px;
  box-shadow: 0 2px 8px var(--primary-shadow);
  padding: 20px 24px;
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 4px;
}
</style>

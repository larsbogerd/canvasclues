<script setup>
const props = defineProps({
  fase: String
})
</script>

<template>
  <div class="fase-label">
    <slot>
    </slot>
  </div>
</template>

<style scoped>
.fase-label {
  border-radius: 20px;
  background: var(--color-secondary);
  width: 17vw;
  box-shadow: 0 2px 8px var(--primary-shadow);
  padding: 20px 24px;
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 4px;
}
</style>

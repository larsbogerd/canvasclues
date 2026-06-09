<script setup>
const props = defineProps({
  hint: Object,
})
</script>

<template>
  <span class="hint-header-text">{{ hint.content }}</span>
</template>

<style scoped>

.hint-header-text{
  font-family: var(--font-secondary), sans-serif;
  font-size: clamp(1.4rem, 2.5vw, 2.2rem);
  font-weight: 800;
  text-transform: uppercase;
  letter-spacing: 0.06em;
  max-width: 100%;
  overflow-wrap: anywhere;
  word-break: break-word;
}
</style>
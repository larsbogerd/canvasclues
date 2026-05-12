<script setup>
const props = defineProps({
  fase: String
})
</script>

<template>
  <div class="fase-label">
    <span class="eyebrow">Huidige Fase</span>
    <span class="role">{{ fase }}</span>
  </div>
</template>

<style scoped>
.fase-label {
  border-radius: 20px;
  background: var(--color-secondary);
  width: 340px;
  box-shadow: 0 2px 8px var(--primary-shadow);
  padding: 20px 24px;
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 4px;
}

.eyebrow {
  font-family: var(--font-secondary), sans-serif;
  font-size: clamp(0.7rem, 1vw, 0.82rem);
  letter-spacing: 0.18em;
  text-transform: uppercase;
  font-weight: 700;
  color: var(--text-eyebrow);
}

.role {
  font-family: var(--font-main), serif;
  font-size: clamp(1.5rem, 2.8vw, 2.4rem);
  font-weight: 500;
  color: var(--text-primary);
}
</style>
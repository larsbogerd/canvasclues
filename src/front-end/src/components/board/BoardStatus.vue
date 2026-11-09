<script setup>
// Stands in for GameGrid while a board is loading or has failed to load, at the same size,
// so the page layout doesn't collapse around a missing grid.
const props = defineProps({
  state: {
    type: String,
    required: true,
    validator: value => ['loading', 'error'].includes(value),
  },
  title: String,
  message: String,
})

const emit = defineEmits(['retry'])
</script>

<template>
  <div class="board-status" :aria-busy="props.state === 'loading'">
    <div class="tiles" aria-hidden="true">
      <div v-for="n in 16" :key="n" class="tile" :class="{ shimmer: props.state === 'loading' }"></div>
    </div>

    <div v-if="props.state === 'error'" class="error-card" role="alert">
      <p class="error-title">{{ props.title }}</p>
      <p class="error-body">{{ props.message }}</p>
      <button class="retry-btn" @click="emit('retry')">Opnieuw proberen</button>
    </div>
  </div>
</template>

<style scoped>
.board-status {
  position: relative;
  width: 100%;
}

.tiles {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 16px;
}

.tile {
  aspect-ratio: 1;
  overflow: hidden;
  border-radius: 15%;
  background-color: var(--color-secondary);
  opacity: 0.7;
}

.shimmer {
  background: linear-gradient(100deg, var(--color-secondary) 40%, var(--color-bg-base) 50%, var(--color-secondary) 60%);
  background-size: 200% 100%;
  animation: shimmer 1.4s linear infinite;
}

@keyframes shimmer {
  from { background-position: 100% 0; }
  to { background-position: -100% 0; }
}

.error-card {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: min(80%, 360px);
  padding: 1.5rem;
  border-radius: 16px;
  background-color: var(--color-secondary);
  box-shadow: 0 14px 28px var(--secondary-shadow);
  text-align: center;
}

.error-title {
  margin: 0 0 0.5rem;
  font-family: var(--font-main), serif;
  font-size: 1.25rem;
  color: var(--text-primary);
}

.error-body {
  margin: 0 0 1.25rem;
  font-family: var(--font-secondary), sans-serif;
  font-size: 0.9rem;
  line-height: 1.45;
  color: var(--text-secondary);
}

.retry-btn {
  font-family: var(--font-secondary), sans-serif;
  font-weight: 600;
  padding: 0.6rem 1.25rem;
  border: none;
  border-radius: 999px;
  background-color: var(--color-primary);
  color: var(--color-secondary);
  cursor: pointer;
}

@media (prefers-reduced-motion: reduce) {
  .shimmer { animation: none; }
}
</style>

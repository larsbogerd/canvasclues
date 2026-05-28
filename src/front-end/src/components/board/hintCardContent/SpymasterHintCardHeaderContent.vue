<script setup>
const props = defineProps({
  score: Number
})
</script>

<template>
  <div class="score-container">
    <p class="hint-header-text">Score</p>
    <p class="score-text score">{{ score }}</p>
  </div>

</template>
<style scoped>

.score-container{
  background-color: var(--color-bg-base);
  border-radius: 12px;
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  gap: 7px;
  width: 80%;
  padding: 10px;
}

.hint-header-text{
  font-family: var(--font-secondary), sans-serif;
  font-size: clamp(1.4rem, 2.5vw, 2.2rem);
  font-weight: 800;
  text-transform: uppercase;
  letter-spacing: 0.06em;
}
.score{
  color: var(--color-primary);
}

.score-text{
  /*should be main but font weight doesnt work */
  font-family: var(--font-secondary), sans-serif;
  font-size: clamp(1.4rem, 2.5vw, 2.2rem);
  letter-spacing: 0.06em;
  font-weight: 800;
}

p {
  margin: 0;
}
</style>
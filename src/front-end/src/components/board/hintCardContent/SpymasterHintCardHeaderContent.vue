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
  align-items: baseline;
  gap: 10px;
  width: 100%;
  padding: 12px;
  box-sizing: border-box;
}

.hint-header-text{
  font-family: var(--font-secondary), sans-serif;
  font-size: clamp(1.4rem, 2vw, 2rem);
  font-weight: 600;
}
.score{
  color: var(--color-primary);
}

.score-text{
  font-family: var(--font-secondary), sans-serif;
  font-size: clamp(1.4rem, 2vw, 2rem);
  font-weight: 600;
  line-height: 1.2;
}

p {
  margin: 0;
}
</style>
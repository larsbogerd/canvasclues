<script setup>
const props = defineProps({
  correctAmount: Number,
  cardValue: Number,
  score: Number,
})
const emit = defineEmits(['submit'])

</script>

<template>
  <div class="hint-body">

  <div class="center-body">

    <div class="stats-row">
      <div class="info-container">
        <p class="info-text"> {{ correctAmount }} </p>
        <p class="eyebrow">gevonden</p>
      </div>

      <div class="info-container">
        <p class="info-text score">+{{ cardValue }}</p>
        <p class="eyebrow">volgende</p>
      </div>

    </div>

    <div class="score-container">
      <p class="hint-header-text">Score</p>
      <p class="score-text score">{{ score }}</p>
    </div>
  </div>

  <button class="end-turn-btn" @click="emit('submit')">Beëindig poging</button>
  </div>
</template>

<style scoped>

.stats-row{
  display: flex;
  flex-direction: row;
  gap: 7px;
}

.info-container {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  gap: 9px;
  background-color: var(--color-bg-base);
  border-radius: 12px;
  width: 100px;
  height: 82px;
}

.score-container{
  background-color: var(--color-bg-base);
  border-radius: 12px;
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  gap: 7px;
}

.center-body{
  display: flex;
  flex-direction: column;
  gap: 14px;
}

.info-text{
  /*should be main but font weight doesnt work */
  font-family: var(--font-secondary), sans-serif;
  font-size: clamp(1.4rem, 2.5vw, 2.2rem);
  color: var(--text-primary);
  font-weight: 600;
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

.eyebrow {
  font-family: var(--font-secondary), sans-serif;
  color: var(--text-eyebrow);
  text-transform: uppercase;
  font-size: 16px;
  font-weight: 400;
}

.end-turn-btn {
  display: block;
  width: 100%;
  margin-top: 16px;
  padding: 12px;
  font-size: 16px;
  font-weight: bold;
  font-family: var(--font-secondary), sans-serif;
  color: var(--text-primary);
  background-color: var(--color-secondary);
  border: 2px solid var(--button-border);
  border-radius: 20px;
  cursor: pointer;
  box-shadow: 0 14px 28px var(--tertiary-shadow);
  transition: border-color 180ms ease, box-shadow 180ms ease, transform 180ms ease;
}

.end-turn-btn:hover,
.end-turn-btn:focus-visible {
  border-color: var(--color-primary);
  box-shadow: 0 10px 24px color-mix(in srgb, var(--color-primary) 22%, transparent);
}

.hint-body {
  padding: 24px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.hint-body p {
  margin: 0;
}

.hint-header-text{
  font-family: var(--font-secondary), sans-serif;
  font-size: clamp(1.4rem, 2.5vw, 2.2rem);
  font-weight: 800;
  text-transform: uppercase;
  letter-spacing: 0.06em;
}
</style>
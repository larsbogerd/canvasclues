<script setup>
const props = defineProps({
  description: String,
  gameMode: String
})

</script>

<template>
  <div class="game-hub-card">
    <div class="text-block">
      <p class="hub-title">
        {{props.gameMode}}
      </p>
      <p class="hub-description">
        {{props.description}}
      </p>
    </div>
      <slot>
      </slot>
  </div>
</template>

<style scoped>

.game-hub-card{
  display: flex;
  flex-direction: column;
  width: 350px;
  min-height: 220px;
  box-sizing: border-box;
  background-color: var(--color-secondary);
  padding: 14px;
  border-radius: 22px;
  border: 1px solid var(--button-border);
  transition: border-color 180ms ease;
}

.game-hub-card:hover {
  border-color: var(--button-border-gradient);
}

.text-block{
  display: flex;
  flex-direction: column;
  flex: 1;
  padding: 20px 20px 10px 20px;
  gap: 4px;
}

.hub-title{
  font-family: var(--font-main),serif;
  font-size: 1.5rem;
  font-weight: 500;
  margin: 0;
  padding-bottom: 10px;
  color: var(--text-primary);
}

.hub-description{
  font-family: var(--font-secondary),sans-serif;
  font-size: 1rem;
  margin: 0;
  color: var(--text-tertiary);
}



</style>

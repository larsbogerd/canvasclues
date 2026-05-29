<script setup>
import router from "@/router.js";

const props = defineProps({
  gameId: Number,
  gameIdsToRandomize: Array,
  gameMode: String
})

function gameboardPicker(){
  if( props.gameMode === "Willekeurige clue" ){
    goToRandomOperativePhase()
  }else if( props.gameMode === "Dagelijkse clue" ){
    goToRandomOperativePhase()
  }else{
    goToOperativePhase();
  }
}

function goToRandomOperativePhase(){
  let randomIndex = Math.floor(props.gameIdsToRandomize.length * Math.random())
  let randomId = props.gameIdsToRandomize[randomIndex];
  router.push(`/game/operative/${randomId}`)
}

function goToOperativePhase() {
  router.push(`/game/operative/${props.gameId}`)
}
</script>

<template>
<button @click="gameboardPicker">
  Speel
</button>
</template>

<style scoped>

button{
  outline: none;
  color: var(--color-secondary);
  background-color: var(--color-primary);
  border: none;
  font-family: var(--font-secondary),sans-serif;
  border-radius: 20px;
  padding: 10px 18px;
  margin: 10px 0 0;
  min-width: 100px;
  cursor: pointer;
  font-weight: 700;
  box-shadow: 0 10px 20px color-mix(in srgb, var(--color-primary) 28%, transparent);
  transition: transform 180ms ease, box-shadow 180ms ease, filter 180ms ease;
  align-self: start;
}

button:hover,
button:focus-visible{
  transform: translateY(-1px);
  filter: saturate(1.02);
  box-shadow: 0 14px 24px color-mix(in srgb, var(--color-primary) 38%, transparent);
}

</style>

<script setup>
import ButtonBase from "@/components/ButtonBase.vue";
import router from "@/router.js";

const props = defineProps({
  score: Number,
  amount: Number,
  selectedAmount: Number,
  correctAmount: Number,
});

const score = 0;

function RouteToSpymaster() {
  router.push('/game/spymaster')
}

function ReloadGame() {
  router.go()
}

</script>

<template>
  <section id="parent">
    <h2>Clue voltooid!</h2>
    <p>Score: {{score}}</p>
    <p>Geselecteerde kaarten: {{props.selectedAmount}}</p>
    <p>Gevonden kaarten: {{props.correctAmount}}/{{props.amount}}</p>
    <div class="container">
      <button-base id="button" @click="RouteToSpymaster">Speel als spymaster</button-base>
      <button-base id="button2" @click="ReloadGame">Nieuwe clue</button-base>
    </div>
  </section>
</template>

<style scoped>
p{
  font-family: var(--font-secondary),sans-serif;
  text-align: center;
}

#parent{
  padding-left: 15%;
  padding-right: 15%;
}

h2{
  text-align: center;
}

.container{
  display: grid;
  grid-template-columns: 49% 49%;
  grid-gap: 2%;
}

#button{
  padding: 3%;
}

#button2{
  padding: 3%;
}
</style>

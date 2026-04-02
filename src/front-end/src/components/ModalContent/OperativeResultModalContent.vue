<script setup>
import trophy from "@/assets/images/trophy.png";
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
  router.push('/Spy')
}

function ReloadGame() {
  router.go()
}

//function RouteToLeaderBoard() {
  // router.push('/leaderboard');
//}

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
      <!--<button-base id="button3" @click="RouteToLeaderBoard"><img class="trophy" alt="trophy" :src="trophy">Bekijk leaderboard</button-base>-->
    </div>
  </section>
</template>

<style scoped>
p{
  font-family: var(--font-secondary),serif;
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
  grid-row-gap: 6%;
  grid-template-areas: "a b"
                       "c c";
}

#button{
  grid-area: a;
  padding: 3%;
}

#button2{
  grid-area: b;
  padding: 3%;
}

#button3{
  display: grid;
  grid-template-columns: 10% auto;
  grid-template-rows: auto;
  align-items: center;
  justify-content: center;
  grid-gap: 2%;
  grid-area: c;
  margin-bottom: 3%;
}

.trophy{
  height: 100%;
  width: 100%;
  filter: invert(1);
}
</style>
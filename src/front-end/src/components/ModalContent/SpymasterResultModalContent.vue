<script setup>
import ButtonBase from "@/components/ButtonBase.vue";
import router from "@/router.js";

function RouteToOpertive() {
  router.push('/game/operative')
}

function ReloadGame() {
  router.go()
}

</script>

<template>
  <section id="parent">
    <h2>Puzzel gepubliceerd!</h2>
    <p>Operatives kunnen nu jou puzzel spelen. Bekijk later hoe goed jouw hints werken.</p>
      <div class="container">
        <button-base id="button" @click="RouteToOpertive">Speel als operative</button-base>
        <button-base id="button2" @click="ReloadGame">Nieuwe puzzel</button-base>
      </div>
  </section>
</template>

<style scoped>
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

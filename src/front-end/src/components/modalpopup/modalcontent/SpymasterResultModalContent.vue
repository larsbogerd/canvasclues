<script setup>
import ButtonBase from "@/components/global-components/ButtonBase.vue";
import router from "@/router.js";


const props = defineProps({ score: Number })


function RouteToOpertive() {
  router.push('/game/bezoeker')
}

function ReloadGame() {
  router.go()
}
</script>

<template>
  <section>
    <h1 class="modal-title">Puzzel gepubliceerd!</h1>
    <div class="modal-stat modal-stat--solo">
      <div class="modal-stat-value score">{{props.score}}</div>
      <div class="modal-stat-label">Score</div>
    </div>
    <div class="modal-body modal-body--centered">
      <p>Bezoekers kunnen nu jouw puzzel spelen. Bekijk later hoe goed jouw hints werken.</p>
    </div>
    <hr class="modal-divider" />
    <div class="modal-actions">
      <ButtonBase @click="RouteToOpertive">Speel als Bezoeker</ButtonBase>
      <ButtonBase @click="ReloadGame">Nieuwe puzzel</ButtonBase>
    </div>
  </section>
</template>

<style scoped>
section{
  width: 28vw;
  padding: clamp(24px, 3vw, 48px) clamp(32px, 4vw, 64px);
}
</style>
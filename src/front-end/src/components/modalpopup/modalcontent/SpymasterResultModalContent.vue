<script setup>
import ButtonBase from "@/components/global-components/ButtonBase.vue";
import router from "@/router.js";

function RouteToOpertive() {
  router.push('/game/operative')
}

function ReloadGame() {
  router.go()
}
</script>

<template>
  <section>
    <h1 class="modal-title">Puzzel gepubliceerd!</h1>
    <div class="modal-body modal-body--centered">
      <p>Operatives kunnen nu jouw puzzel spelen. Bekijk later hoe goed jouw hints werken.</p>
    </div>
    <hr class="modal-divider" />
    <div class="modal-actions">
      <ButtonBase @click="RouteToOpertive">Speel als operative</ButtonBase>
      <ButtonBase @click="ReloadGame">Nieuwe puzzel</ButtonBase>
    </div>
  </section>
</template>

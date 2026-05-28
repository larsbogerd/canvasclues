<script setup>
import ButtonBase from "@/components/global-components/ButtonBase.vue";
import router from "@/router.js";

const props = defineProps({
  score: Number,
  amount: Number,
  selectedAmount: Number,
  correctAmount: Number,
  gameOver: Boolean,
});

function RouteToSpymaster() {
  router.push('/game/spymaster')
}

function ReloadGame() {
  router.go()
}
</script>

<template>
  <section>
    <h1 class="modal-title" v-if="props.gameOver">Game Over!</h1>
    <p class="modal-detail" v-if="props.gameOver">Je hebt 2 vervalste kunstwerken geraakt. Het spel eindigt hier.</p>
    <h1 class="modal-title" v-else>Clue voltooid!</h1>
    <div class="modal-stats">
      <div class="modal-stat">
        <div class="modal-stat-value">{{props.correctAmount}}/{{props.amount}}</div>
        <div class="modal-stat-label">Gevonden</div>
      </div>
      <div class="modal-stat">
        <div class="modal-stat-value">{{props.score}}</div>
        <div class="modal-stat-label">Score</div>
      </div>
    </div>
    <p class="modal-detail">Geselecteerde kaarten: {{props.selectedAmount}}</p>
    <hr class="modal-divider" />
    <div class="modal-actions">
      <ButtonBase @click="RouteToSpymaster">Speel als curator</ButtonBase>
      <ButtonBase @click="ReloadGame">Nieuwe clue</ButtonBase>
    </div>
  </section>
</template>

<style scoped>
section{
  width: 28vw;
  padding: clamp(24px, 3vw, 48px) clamp(32px, 4vw, 64px);
}
</style>
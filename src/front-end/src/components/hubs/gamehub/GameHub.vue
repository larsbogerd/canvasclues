<script setup>
import {ref} from "vue";
import router from "@/router.js";
import GameHubButton from "@/components/hubs/gamehub/GameHubButton.vue";
import operativeImg from "@/assets/images/icons/Visitor2.png";
import spymasterImg from "@/assets/images/icons/Curator2.png";
import Footer from "@/components/global-components/Footer.vue";
import PageHeader from "@/components/header/PageHeader.vue";
import {setGameMode} from "@/assets/composables/StartGameService.js";

const difficulties = ['makkelijk', 'gemiddeld', 'moeilijk'];
const difficulty = ref('makkelijk');

function goToSpyPhase() {
  setGameMode(difficulty.value)
  router.push('/game/curator')
}

function goToOperativeHub() {
  router.push('/game/bezoeker')
}

</script>

<template>
  <div class="game-hub-page">
    <PageHeader>
    </PageHeader>
    <div class="game-hub">
      <h1 class="main-text">Wat wil je doen?</h1>
      <div class="game-hub-button">
        <GameHubButton role-name="spymaster"
                       eyeBrow="Creëer"
                       Phase="Curator"
                       sub-text="Stel een nieuwe collectie samen en begeleid bezoekers met jouw hints"
                    :imgUrl="spymasterImg"
                       altText="Spymaster"
                    :difficulties="difficulties"
                       v-model="difficulty"
                       @button-click="goToSpyPhase"/>

        <GameHubButton role-name="Bezoeker"
                       eyeBrow="Ontcijfer"
                       Phase="Bezoeker"
                       sub-text="Betreed een bestaande collectie en ontcijfer de hint van de curator"
                    :imgUrl="operativeImg"
                       altText="Operative"
                       @button-click="goToOperativeHub" />
      </div>
    </div>
    <Footer class="footer-element"/>
  </div>
</template>

<style scoped>
.game-hub-page {
  display: flex;
  flex-direction: column;
  height: 100vh;
  overflow: hidden;
  background-color: var(--color-bg-base);
}

.game-hub {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 100%;
  flex: 1;
  min-height: 0;
  padding: 0 2rem 5rem;
  box-sizing: border-box;
}

.game-hub-button {
  display: flex;
  flex-direction: row;
  justify-content: center;
  gap: 1.25rem;
  flex-wrap: wrap;
}

.main-text {
  text-align: center;
  font-family: var(--font-main),serif;
  margin-bottom: 1.5rem;
  font-size: clamp(3rem, 4vw, 4.5rem);
  color: var(--text-primary);
}

.footer-element {
  background-color: inherit;
}

</style>
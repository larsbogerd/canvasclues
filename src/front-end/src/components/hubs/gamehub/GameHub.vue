<script setup>
import router from "@/router.js";
import GameHubButton from "@/components/hubs/gamehub/GameHubButton.vue";
import operativeImg from "@/assets/images/icons/Operative.png";
import spymasterImg from "@/assets/images/icons/Spymaster.png";
import Footer from "@/components/global-components/Footer.vue";
import PageHeader from "@/components/header/PageHeader.vue";
function goToSpyPhase() {
  router.push('/game/spymaster')
}

function goToOperativeHub() {
  router.push('/game/operative')
}

</script>

<template>
  <div class="game-hub-page">
    <PageHeader username="ClueKiller12">
    </PageHeader>
    <div class="game-hub">
      <h1 class="main-text">Wat wil je doen?</h1>
      <div class="game-hub-button">
        <GameHubButton role-name="spymaster"
                       eyeBrow="Creëer"
                       Phase="Spymaster"
                       sub-text="Maak een nieuwe puzzel en schrijf hints voor operatives"
                    :imgUrl="spymasterImg"
                       altText="Spymaster"
                       @button-click="goToSpyPhase"/>

        <GameHubButton role-name="operative"
                       eyeBrow="Ontcijfer"
                       Phase="Operative"
                       sub-text="Speel een bestaande puzzel en probeer de hint te ontcijferen"
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
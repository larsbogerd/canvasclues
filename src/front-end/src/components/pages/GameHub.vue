<script setup>
import router from "@/router.js";
import HubButton from "@/components/HubButton.vue";
import operativeImg from "@/assets/images/Icons/Operative.png";
import spymasterImg from "@/assets/images/Icons/Spymaster.png";
import Footer from "@/components/Footer.vue";
import Header from "@/components/header/Header.vue";
function goToSpyPhase() {
  router.push('/spy')
}

function goToOperativePhase() {
  router.push('/operative')
}

</script>

<template>
<div class="game-hub">
  <Header #tutorial-button username="ClueKiller12">
  </Header>
  <div class="screen">
    <h1 class="main-text">wat wil je doen?</h1>
    <div class="game-hub-button">
      <hub-button class="button" Phase="Spymaster" sub-text="Maak een nieuwe puzzel en schrijf hints voor operatives "
                  :imgUrl="spymasterImg" altText="Spymaster" @button-click="goToSpyPhase"/>

      <hub-button class="button" Phase="Operative" sub-text="Speel een bestaande puzzel en probeer de hint te ontcijferen"
                  :imgUrl="operativeImg" altText="Operative" @button-click="goToOperativePhase" />
    </div>
  </div>
  <Footer class="footer-element"/>
</div>
</template>

<style scoped>
.game-hub {
  background-color: var(--background-color);
  height: 100vh;
}

.screen {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin: auto;
  height: 80%;
}

.game-hub-button {
  display: flex;
  flex-direction: row;
  justify-content: center;
}

.button {
  background-color: var(--button-color);
}

.main-text {
  text-align: center;
  font-family: var(--font-main),serif;
}
.footer-element {
  background-color: inherit;
}
</style>
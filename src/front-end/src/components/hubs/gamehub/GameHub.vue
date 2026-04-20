<script setup>
import router from "@/router.js";
import Button from "@/components/hubs/gamehub/Button.vue";
import operativeImg from "@/assets/images/Icons/Operative.png";
import spymasterImg from "@/assets/images/Icons/Spymaster.png";
import Footer from "@/components/Footer.vue";
import Header from "@/components/header/Header.vue";
function goToSpyPhase() {
  router.push('/game/spymaster')
}

function goToOperativeHub() {
  router.push('/game/operative')
}

</script>

<template>
  <div class="game-hub-page">
    <Header username="ClueKiller12">
    </Header>
    <div class="game-hub">
      <h1 class="main-text">Wat wil je doen?</h1>
      <div class="game-hub-button">
        <Button role-name="spymaster" eyeBrow="Creëer" Phase="Spymaster" sub-text="Maak een nieuwe puzzel en schrijf hints voor operatives"
                    :imgUrl="spymasterImg" altText="Spymaster" @button-click="goToSpyPhase"/>

        <Button role-name="operative" eyeBrow="Ontcijfer" Phase="Operative" sub-text="Speel een bestaande puzzel en probeer de hint te ontcijferen"
                    :imgUrl="operativeImg" altText="Operative" @button-click="goToOperativeHub" />
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
  background-color: var(--background-color);
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
  font-family: var(--font-main);
  margin-bottom: 1.5rem;
  font-size: clamp(3rem, 4vw, 4.5rem);
}

.footer-element {
  background-color: inherit;
}

</style>
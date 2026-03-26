<script setup>
import router from "@/router.js";
import HubButton from "@/components/HubButton.vue";
import operativeImg from "@/assets/images/Icons/Operative.png";
import spymasterImg from "@/assets/images/Icons/Spymaster.png";
import Footer from "@/components/Footer.vue";
function goToSpyPhase() {
  router.push('/spy')
}

function goToOperativePhase() {
  router.push('/operative')
}

</script>

<template>
  <Header/>
  <div class="game-hub">
    <h1 class="main-text">wat wil je doen?</h1>
    <div class="game-hub-button">
      <hub-button Phase="Spymaster" sub-text="Maak een nieuwe puzzel en schrijf hints voor operatives "
                  :imgUrl="spymasterImg" altText="Spymaster" @button-click="goToSpyPhase"/>

      <hub-button Phase="Operative" sub-text="Speel een bestaande puzzel en probeer de hint te ontcijferen"
                  :imgUrl="operativeImg" altText="Operative" @button-click="goToOperativePhase" />
    </div>
  </div>
<Footer/>
</template>

<style scoped>
.game-hub {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin: auto;
  min-height: calc(100vh - 150px);
}
.game-hub-button {
  display: flex;
  flex-direction: row;
  justify-content: center;
}

.main-text {
  text-align: center;
  font-family: var(--font-family-main);
}
</style>
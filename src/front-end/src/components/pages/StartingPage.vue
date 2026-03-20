<script setup>
import { useRouter } from 'vue-router'

const router = useRouter()

function goToSpyPhase() {
  router.push('/spy')
}
</script>

<template>
  <div class="page">

    <div class="left">
      <div class="header">
        <Logo :size="80" />
      </div>

      <div class="content">
        <h1 class="title">Canvas Clues</h1>
        <p class="description">
          Vind de verborgen verbanden tussen meesterwerken!
        </p>
        <div class="button-group">
          <button @click="goToSpyPhase" class="start-btn">Start een spel</button>
          <button @click="howToPlay" class="secondary-btn">Hoe werkt het?</button>
        </div>
      </div>
    </div>

    <div class="right">
      <img :src="artImage" alt="Art piece" />
    </div>

    <AppFooter />

    <Popup v-model="showPopup" />

  </div>
</template>

<script>
import AppFooter from '@/components/Footer.vue'
import Logo from '@/components/Logo.vue'
import art from '@/assets/images/mainartpiece.png'
import logoImage from '@/assets/images/logo-artic.png'
import Popup from '@/components/RulesPopUp.vue'



export default {
  name: 'StartingPage',

  components: {
    Logo,
    AppFooter,
    Popup

  },

  data() {
    return {
      artImage: art,
      logo: logoImage,
      showPopup: false
    }
  },
  methods: {
    startGame() {
      console.log('Spel gestart');
    },
    howToPlay() {
      this.showPopup = true
    }
  }
}
</script>

<style>
html, body {
  margin: 0;
  padding: 0;
  height: 100%;
  width: 100%;
}

.page {
  position: relative;
  display: flex;
  height: 100vh;
  width: 100vw;
  background-color: #F4EFE6;
  overflow: hidden;
}

.left {
  flex: 1;
  display: flex;
  align-items: center;
  padding-left: 2rem;
}

.header {
  position: absolute;
  top: 1.5rem;
  left: 2rem;
}

.header img {
  width: 80px;
  height: 80px;
}

.content {
  max-width: 550px;
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.right {
  flex: 1;
  overflow: hidden;
}

.right img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.title {
  font-family: 'Fraunces', serif;
  font-size: 5.5rem;
  font-weight: 300;
  margin: 0;
}

.description {
  font-family: 'DM Sans', sans-serif;
  font-size: 1.5rem;
  font-weight: 300;
  margin: 0;
}

.button-group {
  display: flex;
  gap: 0.8rem;
  margin-top: 0.5rem;
}

.start-btn,
.secondary-btn {
  font-size: 1.2rem;
  padding: 0.6rem 4rem;
  border-radius: 0.5rem;
  cursor: pointer;
}

.start-btn {
  background-color: #E98689;
  color: var(--background-color);
  border: none;
}

.start-btn:hover {
  opacity: 0.9;
}

.secondary-btn {
  background-color: transparent;
  color: black;
  border: 2px solid #E98689;
}
</style>
<script setup>
import { useRouter } from 'vue-router'
import {ref} from "vue";
import ButtonBase from "@/components/ButtonBase.vue";
import Logo from "@/components/Logo.vue";
import BaseModal from "@/components/BaseModal.vue";
import StartingPageModalContent from "@/components/ModalContent/StartingPageModalContent.vue";

const router = useRouter()
const modal = ref(null)

function goToGameHubPhase() {
  router.push('/gamehub')
}
</script>

<template>
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
        <button-base @click="goToGameHubPhase" >Start een spel</button-base>
        <button-base @click="modal?.show()" id="secondary-btn">Hoe werkt het?</button-base>
      </div>
    </div>
  </div>
  <BaseModal ref="modal">
    <StartingPageModalContent></StartingPageModalContent>
  </BaseModal>
</template>
<style scoped>
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

.content {
  max-width: 550px;
  display: flex;
  flex-direction: column;
  gap: 1rem;
}


.title {
  font-family: var(--font-main),serif;
  font-size: 5.5rem;
  font-weight: 300;
  margin: 0;
}

.description {
  font-family: var(--font-secondary),serif;
  font-size: 1.5rem;
  font-weight: 300;
  margin: 0;
}

.button-group {
  display: flex;
  gap: 0.8rem;
  margin-top: 0.5rem;
}

#secondary-btn {
  background-color: transparent;
  color: black;
  border: 2px solid var(--primary-color);
}
</style>
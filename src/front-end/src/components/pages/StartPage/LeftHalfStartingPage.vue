<script setup>
import { useRouter } from 'vue-router'
import {ref} from "vue";
import ButtonBase from "@/components/global-components/ButtonBase.vue";
// import ArticLogo from "@/components/pages/StartPage/ArticLogo.vue";
import ArticLogo from "@/assets/images/svg-components/ArticLogo.vue"
import BaseModal from "@/components/modalpopup/BaseModal.vue";
import StartingPageModalContent from "@/components/modalpopup/modalcontent/StartingPageModalContent.vue";

const router = useRouter()
const modal = ref(null)

function goToGameHubPhase() {
  router.push('/game')
}
</script>

<template>
  <div class="left">
    <div class="header">
      <ArticLogo class="artic-logo" />
    </div>

    <div class="content">
      <h1 class="title">Canvas Clues</h1>
      <p class="description">
        Vind de verborgen verbanden tussen meesterwerken!
      </p>
      <div class="button-group">
        <button-base @click="goToGameHubPhase"
                     id="primary-btn">Start een spel</button-base>
        <button-base @click="modal?.show()"
                     class="secondary-btn">Hoe werkt het?</button-base>
        <button-base @click="router.push('/statistics')"
                     class="secondary-btn">Statistieken</button-base>
      </div>
    </div>
  </div>
  <BaseModal ref="modal">
    <StartingPageModalContent></StartingPageModalContent>
  </BaseModal>
</template>

<style scoped>
.artic-logo{
  color: var(--logo-artic);
  display: block;
}
.left {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 0 2rem;
}

.header {
  position: absolute;
  top: 1.5rem;
  left: 2rem;
}

.header :deep(svg) {
  width: clamp(80px, 5vw, 120px);
  height: clamp(80px, 5vw, 120px);
}

.content {
  max-width: 550px;
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.title {
  font-family: var(--font-main), sans-serif;
  font-size: 5.5rem;
  font-weight: 300;
  margin: 0;
  color: var(--text-primary);
}

.description {
  font-family: var(--font-secondary), sans-serif;
  font-size: 1.5rem;
  font-weight: 300;
  margin: 0;
  color: var(--text-secondary);
}

.button-group {
  display: grid;
  grid-template-columns: 1fr 1fr;
  grid-template-rows: 1fr 1fr;
  gap: 0.9rem;
  margin-top: 0.75rem;
}

:deep(#primary-btn) {
  grid-column: 1;
  grid-row: 1 / span 2;
  width: 100%;
  height: 100%;
  padding: 0.6rem 1rem;
  font-size: 1.4rem;
}

:deep(.secondary-btn) {
  width: 100%;
  padding: 0.6rem 1rem;
  background-color: transparent;
  color: var(--text-primary);
  border: 2px solid var(--button-border);
  box-shadow: 0 14px 28px var(--tertiary-shadow);
}

:deep(.secondary-btn:hover) {
  border-color: var(--color-primary);
  box-shadow: 0 10px 24px color-mix(in srgb, var(--color-primary) 22%, transparent);
}
</style>
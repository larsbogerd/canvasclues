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
        <button-base @click="goToGameHubPhase" >Start een spel</button-base>
        <button-base @click="modal?.show()"
                     id="secondary-btn">Hoe werkt het?</button-base>
      </div>
    </div>
    <button class="admin-secret" @click="router.push('/admin')"></button>
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

.admin-secret {
  position: absolute;
  top: 0;
  left: 0;
  width: 200px;
  height: 200px;
  background: transparent;
  border: none;
  opacity: 0;
  cursor: pointer;
}

.admin-secret:hover {
  opacity: 1;
}

.button-group {
  display: flex;
  gap: 0.9rem;
  margin-top: 0.75rem;
  flex-wrap: wrap;
}

:deep(#secondary-btn) {
  background-color: transparent;
  color: var(--text-primary);
  border: 2px solid var(--button-border);
  box-shadow: 0 14px 28px var(--tertiary-shadow);
}

:deep(#secondary-btn:hover) {
  border-color: var(--color-primary);
  box-shadow: 0 10px 24px color-mix(in srgb, var(--color-primary) 22%, transparent);
}
</style>
<script setup>

import IconPalette from "@/components/header/IconPalette.vue";
import { useRouter } from 'vue-router';

const router = useRouter();
</script>

<template>
  <div class="header-logo-container"
       @click="router.push('/')">
    <icon-palette></icon-palette>
    <span class="logo-text">Canvas Clues</span>
  </div>
</template>

<style scoped>
.header-logo-container {
  border-radius: 20px;
  color: var(--color-bg-base);
  display: flex;
  align-items: center;
  gap: 1rem;
  background-color: var(--color-primary);
  padding: 10px 20px;
  cursor: pointer;
  transition: box-shadow 180ms ease, border-color 180ms ease;
  transform: scale(clamp(0.6, 1.2vh, 1));
  transform-origin: left center;
}

.header-logo-container:hover {
  box-shadow: 0 10px 24px color-mix(in srgb, var(--color-primary) 22%, transparent);
}

.logo-text {
  text-transform: uppercase;
  font-family: var(--font-display), sans-serif;
  font-weight: 700;
  letter-spacing: 2px;
  font-size: 24px;
}
</style>
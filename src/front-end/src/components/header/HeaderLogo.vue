<script setup>

import IconPalette from "@/components/header/IconPalette.vue";
</script>

<template>
  <div class="header-logo-container">
    <icon-palette></icon-palette>
    <span class="logo-text">Canvas Clues</span>
  </div>
</template>

<style scoped>
.header-logo-container {
  color: var(--text-color);
  display: flex;
  align-items: center;
  gap: 1rem;
  background-color: var(--primary-color);
  padding: 10px 20px;
}

.logo-text {
  text-transform: uppercase;
  font-family: var(--font-display),sans-serif;
  font-weight: 700;
  letter-spacing: 2px;
  font-size: 24px;
}
</style>
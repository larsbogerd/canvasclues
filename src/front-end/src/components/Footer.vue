<script setup>
function year() {
  return new Date().getFullYear()
}
</script>

<template>
  <footer class="footer">
    <p>Created by V-Tek · Canvas Clues · {{ year() }}</p>
  </footer>
</template>

<style scoped>
.footer {
  position: absolute;
  bottom: 1rem;
  left: 2rem;
  font-family: var(--font-secondary),serif;
  font-weight: 300;
  font-size: 0.9rem;
  color: #555;
}
</style>
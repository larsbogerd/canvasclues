<template>
  <footer class="footer">
    <p>Created by V-Tek · Canvas Clues · {{ year }}</p>
  </footer>
</template>

<script>
export default {
  name: 'AppFooter',
  computed: {
    year() {
      return new Date().getFullYear()
    }
  }
}
</script>

<style scoped>
.footer {
  position: absolute;
  bottom: 1rem;
  left: 2rem;
  font-family: 'DM Sans', sans-serif;
  font-weight: 300;
  font-size: 0.9rem;
  color: #555;
}
</style>
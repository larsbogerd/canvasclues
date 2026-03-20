<script setup>
const props = defineProps({
  fase: String
})
</script>

<template>
  <div class="fase-label">
    <p class="text-newAmsterdam">
      Huidige Fase <br>
      {{ fase }}
    </p>
  </div>
</template>

<style scoped>
.fase-label {
  border-radius: 20px;
  background: #ffffff;
  width: 340px;
  box-shadow: 0 2px 8px var(--primary-shadow);
}

p {
  text-align: center;
  font-size: 48px;
}
</style>
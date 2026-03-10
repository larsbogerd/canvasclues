<script setup>
const props = defineProps({
  fase: String
})
</script>

<template>
<div class="fase-label">
  <p class="text-newAmsterdam">
    huidige Fase <br>
    {{ fase }}
  </p>
</div>
</template>

<style scoped>
.fase-label{
  border-radius: 20px;
  background: var(--secondary-color);
  width: 410px;
  height: 347px;
  margin: 20px;
}
p{
  padding: 20px;
  text-align: center;
  font-size: 48px;
}
</style>
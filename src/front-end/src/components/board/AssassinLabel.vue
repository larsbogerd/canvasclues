<script setup>

</script>

<template>

  <div class="Assassin-label">
    <div v-for="i in 6" :key="i" class="container">

      <div class="label-block">
      </div>

      <p class="label">
        vervalst
      </p>
    </div>

  </div>
</template>

<style scoped>
.Assassin-label{
  display: flex;
  position: absolute;
  width: max-content;
  height: 24px;
  background-color: var(--color-tertiary-active);
  z-index: 1;
  bottom: 30%;
  right: -30%;
  transform: rotate(6deg);
}

.container{
  display: flex;
  justify-content: center;
  align-items: center;
}
.label{
  margin: 0 4px;
  color: var(--assasin-red);
  font-weight: 700;
  font-family: var(--font-secondary),sans-serif;
  font-size: 1rem;
  text-transform: uppercase;
  letter-spacing: 0.02em;
}

.label-block{
  width: 60px;
  height: 100%;
  background-color: var(--icon-primary);
}

</style>

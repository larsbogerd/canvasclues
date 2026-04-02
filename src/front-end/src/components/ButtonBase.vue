<template>
  <button><slot></slot></button>
</template>

<style scoped>
button {
  font-size: 1.2rem;
  padding: 0.6rem 4rem;
  border-radius: 20px;
  cursor: pointer;
  background-color: var(--primary-color);
  color: var(--background-color);
  border: none;
}

button:hover {
  opacity: 0.9;
}

</style>
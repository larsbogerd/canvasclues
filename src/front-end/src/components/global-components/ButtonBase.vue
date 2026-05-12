<template>
  <button><slot></slot></button>
</template>

<style scoped>
button {
  font-size: 1.2rem;
  padding: 0.6rem 4rem;
  border-radius: 20px;
  cursor: pointer;
  background-color: var(--color-primary);
  color: var(--color-bg-base);
  border: none;
  box-shadow: 0 10px 20px color-mix(in srgb, var(--color-primary) 28%, transparent);
  transition: border-color 180ms ease, box-shadow 180ms ease, opacity 180ms ease;
}

button:hover {
  box-shadow: 0 14px 24px color-mix(in srgb, var(--color-primary) 38%, transparent);
}

</style>
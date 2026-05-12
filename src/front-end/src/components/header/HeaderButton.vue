<script setup>

import IconUser from "@/components/header/IconUser.vue";

const props = defineProps({
  username: String,
})
</script>

<template>
<button class="button">
  <IconUser></IconUser>
  <span class="usernameField">{{props.username}}</span>
</button>
</template>

<style scoped>
.usernameField{
  font-family: var(--font-secondary), sans-serif;
  font-size: 0.9rem;
  color: var(--text-primary);
}

.button{
  display: flex;
  align-items: center;
  gap: 0.6rem;
  padding: 8px 20px;
  font-weight: bold;
  font-family: var(--font-secondary),sans-serif;
  color: var(--text-primary);
  background-color: var(--color-secondary);
  border: 2px solid var(--button-border);
  border-radius: 20px;
  cursor: pointer;
  box-shadow: 0 14px 28px var(--tertiary-shadow);
  transition: border-color 180ms ease, box-shadow 180ms ease, transform 180ms ease;
}

.button:hover,
.button:focus-visible {
  border-color: var(--color-primary);
  box-shadow: 0 10px 24px color-mix(in srgb, var(--color-primary) 22%, transparent);
}

</style>

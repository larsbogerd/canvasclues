<script setup>
const props = defineProps({
  username: String,
})
</script>

<template>
<button class="button">
  <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="text-gray-500 group-hover:text-[#e97d81] transition-colors duration-200">
    <path d="M19 21v-2a4 4 0 0 0-4-4H9a4 4 0 0 0-4 4v2"></path>
    <circle cx="12" cy="7" r="4"></circle>
  </svg>
  <span class="usernameField">{{props.username}}</span>
</button>
</template>

<style scoped>
.usernameField{
  font-family: var(--font-secondary), sans-serif;
  font-size: 0.9rem;
  color: var(--text-color);
}

.button{
  display: flex;
  align-items: center;
  gap: 0.6rem;
  padding: 8px 20px;
  font-weight: bold;
  font-family: var(--font-secondary);
  color: var(--text-color);
  background-color: white;
  border: 2px solid #e2d8c8;
  border-radius: 20px;
  cursor: pointer;
  box-shadow: 0 14px 28px rgba(124, 97, 62, 0.08);
  transition: border-color 180ms ease, box-shadow 180ms ease, transform 180ms ease;
}

.button:hover,
.button:focus-visible {
  border-color: var(--primary-color);
  box-shadow: 0 10px 24px color-mix(in srgb, var(--primary-color) 22%, transparent);
}

</style>

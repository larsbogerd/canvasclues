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
font-family: var(--font-secondary),sans-serif;
}

.button{
  display: flex;
  gap: 1rem;
  padding: 10px 40px;
  min-height: 20px;
  min-width:80px;
}

</style>

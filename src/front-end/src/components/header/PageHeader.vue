<script setup>
import HeaderLogo from "@/components/header/HeaderLogo.vue";
import HeaderButton from "@/components/header/HeaderButton.vue";

const props = defineProps({
  username: String,
  phase: String,
})

</script>

<template>
  <div class="header-element">
  <header-logo/>
    <p class="role">{{ props.phase }}</p>
    <div class="header-right">
      <header-button :username="props.username"/>
    </div>
  </div>
</template>

<style scoped>
.header-element {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 10vh;
  min-height: 56px;
  padding: 0 2vw;
  border-bottom: 1px solid var(--button-border);
  z-index: 1;
}

.role {
  font-family: var(--font-main), sans-serif;
  font-size: clamp(1.5rem, 2.8vw, 2.4rem);
  font-weight: 500;
  color: var(--text-primary);
}

.header-right {
  display: flex;
  gap: 1.5rem;
  align-items: center;
}
</style>
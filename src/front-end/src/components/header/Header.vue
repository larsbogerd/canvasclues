<script setup>
import HeaderLogo from "@/components/header/HeaderLogo.vue";
import HeaderButton from "@/components/header/HeaderButton.vue";

const props = defineProps({
  username: String,
})

</script>

<template>
  <div class="header-element">
<header-logo/>
    <div class="header-left">
      <slot name="tutorial-button"></slot>
      <header-button :username="props.username"/>
    </div>
  </div>
</template>

<style scoped>
.header-element {
  display: flex;
  align-items: center;
  justify-content: space-between;
  max-height: 10vh;
  height: 10vh;
  padding: 0 2vw;
  background-color: inherit;
  box-shadow: var(--primary-shadow) 4px 4px 4px 4px;
  z-index: 1;
}



.header-left {
  display: flex;
  gap: 1.5rem;
  align-items: center;
}
</style>
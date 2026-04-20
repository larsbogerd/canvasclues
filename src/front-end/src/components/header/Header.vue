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
    <div class="header-right">
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
  height: 10vh;
  min-height: 56px;
  padding: 0 2vw;
  background-color: var(--background-color);
  border-bottom: 1px solid #e2d8c8;
  z-index: 1;
}


.header-right {
  display: flex;
  gap: 1.5rem;
  align-items: center;
}
</style>
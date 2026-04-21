<script setup>
import RightHalfStartingPage from "@/components/pages/StartPage/RightHalfStartingPage.vue";
import LeftHalfStartingPage from "@/components/pages/StartPage/LeftHalfStartingPage.vue";
import Footer from "@/components/global-components/Footer.vue";
</script>

<template>
  <div class="page">
    <LeftHalfStartingPage></LeftHalfStartingPage>
    <RightHalfStartingPage></RightHalfStartingPage>
    <Footer></Footer>
  </div>
</template>

<style>
html, body {
  margin: 0;
  padding: 0;
  height: 100%;
  width: 100%;
}

.page {
  position: relative;
  display: flex;
  height: 100vh;
  width: 100vw;
  background-color: var(--background-color);
  overflow: hidden;
}
</style>

<script setup>
import { useRouter } from 'vue-router'

const router = useRouter()

function goToSpyPhase() {
  router.push('/Spy')
}
</script>

<template>
<p>FirstPage</p>
  <button @click="goToSpyPhase">
    To Spymaster Phase
  </button>
</template>

<style scoped>

</style>
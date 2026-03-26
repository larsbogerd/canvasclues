<script setup>
import {onUpdated, ref} from "vue";

let gameId = ref()
onUpdated(() => {
  console.log(gameId.value)
});

</script>
<template>
    <RouterView></RouterView>
</template>

<style scoped>

</style>

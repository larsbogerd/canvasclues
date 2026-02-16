<script setup>
import axios from 'axios';
import {onMounted, ref} from "vue";

const datas = ref([])
const dataNames = ref([])


async function fetchInitialData() {
  console.log("test")
  const RecipesResponse = await axios.get('http://localhost:8082/api/v1/artpieces')

  datas.value = RecipesResponse.data.slice(0)

}

onMounted(async () => {
  await fetchInitialData()
  for (let data of datas.value) {
    dataNames.value.push(data);
    console.log(dataNames.value)
  }
})

</script>

<template>
  <p v-for="data in datas">{{ data.artPieceName }}</p>
  <img src="https://placehold.co/600x400" alt="placeholder img van 600x400">
</template>

<style scoped>


</style>

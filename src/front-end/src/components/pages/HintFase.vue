<script setup>
import inputField from "@/components/InputField.vue";
import axios from "axios";
import {onMounted, ref} from "vue";
import Grid from "@/components/grid.vue";
const hintInput = ref("");
const cards = ref([]);
const emit = defineEmits(['game-started']);

async function submit(input) {
  let status;
  try {
    if (validateInput(input)) {
      status = await postHint(input)
      httpStatus(status)
    } else {
      console.error("invalid input");
    }
  } catch (error) {
    console.log(error)
    return error;
  }
}

function httpStatus(status) {
  if (status === 200) {
    console.log(status);
    hintInput.value = "";
  }

  else if (status === 500) {
    console.error("internal server error", status);
    return status;

  } else {
    console.warn("Unexpected status:", status);
    return status;

  }
}

function validateInput(input) {
  const regex = /^[a-zA-Z]+$/;
  return regex.test(input);
}

async function postHint(input) {
  try {
    const response = await axios.post('http://localhost:8082/api/v1/hints', {
      hintContent: input
    })
    return response.status;
  } catch (error) {
    return error.status;
  }
}

onMounted(startGame);

async function startGame() {
  try {
    const response = await axios.post('http://localhost:8082/api/v1/game/start');
    cards.value = response.data;
    console.log("Fetched hints:", cards.value);
    for (const card of cards.value){
      const gameID = card.gameId;
      console.log(gameID);
      emit('game-started', gameID);
    }
  } catch(error) {
    console.log(error);
  }
}

</script>

<template>
  <div class="screen">
    <div class="grid">
      <grid :cards="cards"/>
    </div>
    <input-field name="submit" v-on:submit="submit" v-model="hintInput" label="jou hint:" condition=""/>
  </div>
</template>

<style scoped>
.screen {
  background-color: red;
  gap: 20px;
  display: grid;
  grid-template-columns: 1fr 1fr;
  justify-items: center;
  align-items: center;
}
</style>
<script setup>
import inputField from "@/components/InputField.vue";
import axios from "axios";
import {onMounted, ref} from "vue";
import Grid from "@/components/Grid.vue";
import FaseLabel from "@/components/faseLabel.vue";
import { useRouter } from 'vue-router'
const hintInput = ref("");
const cards = ref([]);
const selectedCards = ref([]);
const emit = defineEmits(['game-started']);
const router = useRouter();
let gameId;

async function submit(input) {
  let status;
  try {
    if (validateInput(input)) {
      await patchCards()

      status = await postHint(input)
      httpStatus(status)
      await router.push('/Operative');
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



onMounted(startGame);

async function startGame() {
  try {
    const response = await axios.post('http://localhost:8082/api/v1/game/start');
    cards.value = response.data;
    console.log("Fetched hints:", cards.value);
    for (const card of cards.value){
      gameId = card.gameId;
      emit('game-started', gameId);
    }
  } catch(error) {
    console.log(error);
  }
}

async function postHint(input) {
  try {
    const response = await axios.post('http://localhost:8082/api/v1/hints', {
      hintContent: input,
      gameId: gameId
    })
    return response.status;
  } catch (error) {
    return error.status;
  }
}

function handleCardClicked(id, clicked) {
  if (clicked) {
    selectedCards.value.push(id);
  } else {
    selectedCards.value = selectedCards.value.filter(cardId => cardId !== id);
  }
  console.log("Selected cards:", selectedCards.value);
}

async function patchCards() {
  try {
    const response = await axios.patch('http://localhost:8082/api/v1/game/cards', {
      cardIds: selectedCards.value,
      spymasterPick: true
    })
    console.log("Patched cards:", response.data);
    return response.status;
  } catch (error) {
    console.log(error);
    return error.status;
  }
}

</script>

<template>
  <div class="screen">
      <grid :cards="cards" @card-clicked="handleCardClicked"/>
    <div style="text-align: center;">
    <p>Selected: {{ selectedCards.length }}</p>
      <div class="info">
        <fase-label fase="Spymaster"/>
        <input-field name="submit" v-on:submit="submit" v-model="hintInput" label="jouw hint:" condition=""/>
      </div>
    </div>
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

.info{
  display: grid;
  justify-items: center;
}

</style>

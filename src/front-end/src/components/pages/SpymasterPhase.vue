<script setup>
import inputField from "@/components/InputField.vue";
import axios from "axios";
import {onMounted, ref} from "vue";
import Grid from "@/components/Grid.vue";
import FaseLabel from "@/components/FaseLabel.vue";
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
      await router.push('/gamehub');
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
      content: input,
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
    const response = await axios.patch('http://localhost:8082/api/v1/game/updatecards', {
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
    <grid class="grid" :cards="cards" @card-clicked="handleCardClicked"/>
    <div class="sidebar">
      <fase-label fase="Spymaster"/>
      <div class="hint-card">
        <div class="hint-body">
          <p>Geselecteerd: {{ selectedCards.length }}</p>
          <input-field name="Bevestig hint" v-on:submit="submit" v-model="hintInput" label="Jouw hint"/>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.screen {
  background-color: var(--background-color);
  gap: 20px;
  display: grid;
  grid-template-columns: 2fr 1fr;
  align-items: center;
  width: 100vw;
  height: 100vh;
  box-sizing: border-box;
  padding: 20px;
}

.grid {
  justify-self: center;
}

.sidebar {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 20px;
}

.hint-card {
  background: white;
  border-radius: 20px;
  width: 340px;
  overflow: hidden;
  box-shadow: 0 2px 8px var(--primary-shadow);
}

.hint-body {
  padding: 24px;
}

.hint-body p {
  font-family: var(--font-secondary);
  font-size: 18px;
  margin: 0 0 16px 0;
  color: var(--text-color);
}
</style>
<script setup>
import axios from 'axios';
import {onMounted, ref} from "vue";
import InputField from "@/components/InputField.vue";
import Grid from "@/components/Grid.vue";
import FaseLabel from "@/components/faseLabel.vue";
import {useRouter} from 'vue-router'

const router = useRouter();
let hintInput = ref("");
const cards = ref([]);
const hint = ref(null);
const selectedCards = ref([]);


onMounted(() => {
  getHint();
  getGrid();
});

async function getGameId(){
  return 1;
}

async function getHint() {
  try {
    const id = await getGameId()
    console.log(id);
    const response = await axios.get(`http://localhost:8082/api/v1/hints/${id}`);
    hint.value = response.data;
  } catch(error) {
    console.log(error.status);
  }
}

async function submit() {
  try {
    let correctCards = await getSelectedCards();
    for (let [cardId, isCorrect] of Object.entries(correctCards.data)) {
      let cardToUpdate = cards.value.find(card => card.id === cardId);
      if (cardToUpdate) {
        cardToUpdate.color = isCorrect ? 'right' : 'wrong';
      }
    }
  } catch (error) {
    console.log(error);
  }
}

function handleCardClicked(id, clicked) {
  if (clicked) {
    selectedCards.value.push(id);
  } else {
    selectedCards.value = selectedCards.value.filter(cardId => cardId !== id);
  }
  console.log("op Selected cards:", selectedCards.value);

}

async function getSelectedCards(){
  try{
    const response = await axios.post("http://localhost:8082/api/v1/game/checkcards",
      selectedCards.value
    )
    console.log(response.data);
    return response;
  } catch(error){
    return error.status
  }
}

async function getGrid() {
  try {
    const id = await getGameId()
    console.log(id)
    const response = await axios.get(`http://localhost:8082/api/v1/game/${id}`);
    cards.value = response.data;
  } catch(error) {
    console.log(error);
  }
}

</script>

<template>
  <div class="screen">
    <grid :cards="cards" @card-clicked="handleCardClicked" :color="color"/>
    <div style="text-align: center;">
      <p>Selected: {{ selectedCards.length }}</p>
      <div class="info">
        <fase-label fase="Operative"/>
        <input-field readonly="readonly" name="end turn" v-on:submit="submit" v-model="hintInput"
                     v-if="hint" :label="'jouw hint is: '+hint.hintContent"/>
      </div>
    </div>
  </div>
</template>

<style scoped>
.screen {
  background-color: green;
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

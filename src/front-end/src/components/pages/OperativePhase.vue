<script setup>
import axios from 'axios';
import {onMounted, ref} from "vue";
import Grid from "@/components/board/Grid.vue";
import FaseLabel from "@/components/board/FaseLabel.vue";
import {useRouter, useRoute} from 'vue-router'
import Header from "@/components/header/Header.vue";
import TutorialButton from "@/components/TutorialButton.vue";
import OperativeModalContent from "@/components/ModalContent/OperativeModalContent.vue";
import BaseModal from "@/components/BaseModal.vue";
import OperativeResultModalContent from "@/components/ModalContent/OperativeResultModalContent.vue";

const router = useRouter();
const route = useRoute();
const modal = ref(null)
const cards = ref([]);
const hint = ref(null);
const selectedCards = ref([]);
const correctAmount = ref(0);
const amount = ref(0);

onMounted (async () => {
  await getHint();
  await getGrid();
  amount.value = await getCountOfCardsSelectedBySpymaster();
});

function getGameId(){
  return route.params.gameId;
}

async function getHint() {
  try {
    const id = await getGameId()
    const response = await axios.get(`http://localhost:8082/api/v1/hints/${id}`);
    hint.value = response.data;
  } catch(error) {
    console.log(error.status);
  }
}

async function submit() {
  try {
    correctAmount.value = 0;
    let correctCards = await getSelectedCards();
    for (let [cardId, isCorrect] of Object.entries(correctCards.data)) {
      let cardToUpdate = cards.value.find(card => card.id === cardId);
      if (cardToUpdate) {
        cardToUpdate.color = isCorrect ? 'right' : 'wrong';
        if (isCorrect) correctAmount.value++;
      }
    }
    modal.value.show();
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
}

async function getSelectedCards(){
  try{
    const response = await axios.post("http://localhost:8082/api/v1/game/checkcards",
      selectedCards.value
    )
    return response;
  } catch(error){
    return error.status
  }
}

async function getGrid() {
  try {
    const id = await getGameId()
    const response = await axios.get(`http://localhost:8082/api/v1/game/${id}`);
    cards.value = response.data;
  } catch(error) {
    console.log(error);
  }
}

async function getCountOfCardsSelectedBySpymaster() {
    let correctAmount = 0;
  for (const card of cards.value){
    if(card.spymasterPick === true){
      correctAmount++
    }
  }
  return correctAmount;
}
</script>

<template>
  <div class="operative-phase">
  <Header #tutorial-button username="V-Tek">
    <TutorialButton>
      <OperativeModalContent></OperativeModalContent>
    </TutorialButton>
  </Header>
  <div class="screen">
    <grid class="grid" :cards="cards" @card-clicked="handleCardClicked" :color="color"/>
    <div class="sidebar">
      <fase-label fase="Operative"/>
      <div class="hint-card" v-if="hint">
        <div class="hint-header">
          <span>{{ hint.content }}</span>
          <span class="hint-number">{{ amount }}</span>
        </div>
        <div class="hint-body">
          <p>Nog {{ amount }} kunstwerken te vinden</p>
          <p>Geselecteerd: {{ selectedCards.length }}</p>
          <p>Correct geraden: {{correctAmount}}</p>
          <button class="end-turn-btn" @click="submit">Beëindig poging</button>
        </div>
      </div>
    </div>
  </div>
  </div>

  <BaseModal ref="modal">
    <OperativeResultModalContent :correctAmount="correctAmount" :amount="amount" :selectedAmount="selectedCards.length"></OperativeResultModalContent>
  </BaseModal>
</template>

<style scoped>
.operative-phase {
  display: flex;
  flex-direction: column;
  max-height: 100vh;
  overflow: hidden;
  background-color: var(--background-color);
}

.screen {
  background-color: inherit;
  gap: 20px;
  display: grid;
  grid-template-columns: 2fr 1fr;
  align-items: center;
  width: 100vw;
  height: 92vh;
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

.hint-header {
  font-family: var(--font-display), sans-serif;
  font-size: 32px;
  font-weight: bold;
  text-transform: uppercase;
  text-align: center;
  padding: 16px;
  border-bottom: 2px solid #e0e0e0;
}

.hint-number {
  color: #ff0000;
  margin-left: 8px;
}

.hint-body {
  padding: 20px 24px;
}

.hint-body p {
  font-family: var(--font-secondary),sans-serif;
  font-size: 18px;
  margin: 4px 0;
  color: var(--text-color);
}

.end-turn-btn {
  display: block;
  width: 100%;
  margin-top: 16px;
  padding: 12px;
  font-size: 16px;
  font-weight: bold;
  color: white;
  background-color: #333333;
  border-radius: 20px;
  cursor: pointer;
}

.end-turn-btn:hover {
  opacity: 0.9;
}
</style>

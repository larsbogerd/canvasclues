<script setup>
import axios from 'axios';
import {onMounted, ref, provide} from "vue";
import Grid from "@/components/board/Grid.vue";
import FaseLabel from "@/components/board/FaseLabel.vue";
import ArtInfo from "@/components/board/ArtInfo.vue";
import {useRoute, useRouter} from 'vue-router'
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
const activeCard = ref(null);
const amount = ref(0);

provide("submitFn", lockIn)
provide("handleCardClickedFn", handleCardClicked)
onMounted(async () => {
  await getHint();
  await getGrid();
  amount.value = await getCountOfCardsSelectedBySpymaster();
});

function getGameId() {
  return route.params.gameId;
}

async function getHint() {
  try {
    const id = await getGameId()
    const response = await axios.get(`http://localhost:8082/api/v1/hints/${id}`);
    hint.value = response.data;
  } catch (error) {
    console.log(error.status);
  }
}

correctAmount.value = 0;
async function lockIn(cardId) {
  const response = await getSelectedCards();
  selectedCards.value.push(cardId)
  for (const [id, isCorrect] of Object.entries(response.data)) {
    if(id === cardId){
      let cardToUpdate = cards.value.find(card => card.id === cardId);
      if (cardToUpdate) {
        cardToUpdate.color = isCorrect ? 'right' : 'wrong';
        if (isCorrect) correctAmount.value++;
        break;
      }
    }
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

function handleInfoClicked(id) {
  activeCard.value = cards.value.find(c => c.id === id) ?? null;
}

async function getSelectedCards() {
  try{
    const response = await axios.post("http://localhost:8082/api/v1/game/checkcards",
        selectedCards.value
    )
    return response;
  } catch (error) {
    return error.status
  }
}

async function getGrid() {
  try {
    const id = await getGameId()
    const response = await axios.get(`http://localhost:8082/api/v1/game/${id}`);
    cards.value = response.data;
  } catch (error) {
    console.log(error);
  }
}

async function getCountOfCardsSelectedBySpymaster() {
  let correctAmount = 0;
  for (const card of cards.value) {
    if (card.spymasterPick === true) {
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
    <div class="layout">
      <grid class="grid" :cards="cards" :active-info-id="activeCard?.id" phase="operative" @card-clicked="handleCardClicked" @info-clicked="handleInfoClicked"/>
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
            <p>Correct geraden: {{ correctAmount }}</p>
            <button class="end-turn-btn" @click="submit">Beëindig poging</button>
          </div>
        </div>
        <ArtInfo v-if="activeCard"
                 :title="activeCard.title"
                 :artist="activeCard.artistDisplay"
                 :date="activeCard.dateDisplay"
                 :medium="activeCard.mediumDisplay"
                 :origin="activeCard.placeOfOrigin"
                 @close="activeCard = null"
        />
      </div>
    </div>
  </div>
  </div>
  <BaseModal ref="modal">
    <OperativeResultModalContent :correctAmount="correctAmount" :amount="amount"
                                 :selectedAmount="selectedCards.length"></OperativeResultModalContent>
  </BaseModal>
</template>

<style scoped>
.operative-phase {
  display: flex;
  flex-direction: column;
  height: 100vh;
  overflow: hidden;
  background-color: var(--background-color);
}

.screen {
  background-color: var(--background-color);
  width: 100%;
  flex: 1;
  min-height: 0;
  display: flex;
  justify-content: center;
  align-items: center;
}

.layout {
  --layout-height: 85vh;
  --layout-padding: 20px;
  display: grid;
  grid-template-columns: 1fr auto;
  grid-template-rows: 1fr;
  align-items: start;
  gap: 32px;
  height: var(--layout-height);
  box-sizing: border-box;
  padding: var(--layout-padding);
}


.grid {
  justify-self: center;
  height: 100%;
  max-width: calc(var(--layout-height) - var(--layout-padding) * 2);
}

.sidebar {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 14px;
}

.hint-card {
  width: 340px;
  border-radius: 24px;
  border: 1px solid #e2d8c8;
  box-shadow: 0 2px 8px var(--primary-shadow);
  background: #ffffff;
}

.hint-header {
  font-family: var(--font-secondary), sans-serif;
  font-size: clamp(1.4rem, 2.5vw, 2.2rem);
  font-weight: 800;
  text-transform: uppercase;
  letter-spacing: 0.06em;
  text-align: center;
  padding: 20px 16px;
  border-bottom: 2px solid #e0e0e0;
}

.hint-number {
  color: #ff0000;
  margin-left: 8px;
  font-weight: 900;
}

.hint-body {
  padding: 24px;
}

.hint-body p {
  font-family: var(--font-secondary),sans-serif;
  font-size: clamp(0.9rem, 1.2vw, 1.125rem);
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
  font-family: var(--font-secondary);
  color: var(--text-color);
  background-color: white;
  border: 2px solid #e2d8c8;
  border-radius: 20px;
  cursor: pointer;
  box-shadow: 0 14px 28px rgba(124, 97, 62, 0.08);
  transition: border-color 180ms ease, box-shadow 180ms ease, transform 180ms ease;
}

.end-turn-btn:hover,
.end-turn-btn:focus-visible {
  border-color: var(--primary-color);
  box-shadow: 0 10px 24px color-mix(in srgb, var(--primary-color) 22%, transparent);
}
</style>

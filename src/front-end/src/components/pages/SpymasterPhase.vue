<script setup>
import InputField from "@/components/board/InputField.vue";
import axios from "axios";
import { onMounted, ref, computed } from "vue";
import GameGrid from "@/components/board/GameGrid.vue";

import {startGameCall} from "@/assets/composables/StartGameService.js";
import {submitSpymasterTurn} from "@/assets/composables/SubmitService.js";

import ArtInfo from "@/components/board/ArtInfo.vue";
import { useRouter } from 'vue-router'
import PageHeader from "@/components/header/PageHeader.vue";
import BaseModal from "@/components/modalpopup/BaseModal.vue";
import SpymasterResultModalContent from "@/components/modalpopup/modalcontent/SpymasterResultModalContent.vue";
import GameRules from "@/components/board/GameRules.vue";
import SpymasterModalContent from "@/components/modalpopup/modalcontent/SpymasterModalContent.vue";

const hintInput = ref("");
const modal = ref(null);
const cards = ref([]);
const selectedCards = ref([]);
const activeCard = ref(null);
const emit = defineEmits(['game-started']);

let gameId;

onMounted(startGame);

async function submit(input) {
  let status;
  try {
    if (!validateInput(input)) {
      console.error("invalid hint input");
      return;
    }

    if (selectedCards.value.length < 2) {
      console.error("Spymaster must select at least 2 cards");
      return;
    }

    status = await submitSpymasterTurn(gameId, {
      cardIds: selectedCards.value,
      spyScore: selectedCards.value.length,
      hintContent: input,
    });

    httpStatus(status);
    modal.value.show();
  } catch (error) {
    console.log(error);
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

async function startGame() {
  try{
    cards.value = await startGameCall();
    console.log("Fetched hints:", cards.value);
    for (const card of cards.value){
      gameId = await card.gameId;
      emit('game-started', gameId);
    }
  } catch(error) {
    console.log(error);
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

function handleInfoClicked(id) {
  activeCard.value = cards.value.find(c => c.id === id) ?? null;
}

const errorMessage = ref("");
errorMessage.value = "";



</script>

<template>
  <div class="spymaster-phase">
    <div class="header">
      <PageHeader username="V-Tek" phase="Spymaster">
      </PageHeader>
    </div>

  <div class="screen">
    <GameRules class="game-rules">
      <SpymasterModalContent>
      </SpymasterModalContent>
    </GameRules>
    <div class="layout">
      <GameGrid class="grid" phase="spymaster"
                 :cards="cards"
                 :active-info-id="activeCard?.id"
                 @card-clicked="handleCardClicked"
                 @info-clicked="handleInfoClicked"/>

      <div class="sidebar">
        <div class="hint-card">
          <div class="hint-body">
            <p>Maximale score: {{ selectedCards.length * 20}}</p>
            <InputField name="Bevestig hint"
                         v-on:submit="submit"
                         v-model="hintInput"
                         label="Jouw hint"/>
          </div>
        </div>

        <ArtInfo v-if="activeCard"
                 :title="activeCard.title"
                 :artist="activeCard.artistDisplay"
                 :date="activeCard.dateDisplay"
                 :medium="activeCard.mediumDisplay"
                 :origin="activeCard.placeOfOrigin"
                 :artworkId="activeCard.artworkId"
                 :department="activeCard.department"
        />
      </div>

    </div>
  </div>
  </div>
  <BaseModal ref="modal">
      <SpymasterResultModalContent
          :score="selectedCards.length * 20"/>
  </BaseModal>
</template>

<style scoped>
.spymaster-phase {
  display: flex;
  flex-direction: column;
  height: 100vh;
  overflow: hidden;
  background-color: var(--color-bg-base);
}

.screen {
  width: 100%;
  flex: 1;
  min-height: 0;
  display: flex;
  justify-content: center;
  align-items: center;
}

.game-rules {
  margin-top: 5vh;
  align-self: flex-start;
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
  width: 17vw;
  border-radius: 24px;
  border: 1px solid var(--button-border);
  box-shadow: 0 2px 8px var(--primary-shadow);
  background: var(--color-secondary);
}

.hint-body {
  padding: 24px;
}

.hint-body p {
  font-family: var(--font-secondary),sans-serif;
  font-size: 18px;
  margin: 0 0 14px 0;
  color: var(--text-primary);
}

</style>

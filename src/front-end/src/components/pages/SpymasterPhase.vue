<script setup>
import InputField from "@/components/board/InputField.vue";
import {onMounted, ref, watch} from "vue";
import GameGrid from "@/components/board/GameGrid.vue";

import {startGameCall} from "@/assets/composables/StartGameService.js";
import {submitSpymasterTurn} from "@/assets/composables/SubmitService.js";

import ArtInfo from "@/components/board/ArtInfo.vue";
import PageHeader from "@/components/header/PageHeader.vue";
import BaseModal from "@/components/modalpopup/BaseModal.vue";
import SpymasterResultModalContent from "@/components/modalpopup/modalcontent/SpymasterResultModalContent.vue";
import GameRules from "@/components/board/GameRules.vue";
import SpymasterModalContent from "@/components/modalpopup/modalcontent/SpymasterModalContent.vue";
import HintCard from "@/components/board/HintCard.vue";
import SpymasterHintCardHeaderContent from "@/components/board/hintCardContent/SpymasterHintCardHeaderContent.vue";
import SpymasterHintCardContent from "@/components/board/hintCardContent/SpymasterHintCardContent.vue";

const hintInput = ref("");
const modal = ref(null);
const cards = ref([]);
const selectedCards = ref([]);
const activeCard = ref(null);
const emit = defineEmits(['game-started']);
const showInputErrorPopup = ref(false);
const inputErrorMessage = ref("Dat is geen valide input.");
let gameId;

onMounted(startGame);

async function submit(input) {
  let status;
  try {
    if (!validateInput(input)) {
      console.error("invalid input");
      giveInputFeedback(input);
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

watch(hintInput, () => {
  if(hintInput.value !== '' && showInputErrorPopup.value) {
    showInputErrorPopup.value = false;
  }
})

function giveInputFeedback(input) {
  hintInput.value = "";
  showInputErrorPopup.value = true;
  const hasSpace = /\s/.test(input);
  const hasSpecialChars = /[^a-zA-Z]/.test(input);

  if (hasSpace) {
    inputErrorMessage.value = "Een hint mag maar één woord bevatten.";
  } else if (hasSpecialChars) {
    inputErrorMessage.value = "Een hint mag alleen letters bevatten.";
  } else {
    inputErrorMessage.value = "Dat is geen valide input";
  }
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

function handleCardClicked(id) {
  if (selectedCards.value.includes(id)) {
    selectedCards.value = selectedCards.value.filter(cardId => cardId !== id);
  } else {
    selectedCards.value.push(id);
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
      <PageHeader username="ClueCollector" phase="Curator">
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
                 :selected-ids="selectedCards"
                 @card-clicked="handleCardClicked"
                 @info-clicked="handleInfoClicked"/>

      <div class="sidebar">
        <HintCard>
          <template #hint-header>
              <SpymasterHintCardHeaderContent
                :score="selectedCards.length * 20">
              </SpymasterHintCardHeaderContent>
          </template>
          <template #hint-body>
            <div class="hint-body">

              <SpymasterHintCardContent
                  :showInputErrorPopup="showInputErrorPopup"
                  :inputErrorMessage="inputErrorMessage"
                  v-model="hintInput"
                  @submit="submit"
              >
              </SpymasterHintCardContent>

              <InputField name="Bevestig hint"
                          v-on:submit="submit"
                          v-model="hintInput"
                          label="Jouw hint."/>

            </div>
          </template>
        </HintCard>

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


.hint-body {
  position: relative;
  padding: 24px;
}

.hint-body p {
  font-family: var(--font-secondary),sans-serif;
  font-size: 18px;
  margin: 0 0 14px 0;
}

</style>

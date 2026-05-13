<script setup>
import ArtInfo from "@/components/board/ArtInfo.vue";
import {onMounted, provide, ref} from "vue";
import GameGrid from "@/components/board/GameGrid.vue";
import FaseLabel from "@/components/board/FaseLabel.vue";
import PageHeader from "@/components/header/PageHeader.vue";
import TutorialButton from "@/components/global-components/TutorialButton.vue";
import OperativeModalContent from "@/components/modalpopup/modalcontent/OperativeModalContent.vue";
import BaseModal from "@/components/modalpopup/BaseModal.vue";
import OperativeResultModalContent from "@/components/modalpopup/modalcontent/OperativeResultModalContent.vue";
import {getSelectedCards} from "@/assets/composables/VerificationService.js";
import {finishSession, startSession} from "@/assets/composables/SessionService.js";

const modal = ref(null)
const sessionId = ref(null);
const cards = ref([]);
const hint = ref(null);
const selectedCards = ref([]);
const correctAmount = ref(0);
const activeCard = ref(null);
const amount = ref(0);

provide("submitFn", lockIn)
provide("handleCardClickedFn", handleCardClicked)
onMounted (async () => {
  const data = await startSession();
  sessionId.value = data.sessionId;
  cards.value = data.cards;
  hint.value = data.hint;
  amount.value = data.spymasterPickCount;
});


async function lockIn(cardId) {
  const response = await getSelectedCards(selectedCards);
  for (const [id, isCorrect] of Object.entries(response)) {
    if(id === cardId){
      let cardToUpdate = cards.value.find(card => card.id === cardId);
      if (cardToUpdate) {
        cardToUpdate.color = isCorrect ? 'right' : 'wrong';
        if (isCorrect) correctAmount.value++;
      }
      break;
    }
  }
}


async function submit() {
  try {
    correctAmount.value = 0;
    let correctCards = await getSelectedCards(selectedCards);
    for (let [cardId, isCorrect] of Object.entries(correctCards)) {
      let cardToUpdate = cards.value.find(card => card.id === cardId);
      if (cardToUpdate) {
        cardToUpdate.color = isCorrect ? 'right' : 'wrong';
        if (isCorrect) correctAmount.value++;
      }
    }
    await finishSession(sessionId.value, correctAmount.value)
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
</script>

<template>
  <div class="operative-phase">
    <PageHeader #tutorial-button username="V-Tek">
      <TutorialButton>
        <OperativeModalContent></OperativeModalContent>
      </TutorialButton>
    </PageHeader>
    <div class="screen">
      <div class="layout">
        <GameGrid class="grid"
                   :cards="cards"
                   :active-info-id="activeCard?.id"
                   phase="operative"
                   @card-clicked="handleCardClicked"
                   @info-clicked="handleInfoClicked"/>
        <div class="sidebar">
          <FaseLabel fase="Operative"/>
          <div class="hint-card" v-if="hint">
            <div class="hint-header">
              <span>{{ hint.content }}</span>
              <span class="hint-number">{{ amount }}</span>
            </div>
            <div class="hint-body">
              <p>Nog {{ amount - correctAmount }} kunstwerk(en) te vinden</p>
              <p>Geselecteerd: {{ selectedCards.length }}</p>
              <p>Score: {{ correctAmount }}</p>
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
          <span v-if="sessionId"
                class="session-id-chip"
                :title="sessionId">
                {{ sessionId }}
          </span>
        </div>
      </div>
    </div>
  </div>
  <BaseModal ref="modal">
    <OperativeResultModalContent :correctAmount="correctAmount"
                                 :amount="amount"
                                 :selectedAmount="selectedCards.length">
    </OperativeResultModalContent>
  </BaseModal>
</template>

<style scoped>
.operative-phase {
  display: flex;
  flex-direction: column;
  height: 100vh;
  overflow: hidden;
  background-color: var(--color-bg-base);
}

.screen {
  background-color: var(--color-bg-base);
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
  border: 1px solid var(--button-border);
  box-shadow: 0 2px 8px var(--primary-shadow);
  background: var(--color-secondary);
}

.hint-header {
  font-family: var(--font-secondary), sans-serif;
  font-size: clamp(1.4rem, 2.5vw, 2.2rem);
  font-weight: 800;
  text-transform: uppercase;
  letter-spacing: 0.06em;
  text-align: center;
  padding: 20px 16px;
  border-bottom: 2px solid var(--hint-border-bottom);
  color: var(--text-primary);
}

.hint-number {
  color: var(--color-primary);
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
  color: var(--text-primary);
}

.end-turn-btn {
  display: block;
  width: 100%;
  margin-top: 16px;
  padding: 12px;
  font-size: 16px;
  font-weight: bold;
  font-family: var(--font-secondary), sans-serif;
  color: var(--text-primary);
  background-color: var(--color-secondary);
  border: 2px solid var(--button-border);
  border-radius: 20px;
  cursor: pointer;
  box-shadow: 0 14px 28px var(--tertiary-shadow);
  transition: border-color 180ms ease, box-shadow 180ms ease, transform 180ms ease;
}

.end-turn-btn:hover,
.end-turn-btn:focus-visible {
  border-color: var(--color-primary);
  box-shadow: 0 10px 24px color-mix(in srgb, var(--color-primary) 22%, transparent);
}

.session-id-chip {
  font-family: var(--font-secondary), sans-serif;
  font-size: 0.75rem;
  color: var(--text-primary);
  border: 1px dashed var(--text-primary);
  border-radius: 10px;
  padding: 4px 10px;
  align-self: center;
}
</style>

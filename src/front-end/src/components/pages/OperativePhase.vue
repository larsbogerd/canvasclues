<script setup>
import ArtInfo from "@/components/board/ArtInfo.vue";
import {computed, onMounted, provide, ref, watch} from "vue";
import GameGrid from "@/components/board/GameGrid.vue";
import PageHeader from "@/components/header/PageHeader.vue";
import BaseModal from "@/components/modalpopup/BaseModal.vue";
import OperativeResultModalContent from "@/components/modalpopup/modalcontent/OperativeResultModalContent.vue";
import {finishSession, startSession} from "@/assets/composables/SessionService.js";
import {submitGuess} from "@/assets/composables/GuessService.js";
import GameRules from "@/components/board/GameRules.vue";
import OperativeModalContent from "@/components/modalpopup/modalcontent/OperativeModalContent.vue";

const modal = ref(null)
const sessionId = ref(null);
const cards = ref([]);
const hint = ref(null);
const selectedCards = ref([]);
const activeCard = ref(null);
const amount = ref(0);
const score = ref(0);
const combo = ref(0);
const assassinGuesses = ref(0);
const wrongGuesses = ref(0);
const correctAmount = computed(() =>
  cards.value.filter(card => card.color === 'right').length
);

provide("submitFn", lockIn)
provide("handleCardClickedFn", handleCardClicked)

watch(correctAmount, autoFinishWhenAllFound);

onMounted (async () => {
  const data = await startSession();
  sessionId.value = data.sessionId;
  cards.value = data.cards;
  hint.value = data.hint;
  amount.value = data.spymasterPickCount;
});

async function lockIn(cardId) {
  const result = await submitGuess(sessionId.value, cardId);
  if (!result) return;
  const cardToUpdate = cards.value.find(card => card.id === cardId);
  if (cardToUpdate) {
    cardToUpdate.color = result.correct ? 'right' : (result.cardType === 'ASSASSIN' ? 'wrongassassin' : 'wrong');
  }
  score.value = result.score;
  combo.value = result.comboStreak;
  wrongGuesses.value = result.wrongGuesses;
  assassinGuesses.value = result.assassinGuesses;
  console.log('result:', result);
  tooManyAssassinGuesses();
}

function tooManyAssassinGuesses() {
  if (assassinGuesses.value >= 2) {
    setTimeout(submit, 800);
  }
}

async function submit() {
  try {
    await finishSession(sessionId.value);
    if (wrongGuesses.value === 0) {
      score.value += 20;
    }
    modal.value.show();
  } catch (error) {
    console.log(error);
  }
}

function autoFinishWhenAllFound(newCorrect) {
  if (amount.value > 0 && newCorrect === amount.value) {
    setTimeout(submit, 800);
  }
}

function handleCardClicked(id, clicked) {
  if (clicked) {
    selectedCards.value.push(id);
  } else {
    selectedCards.value = selectedCards.value.filter(cardId => cardId !== id);
  }
}

const comboMultiplier = computed(() => {
  if (combo.value >= 4) return '3';
  if (combo.value === 3) return '2';
  if (combo.value === 2) return '1.5';
  return '1';
});

function handleInfoClicked(id) {
  activeCard.value = cards.value.find(c => c.id === id) ?? null;
}
</script>

<template>
  <div class="operative-phase">
    <PageHeader username="V-Tek" phase="Operative">
    </PageHeader>
    <div class="screen">
      <GameRules class="game-rules">
        <OperativeModalContent>
        </OperativeModalContent>
      </GameRules>

      <div class="layout">
        <GameGrid class="grid"
                  :cards="cards"
                  :active-info-id="activeCard?.id"
                  phase="operative"
                  @card-clicked="handleCardClicked"
                  @info-clicked="handleInfoClicked"/>
        <div class="sidebar">
          <div class="hint-card" v-if="hint">
            <div class="hint-header">
              <span>{{ hint.content }}</span>
              <span class="hint-number">{{ amount }}</span>
            </div>
            <div class="hint-body">
              <p>Nog {{ amount - correctAmount }} kunstwerk(en) te vinden</p>
              <p>Geselecteerd: {{ selectedCards.length }}</p>
              <p>Score: {{ score }}</p>
              <p>Combo: ×{{ comboMultiplier }}</p>
              <p>Assassin gokken: {{ assassinGuesses }} </p>
              <button class="end-turn-btn" @click="submit">Beëindig poging</button>
            </div>
          </div>
          <ArtInfo v-if="activeCard"
                   :title="activeCard.title"
                   :artist="activeCard.artistDisplay"
                   :medium="activeCard.mediumDisplay"
                   :origin="activeCard.placeOfOrigin"
                   :style="activeCard.altText"
                   :date="activeCard.dateDisplay"
                   :artworkId="activeCard.artworkId"
                   :department="activeCard.department"
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
                                 :score="score"
                                 :selectedAmount="selectedCards.length"
                                 :gameOver="assassinGuesses >= 2">
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

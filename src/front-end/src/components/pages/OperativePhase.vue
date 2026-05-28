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
import HintCard from "@/components/board/HintCard.vue";
import OperativeHintCardContent from "@/components/board/hintCardContent/OperativeHintCardContent.vue";
import OperativeHintCardHeaderContent from "@/components/board/hintCardContent/OperativeHintCardHeaderContent.vue";

const modal = ref(null)
const sessionId = ref(null);
const cards = ref([]);
const hint = ref(null);
const selectedCards = ref([]);
const activeCard = ref(null);
const amount = ref(0);
const score = ref(0);
const comboMultiplier = ref(0);
const wrongGuesses = ref(0);
const assassinGuesses = ref(0);

const baseScore = 20;
const comboTiers = [1, 1.5, 2, 3];


const currentCardValue = computed(() => {
  return baseScore * activeCombo.value;
});

const correctAmount = computed(() =>
  cards.value.filter(card => card.color === 'right').length
);

const activeCombo = computed(() => {
  const maxIndex = comboTiers.length - 1;
  const streak = Math.min(comboMultiplier.value, maxIndex);
  return comboTiers[Math.max(0, streak)];
});

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

  comboMultiplier.value = result.comboStreak;

  score.value = result.score;
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

function autoFinishWhenAllFound(newCorrect) {
  if (amount.value > 0 && newCorrect === amount.value) {
    setTimeout(submit, 800);
  }
}

async function submit() {
  try {
    const result = await finishSession(sessionId.value);
    if (result) score.value = result.score;
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
    <PageHeader username="ClueCollector" phase="Bezoeker">
    </PageHeader>
    <div class="screen">
      <div class="layout">

        <GameRules>
          <OperativeModalContent>
          </OperativeModalContent>
        </GameRules>

        <GameGrid class="grid"
                  :cards="cards"
                  :active-info-id="activeCard?.id"
                  phase="operative"
                  @card-clicked="handleCardClicked"
                  @info-clicked="handleInfoClicked"
        >
        </GameGrid>

        <div class="sidebar">
          <HintCard
          >
            <template #hint-header>
              <OperativeHintCardHeaderContent v-if="hint"
                  :hint="hint"
              >
              </OperativeHintCardHeaderContent>
            </template>

            <template #hint-body>
              <OperativeHintCardContent
                  :correctAmount="correctAmount"
                  :cardValue="currentCardValue"
                  :score="score"
                  @submit="submit"
              >
              </OperativeHintCardContent>
            </template>

          </HintCard>

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



.layout {
  --layout-height: 85vh;
  --layout-padding: 20px;
  display: grid;
  grid-template-columns: auto 1fr auto;
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

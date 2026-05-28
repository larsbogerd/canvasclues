<script setup>
import {computed} from "vue";
import infoIcon from '@/assets/images/icons/info.svg'
import LockButton from "@/components/board/LockButton.vue";

const props = defineProps({
  id: String,
  imgUrl: String,
  altText: String,
  color: String,
  infoActive: Boolean,
  phase: String,
  type: String,
  selected: { type: Boolean, default: false }
})

const emit = defineEmits(['card-clicked', 'info-clicked'])

const revealed = computed(() =>
  ['right', 'wrong', 'wrongassassin'].includes(props.color)
);

function handleSpymasterClick() {
  emit('card-clicked', props.id);
}

function handleOperativeClick() {
  if (!revealed.value) {
    emit('card-clicked', props.id);
  }
  emit('info-clicked', props.id);
}

</script>

<template>

  <div v-if="phase==='spymaster'"
       class="card"
       @click="handleSpymasterClick"
       :class="[props.type, { active: props.selected, 'info-shown': infoActive }]">

    <img :src="props.imgUrl"
         :alt="props.altText" draggable="false"/>

    <button class="info-btn"
            @click.stop="emit('info-clicked', props.id)">
      <img :src="infoIcon"
           alt="Info" />
    </button>
  </div>

  <div v-else-if="phase==='operative'"
       class="card"
       @click="handleOperativeClick"
       :class="[props.color, { active: props.selected, 'info-shown': infoActive }]">

    <img :src="props.imgUrl"
         :alt="props.altText"
         draggable="false"/>

    <LockButton v-if="!revealed"
                :id="props.id">
    </LockButton>
  </div>
</template>
<style scoped>

.card {
  width: 100%;
  aspect-ratio: 1;
  box-sizing: border-box;
  overflow: hidden;
  border-radius: 15%;
  user-select: none;
  position: relative;
  border: 0 solid transparent;
  cursor: pointer;
  transition:
    border-width 120ms cubic-bezier(0.2, 0.8, 0.2, 1),
    border-color 120ms cubic-bezier(0.2, 0.8, 0.2, 1),
    box-shadow 120ms cubic-bezier(0.2, 0.8, 0.2, 1);
}

.PLAYABLE{
  border: clamp(2px, 1vh,  4px) solid rgba(0, 0, 255, 0);
  cursor: pointer;
}

.ASSASSIN {
  cursor: not-allowed;
  pointer-events: none;
}

.ASSASSIN > img {
  opacity: 30%;
}

.ASSASSIN .info-btn{
  pointer-events: auto;
}

.right {
  border: clamp(4px, 1vh, 12px) solid var(--game-good) !important;
}

.wrong {
  border: clamp(4px, 1vh, 12px) solid var(--game-wrong) !important;
}

.wrongassassin {
  border: clamp(4px, 1vh, 12px) solid #000 !important;
  background: #000;
}

.wrongassassin img {
  opacity: 0.15;
}

.active {
  border: clamp(4px, 1vh, 12px) solid var(--color-primary);
  box-shadow: 7px 6px 28px 1px var(--active-card-shadow);
}

.card.active :deep(.lock-btn) {
  opacity: 1;
  pointer-events: auto;
}

.info-btn {
  position: absolute;
  top: 5%;
  right: 5%;
  background: var(--bg-light-opacity);
  border: none;
  border-radius: 50%;
  backdrop-filter: blur(4px);
  width: clamp(24px, 15%, 48px);
  height: clamp(24px, 15%, 48px);
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  opacity: 0;
  transition: opacity 200ms ease 500ms;
}

.card:hover .info-btn,
.info-shown .info-btn {
  opacity: 1;
  transition: opacity 200ms ease 100ms;
}

.info-shown .info-btn {
  background: var(--color-primary);
}

.info-shown .info-btn img {
  filter: brightness(0) invert(1);
}

.info-btn img {
  width: 50%;
  height: 50%;
}

img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  display: block;
}

</style>

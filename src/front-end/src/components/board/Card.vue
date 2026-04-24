<script setup>
import {ref} from "vue";
import infoIcon from '@/assets/images/Icons/info.svg'
import LockButton from "@/components/board/LockButton.vue";

const clicked = ref(false);

const props = defineProps({
  id: String,
  imgUrl: String,
  altText: String,
  color: String,
  infoActive: Boolean,
  phase: String,
  type: String
})

const emit = defineEmits(['card-clicked', 'info-clicked'])
</script>

<template>
  <div v-if="color==='right'" class="card right" :class="{ 'info-shown': infoActive }">
    <img :src="props.imgUrl" :alt="props.altText" draggable="false"/>
    <button class="info-btn" @click.stop="emit('info-clicked', props.id)">
      <img :src="infoIcon" alt="Info" />
    </button>
  </div>

  <div v-else-if="color==='wrong'" class="card wrong" :class="{ 'info-shown': infoActive }">
    <img :src="props.imgUrl" :alt="props.altText" draggable="false"/>
    <button class="info-btn" @click.stop="emit('info-clicked', props.id)">
      <img :src="infoIcon" alt="Info" />
    </button>
  </div>
  <div v-else-if="phase==='spymaster'" class="card" @click="clicked = !clicked;
        emit('card-clicked', props.id, clicked)"
       :class="props.type, { active: clicked, 'info-shown': infoActive }">
    <img :src="props.imgUrl" :alt="props.altText" draggable="false"/>
    <button class="info-btn" @click.stop="emit('info-clicked', props.id)">
      <img :src="infoIcon" alt="Info" />
    </button>
  </div>

  <div v-else class="card" @click="clicked = !clicked; emit('card-clicked', props.id, clicked)"
       :class="{ active: clicked, 'info-shown': infoActive }">
    <img :src="props.imgUrl" :alt="props.altText" draggable="false"/>
    <LockButton v-if="phase=='operative'" :id="props.id"></LockButton>
    <button class="info-btn" @click.stop="emit('info-clicked', props.id)">
      <img :src="infoIcon" alt="Info" />
    </button>
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
  transition:
    border-width 120ms cubic-bezier(0.2, 0.8, 0.2, 1),
    border-color 120ms cubic-bezier(0.2, 0.8, 0.2, 1),
    box-shadow 120ms cubic-bezier(0.2, 0.8, 0.2, 1);
}

.HIGH_SCORE{
  border: clamp(2px, 1vh, 4px) solid purple;
}

.MEDIUM_SCORE{
  border: clamp(2px, 1vh,  4px) solid yellow;
}

.LOW_SCORE{
  border: clamp(2px, 1vh,  4px) solid blue;
}

.GAME_OVER{
  border: clamp(2px, 1vh,  4px) solid black;
}

.right {
  border: clamp(4px, 1vh, 12px) solid var(--primary-good) !important;
}

.wrong {
  border: clamp(4px, 1vh, 12px) solid var(--primary-wrong) !important;
}

.active {
  border: clamp(4px, 1vh, 12px) solid var(--primary-color);
  box-shadow: 7px 6px 28px 1px rgba(0, 0, 0, 0.24);
}

.card.active:hover :deep(.lock-btn),
.card.active:focus-within :deep(.lock-btn) {
  opacity: 1;
  pointer-events: auto;
}

.info-btn {
  position: absolute;
  top: 5%;
  right: 5%;
  background: rgba(255, 255, 255, 0.85);
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
  background: var(--primary-color);
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

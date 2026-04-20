<script setup xmlns="http://www.w3.org/1999/html">
import closeIcon from '@/assets/images/Icons/close.svg'

const props = defineProps({
  title: String,
  artist: String,
  medium: String,
  origin: String,
  style: String,
  date: String,
})

const emit = defineEmits(['close'])
</script>

<template>
  <div class="art-info">
    <div class="art-info-header">
      <div>
        <p class="art-info-title">{{ props.title }}</p>
        <p class="art-info-subtitle">{{ props.artist }}</p>
        <p class="art-info-date">{{ props.date }}</p>
      </div>
      <button class="art-info-close" @click="emit('close')">
        <img :src="closeIcon" alt="Sluiten" draggable="false" />
      </button>
    </div>
    <div class="art-info-details">
      <p class="art-info-detail">{{ props.medium }}</p>
      <p class="art-info-detail">{{ props.origin }}</p>
    </div>
  </div>
</template>

<style scoped>
.art-info {
  background: white;
  border-radius: 20px;
  min-height: auto;
  width: 340px;
  padding: 24px;
  box-shadow: 0 2px 8px var(--primary-shadow);
  box-sizing: border-box;
}

.art-info-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  gap: 12px;
}

.art-info-title {
  font-family: var(--font-main);
  font-size: clamp(1rem, 1.5vw, 1.4rem);
  font-weight: 500;
  margin: 0 0 6px 0;
  color: var(--text-color);
}

.art-info-subtitle {
  font-family: var(--font-secondary);
  font-size: clamp(0.8rem, 1.1vw, 1rem);
  margin: 0;
  color: #4c4c4c;
}

.art-info-date {
  font-family: var(--font-secondary);
  font-size: clamp(0.7rem, 1vw, 0.9rem);
  margin: 4px 0 0;
  color: #8b7f70;
}

.art-info-details {
  border-top: 1px solid #e8e0d4;
  margin-top: 14px;
  padding-top: 12px;
}

.art-info-detail {
  font-family: var(--font-secondary);
  font-size: clamp(0.7rem, 1vw, 0.85rem);
  margin: 0 0 2px 0;
  color: #8b7f70;
}

.art-info-close {
  background: none;
  border: none;
  cursor: pointer;
  padding: 0;
  flex-shrink: 0;
  transition: opacity 180ms ease;
}

.art-info-close img {
  width: 20px;
  height: 20px;
}

.art-info-close:hover {
  opacity: 0.6;
}
</style>

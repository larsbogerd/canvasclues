<script setup>
import thumbsUpUrl from "@/assets/images/icons/thumb-up.svg";
import thumbsDownUrl from "@/assets/images/icons/thumb-down.svg";

const emit = defineEmits(['submitGameQuality'])

</script>

<template>
  <section>
    <h1 class="modal-title">Beoordeel Board</h1>
    <h2 class="modal-sub-title">Vond je dat de hint goed bij de plaatjes paste? Laat het ons weten.</h2>
    <div class="modal-stats">
      <div class="modal-stat" @click="emit('submitGameQuality', true)">
        <div class="modal-stat-value">
          <img class="like" alt="thumbs up" :src="thumbsUpUrl">
        </div>
        <div class="modal-stat-label">Goed Board</div>
      </div>
      <div @click="emit('submitGameQuality', false)" class="modal-stat">
        <div class="modal-stat-value score">
          <img class="dislike" alt="thumbs down" :src="thumbsDownUrl">
        </div>
        <div class="modal-stat-label">Slecht Board</div>
      </div>
    </div>
    <hr class="modal-divider"/>
    <div class="modal-actions">
      <div class="modal-stat-label">Het beoordelen van de hint kwaliteit is noodzakelijk
        voor het graderen van legitieme/interessante hints.
      </div>
    </div>
  </section>
</template>

<style scoped>

.modal-stat{
  background-color: var(--color-primary);
  transition: transform 180ms ease;
}

.modal-stat:hover{
  cursor: pointer;
  transform: scale(1.05);
}

.modal-stat-label{
  color: var(--text-art-details);
}

.modal-actions .modal-stat-label{
  color: var(--text-eyebrow);
}

section {
  width: 28vw;
  padding: clamp(24px, 3vw, 48px) clamp(32px, 4vw, 64px);
}
</style>
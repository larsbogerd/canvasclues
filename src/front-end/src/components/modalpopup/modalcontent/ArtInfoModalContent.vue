<script setup>
import {computed} from "vue";
import {htmlToText} from "@/assets/composables/ArtworkDetailsService.js";

const props = defineProps({
  title: String,
  artist: String,
  fullSizeUrl: String,
  details: Object,
})

const shortDescription = computed(() => htmlToText(props.details?.shortDescription))
</script>

<template>
  <div class="grid-top">
    <section class="image-panel">
      <img
          class="artwork-blur"
          :src="props.fullSizeUrl"
          alt=""
          aria-hidden="true"
      />
      <img
          class="artwork-img"
          :src="props.fullSizeUrl"
          :alt="props.title"
      />
    </section>

    <div class="grid">

      <section class="info-panel">
        <header class="title-block">
          <h1>{{ props.title }}</h1>
          <p class="artist">{{ props.artist }}</p>
        </header>
      </section>
      <div class="trait-card">
        <dl class="traits">

          <div class="trait" v-if="props.details?.dateDisplay">
            <dt>Datum</dt>
            <dd>{{ props.details.dateDisplay }}</dd>
          </div>

          <div class="trait" v-if="props.details?.mediumDisplay">
            <dt>Techniek</dt>
            <dd>{{ props.details.mediumDisplay }}</dd>
          </div>

          <div class="trait" v-if="props.details?.placeOfOrigin">
            <dt>Herkomst</dt>
            <dd>{{ props.details.placeOfOrigin }}</dd>
          </div>

          <div class="trait" v-if="props.details?.departmentTitle">
            <dt>Afdeling</dt>
            <dd>{{ props.details.departmentTitle }}</dd>
          </div>

          <div class="trait" v-if="props.details?.dimensions">
            <dt>Afmetingen</dt>
            <dd>{{ props.details.dimensions }}</dd>
          </div>

          <div class="trait" v-if="props.details?.styleTitle">
            <dt>Stijl</dt>
            <dd>{{ props.details.styleTitle }}</dd>
          </div>

          <div class="trait" v-if="props.details?.artworkTypeTitle">
            <dt>Type</dt>
            <dd>{{ props.details.artworkTypeTitle }}</dd>
          </div>
        </dl>
      </div>

      <section class="description-card" v-if="shortDescription">
        <p class="short-description">{{ shortDescription }}</p>
      </section>
    </div>
  </div>
</template>

<style scoped>
.grid-top{
  display: grid;
  grid-template-columns: 1fr 1fr;
  width: 60vw;
  height: 60vh;
}

.grid{
  display: flex;
  flex-direction: column;
  padding: clamp(24px, 3vw, 48px) clamp(32px, 4vw, 64px);
  gap: 2rem;
  min-width: 27vw;
  min-height: 0;
  overflow: auto;
}

.title-block {
  border-bottom: 1px solid var(--secondary-border);
  margin-bottom: 0.5rem;

}

.artist {
  font-family: var(--font-secondary), sans-serif;
  color: var(--text-primary);
  opacity: 0.75;
  margin-bottom: 10px;
  white-space: pre-line;
}

.image-panel {
  position: relative;
  box-shadow: 0 18px 38px var(--secondary-shadow);
  border-radius: 20px 0 0 20px;
  display: flex;
  align-items: center;
  justify-content: center;
  box-sizing: border-box;
  overflow: hidden;
  height: 100%;
  min-height: 0;
}

.artwork-blur {
  position: absolute;
  inset: 0;
  width: 100%;
  height: 100%;
  object-fit: cover;
  filter: blur(36px) saturate(1.15);
  transform: scale(1.15);
  opacity: 0.55;
  pointer-events: none;
  user-select: none;
}

.artwork-img {
  position: relative;
  width: 100%;
  height: 100%;
  object-fit: contain;
}

.trait-card {
  border: none;
}

.trait-card h2 {
  font-family: var(--font-main), serif;
  font-size: 1.25rem;
  font-weight: 500;
  color: var(--text-primary);
  margin: 0 0 12px 0;
}

.traits {
  margin: 0;
  display: grid;
  grid-template-columns: 1fr;
  gap: 2px;
}

.trait {
  display: grid;
  grid-template-columns: 140px 1fr;
  padding: 8px 0;
  border-bottom: 1px solid var(--hub-border-bottom);
  gap: 16px;
}

.trait:last-child {
  border-bottom: none;
}

.trait dt {
  font-family: var(--font-secondary), sans-serif;
  font-size: 0.8rem;
  letter-spacing: 0.06em;
  text-transform: uppercase;
  color: var(--text-primary);
  opacity: 0.55;
  margin: 0;
  align-self: center;
}

.trait dd {
  font-family: var(--font-secondary), sans-serif;
  color: var(--text-primary);
  margin: 0;
  align-self: center;
}

.description-card {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}

.short-description {
  font-family: var(--font-secondary), sans-serif;
  color: var(--text-primary);
  line-height: 1.55;
  white-space: pre-line;
  margin: 0;
}

</style>

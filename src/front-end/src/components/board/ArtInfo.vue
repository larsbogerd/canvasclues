<script setup xmlns="http://www.w3.org/1999/html">
import ExpandIcon from '@/assets/images/svg-components/ExpandIcon.vue'
import BaseModal from "@/components/modalpopup/BaseModal.vue";
import ArtInfoModalContent from "@/components/modalpopup/modalcontent/ArtInfoModalContent.vue";
import {ref} from "vue";

const props = defineProps({
  title: String,
  artist: String,
  medium: String,
  origin: String,
  style: String,
  date: String,
  artworkId: String,
  department: String,
})
const modal = ref(null)

function fullsizeUrl(id) {
  return `https://www.artic.edu/iiif/2/${id}/full/600,/0/default.jpg`;
}

function expand(){
  modal.value.show();
}

</script>

<template>
  <div class="art-info">

    <div class="art-info-header">
      <div>
        <p class="art-info-title">{{ props.title }}</p>
        <p class="art-info-subtitle">{{ props.artist }}</p>
      </div>

      <button class="art-info-expand"
              @click="expand">
          <ExpandIcon class="expand-icon"></ExpandIcon>
      </button>
    </div>

    <div class="art-info-details">
      <div class="image-panel">
        <img
            class="artwork-blur"
            :src="fullsizeUrl(props.artworkId)"
            alt=""
            aria-hidden="true"
        />
        <img class="artwork-img"
             :src="fullsizeUrl(props.artworkId)"
             :alt="props.title"
        />
      </div>
    </div>
  </div>

  <base-modal ref="modal">
    <ArtInfoModalContent
        :title="props.title"
        :artist="props.artist"
        :medium="props.medium"
        :origin="props.origin"
        :style="props.style"
        :date="props.date"
        :fullSizeUrl="fullsizeUrl(props.artworkId)"
        :department="props.department"
    >
    </ArtInfoModalContent>
  </base-modal>

</template>
<style scoped>

.art-info{
  background-color: var(--color-secondary);
  background: var(--color-secondary);
  min-height: auto;
  width: 17vw;
  border-radius: 20px;
  box-shadow: 0 2px 8px var(--primary-shadow);
  box-sizing: border-box;
}

.art-info-header {
  padding: 24px;
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  gap: 12px;
}

.art-info-title {
  font-family: var(--font-main),serif;
  font-size: clamp(1rem, 1.5vw, 1.4rem);
  font-weight: 500;
  margin: 0 0 6px 0;
  color: var(--text-primary);
}

.art-info-subtitle {
  font-family: var(--font-secondary),sans-serif;
  font-size: clamp(0.8rem, 1.1vw, 1rem);
  margin: 0;
  color: var(--text-tertiary);
}

.art-info-details {
  border-top: 1px solid var(--text-art-details);
}

.image-panel {
  position: relative;
  box-shadow: 0 18px 38px var(--secondary-shadow);
  border-radius: 0 0 20px 20px;
  display: flex;
  align-items: center;
  justify-content: center;
  box-sizing: border-box;
  overflow: hidden;
}

.artwork-img{
  object-fit: contain;
  position: relative;
  max-height: 300px;
  width: 100%;
  border-radius: 0 0 20px 20px;
  display: block;
}

.artwork-blur{
  filter: blur(36px) saturate(1.15);
  transform: scale(1.15);
  opacity: 0.55;
  object-fit: cover;
  position: absolute;
  max-height: 300px;
  width: 100%;
  border-radius: 0 0 20px 20px;
  display: block;
}

.art-info-expand {
  background: none;
  border: none;
  cursor: pointer;
  padding: 0;
  flex-shrink: 0;
  transition: opacity 180ms ease;
  opacity: 0.6;
}

.art-info-expand:hover {
  opacity: 1;
}

.expand-icon{
  color: var(--icon-primary);
}

</style>

<script setup>
import {computed} from "vue";
import {ART_SOURCES} from "@/assets/composables/ArtSources.js";

const props = defineProps({
  source: String,
})

const museum = computed(() => ART_SOURCES[props.source]);
</script>

<template>
  <p v-if="museum" class="source-credit">
    Kunstwerken:
    <a :href="museum.url" target="_blank" rel="noopener noreferrer">{{ museum.name }}</a>
  </p>
</template>

<style scoped>
.source-credit {
  margin: 0;
  font-family: var(--font-secondary), sans-serif;
  font-size: 0.75rem;
  color: var(--text-tertiary);
  text-align: center;
}

.source-credit a {
  color: inherit;
}
</style>

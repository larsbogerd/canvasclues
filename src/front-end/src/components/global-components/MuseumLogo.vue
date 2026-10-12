<script setup>
import {computed} from "vue";
import {ART_SOURCES} from "@/assets/composables/ArtSources.js";

// Draws a museum logo in the current text colour. The logo files differ in colour (the Met's
// is red) but all have transparent backgrounds, so using them as a mask over currentColor
// makes them a matching set that can be muted or highlighted with plain CSS.
const props = defineProps({
  source: {type: String, required: true},
})

const museum = computed(() => ART_SOURCES[props.source]);
const maskStyle = computed(() => museum.value && {
  maskImage: `url("${museum.value.logo}")`,
  WebkitMaskImage: `url("${museum.value.logo}")`,
});
</script>

<template>
  <span v-if="museum"
        class="museum-logo"
        role="img"
        :aria-label="museum.name"
        :style="maskStyle"></span>
</template>

<style scoped>
.museum-logo {
  display: block;
  width: 100%;
  height: 100%;
  background-color: currentColor;
  mask-size: contain;
  mask-repeat: no-repeat;
  mask-position: center;
  -webkit-mask-size: contain;
  -webkit-mask-repeat: no-repeat;
  -webkit-mask-position: center;
}
</style>

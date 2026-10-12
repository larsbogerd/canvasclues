<script setup>
import {nextTick, ref} from "vue";
import MuseumLogo from "@/components/global-components/MuseumLogo.vue";
import {ART_SOURCES, ART_SOURCE_KEYS} from "@/assets/composables/ArtSources.js";
import {selectedSource} from "@/assets/composables/SelectedSource.js";

const options = ref([]);

// Radio-group keyboard behaviour: one tab stop, arrow keys move the selection.
async function move(step) {
  const index = ART_SOURCE_KEYS.indexOf(selectedSource.value);
  const next = (index + step + ART_SOURCE_KEYS.length) % ART_SOURCE_KEYS.length;
  selectedSource.value = ART_SOURCE_KEYS[next];
  await nextTick();
  options.value[next]?.focus();
}
</script>

<template>
  <div class="museum-picker">
    <div class="options"
         role="radiogroup"
         aria-label="Museum"
         @keydown.right.prevent="move(1)"
         @keydown.down.prevent="move(1)"
         @keydown.left.prevent="move(-1)"
         @keydown.up.prevent="move(-1)">
      <button v-for="key in ART_SOURCE_KEYS"
              :key="key"
              ref="options"
              type="button"
              class="option"
              :class="{ selected: key === selectedSource }"
              role="radio"
              :aria-checked="key === selectedSource"
              :tabindex="key === selectedSource ? 0 : -1"
              :title="ART_SOURCES[key].name"
              @click="selectedSource = key">
        <MuseumLogo :source="key"/>
      </button>
    </div>

    <span class="selected-name" aria-hidden="true">{{ ART_SOURCES[selectedSource].name }}</span>
  </div>
</template>

<style scoped>
.museum-picker {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0.6rem;
  margin-bottom: 1.75rem;
}

.options {
  display: flex;
  gap: 0.75rem;
}

.option {
  width: 104px;
  height: 72px;
  padding: 12px 18px;
  box-sizing: border-box;
  border: 1px solid transparent;
  border-radius: 16px;
  background: transparent;
  color: var(--text-eyebrow);
  opacity: 0.45;
  cursor: pointer;
  transition: opacity 150ms ease, background-color 150ms ease, box-shadow 150ms ease, color 150ms ease;
}

.option:hover {
  opacity: 0.8;
}

.option.selected {
  opacity: 1;
  color: var(--text-primary);
  background-color: var(--color-secondary);
  border-color: var(--button-border);
  box-shadow: 0 8px 20px var(--secondary-shadow);
}

.option:focus-visible {
  outline: 2px solid var(--color-primary);
  outline-offset: 3px;
}

.selected-name {
  font-family: var(--font-secondary), sans-serif;
  font-size: 0.85rem;
  color: var(--text-tertiary);
}

@media (prefers-reduced-motion: reduce) {
  .option { transition: none; }
}
</style>

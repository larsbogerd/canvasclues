<script setup>
import { ref } from 'vue'

const props = defineProps({
  title: { type: String, default: 'Spelregels' },
  defaultOpen: { type: Boolean, default: true },
})

const open = ref(props.defaultOpen)
</script>

<template>
  <div class="game-rules" :class="{ 'is-open': open }">
    <button
        type="button"
        class="game-rules-toggle"
        :aria-expanded="open"
        @click="open = !open"
    >
      <span>{{ title }}</span>
      <span class="chevron" aria-hidden="true">⌄</span>
    </button>
    <Transition name="rules">
      <div v-show="open" class="game-rules-body">
        <slot></slot>
      </div>
    </Transition>
  </div>
</template>

<style scoped>
.game-rules {
  border-radius: 20px;
  background: var(--color-secondary);
  width: 17vw;
  min-width: 280px;
  max-width: 400px;
  box-shadow: 0 2px 8px var(--primary-shadow);
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.game-rules-toggle {
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;
  padding: 14px 20px;
  background: transparent;
  border: none;
  font-family: var(--font-secondary), sans-serif;
  font-size: 1rem;
  font-weight: 600;
  color: var(--text-primary);
  text-align: left;
}

.chevron {
  display: inline-block;
  transition: transform 180ms ease, color 160ms ease;
  font-size: 1.1rem;
  line-height: 1;
}

.game-rules-toggle:hover .chevron,
.game-rules-toggle:focus-visible .chevron {
  color: var(--color-primary);
}

.game-rules.is-open .chevron {
  transform: rotate(180deg);
}

.game-rules-body {
  padding: 0 20px 16px;
  max-height: 50vh;
  overflow-y: auto;
}

.rules-enter-active,
.rules-leave-active {
  transition: opacity 180ms ease;
}
.rules-enter-from,
.rules-leave-to {
  opacity: 0;
}
</style>

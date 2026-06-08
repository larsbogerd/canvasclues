<script setup>

const props = defineProps({
  eyeBrow: String,
  Phase : String,
  subText : String,
  imgUrl: String,
  altText: String,

  difficulties: {
    type: Array,
    default: () => [],
  },

  modelValue: String,
})

const emit = defineEmits(
    ['button-click', 'update:modelValue']
)

function selectDifficulty(value) {
  emit('update:modelValue', value);
}

</script>

<template>
  <button @click="emit('button-click')"
          class="button">

    <span class="text-block">
      <span class="eyebrow">{{ props.eyeBrow }}</span>
      <span class="h1">{{ props.Phase }}</span>
      <span class="subtext">{{ props.subText }}</span>

      <span v-if="props.difficulties.length" class="difficulty-toggle">
        <span
            v-for="option in props.difficulties"
            :key="option"
            class="difficulty-option"
            :class="{ active: props.modelValue === option }"
            role="button"
            tabindex="0"
            @click.stop="selectDifficulty(option)"
            @keydown.enter.stop.prevent="selectDifficulty(option)"
            @keydown.space.stop.prevent="selectDifficulty(option)">
          {{ option }}
        </span>
      </span>
    </span>
    <span class="icon-wrap">
      <img :src="props.imgUrl" :alt="props.altText" />
    </span>
  </button>
</template>

<style scoped>
.text-block {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  justify-content: center;
  gap: 0.75rem;
  width: min(60%, 360px);
  text-align: left;

}

.eyebrow {
  font-size: 0.82rem;
  letter-spacing: 0.18em;
  text-transform: uppercase;
  font-family: var(--font-secondary),sans-serif;
  color: var(--text-eyebrow);
  font-weight: 700;
}

.h1{
  font-size: 3rem;
  font-weight: 500;
  font-family: var(--font-main),serif;
  line-height: 0.95;
  color: var(--text-primary);
}

.subtext {
  font-size: 1.05rem;
  font-family: var(--font-secondary),sans-serif;
  line-height: 1.45;
  max-width: 32ch;
  color:var(--text-secondary);
}

.difficulty-toggle {
  display: inline-flex;
  gap: 0.5rem;
  margin-top: 0.5rem;
  flex-wrap: wrap;
}

.difficulty-option {
  font-family: var(--font-secondary),sans-serif;
  font-size: 0.85rem;
  font-weight: 600;
  padding: 0.4rem 0.9rem;
  border-radius: 999px;
  border: 1px solid var(--button-border);
  background-color: var(--color-secondary);
  color: var(--text-secondary);
  cursor: pointer;
  user-select: none;
  text-transform: capitalize;
  transition: border-color 160ms ease, background-color 160ms ease, color 160ms ease;
}

.difficulty-option:hover,
.difficulty-option:focus-visible {
  border-color: var(--color-primary);
  outline: none;
}

.difficulty-option.active {
  background-color: var(--color-primary);
  border-color: var(--color-primary);
  color: var(--color-bg-base);
}

.icon-wrap {
  position: absolute;
  right: 2rem;
  top: 50%;
  transform: translateY(-50%);
  width: 42%;
  height: 78%;
  display: flex;
  align-items: center;
  justify-content: center;
  pointer-events: none;
}

img{
  max-height: 100%;
  max-width: 100%;
  opacity: 0.22;
  object-fit: contain;
}

.button {
  font-family: inherit;
  color: inherit;
  outline: none;
  position: relative;
  display: flex;
  align-items: stretch;
  justify-content: space-between;
  overflow: hidden;
  background: var(--button-gradient);
  border: 2px solid var(--button-border);
  border-radius: 28px;
  padding: 2rem 2.25rem;
  margin: 10px;
  width: min(760px, 42vw);
  min-height: 310px;
  cursor: pointer;
  box-shadow: 0 14px 28px var(--secondary-shadow);
  transition: border-color 180ms ease, box-shadow 180ms ease;
}

.button:hover,
.button:focus-visible {
  border-color: var(--color-primary);
  box-shadow: 0 10px 24px color-mix(in srgb, var(--color-primary) 22%, transparent);
}

@media (max-width: 1100px) {
  .button {
    width: min(760px, 88vw);
  }
}

@media (max-width: 760px) {
  .button {
    min-height: 260px;
    padding: 1.5rem;
  }

  .text-block {
    width: 66%;
  }

  .h1 {
    font-size: 2.35rem;
  }

  .subtext {
    font-size: 0.98rem;
  }
}

</style>

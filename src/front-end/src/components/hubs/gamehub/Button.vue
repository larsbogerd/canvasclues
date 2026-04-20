<script setup>

const props = defineProps({
  eyeBrow: String,
  Phase : String,
  subText : String,
  imgUrl: String,
  altText: String,
})

const emit = defineEmits(
    ['button-click']
)

</script>

<template>
  <button @click="emit('button-click')" class="button">
    <span class="text-block">
      <span class="eyebrow">{{ props.eyeBrow }}</span>
      <span class="h1">{{ props.Phase }}</span>
      <span class="subtext">{{ props.subText }}</span>
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
  font-family: var(--font-secondary);
  color: #8b7f70;
  font-weight: 700;
}

.h1{
  font-size: 3rem;
  font-weight: 500;
  font-family: var(--font-main);
  line-height: 0.95;
}

.subtext {
  font-size: 1.05rem;
  font-family: var(--font-secondary);
  line-height: 1.45;
  max-width: 32ch;
  color: #2f2b27;
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
  background: linear-gradient(180deg, rgba(255, 255, 255, 0.98), rgba(249, 245, 239, 0.95));
  border: 2px solid #e2d8c8;
  border-radius: 28px;
  padding: 2rem 2.25rem;
  margin: 10px;
  width: min(760px, 42vw);
  min-height: 310px;
  cursor: pointer;
  box-shadow: 0 14px 28px rgba(124, 97, 62, 0.12);
  transition: border-color 180ms ease, box-shadow 180ms ease;
}

.button:hover,
.button:focus-visible {
  border-color: var(--primary-color);
  box-shadow: 0 10px 24px color-mix(in srgb, var(--primary-color) 22%, transparent);
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

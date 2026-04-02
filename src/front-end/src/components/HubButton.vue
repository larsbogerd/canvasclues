<script setup>

const props = defineProps({
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
  <button @click="emit('button-click')" class="button container">
      <span class="text-block">
        <span class="h1">{{ props.Phase }}</span>
        <span class="subtext">{{ props.subText }}</span>
      </span>
    <img :src="props.imgUrl" :alt="props.altText" />
  </button>
</template>

<style scoped>

.container{
  display: grid;
  place-items: center;
}

.text-block {
  grid-area: 1 / 1;
  text-align: center;
  text-wrap: wrap;
  width: 95%;
  z-index: 2;

  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0.5rem;
}

.h1{
  font-size: 1.5rem;
  font-weight: 500;
  font-family: var(--font-main),serif;
}

.subtext {
  font-size: 1rem;
  font-family: var(--font-secondary),sans-serif;
}

img{
  grid-area: 1 / 1;
  max-height: 100%;
  max-width: 50%;
  opacity: 25%;
  z-index: 1;
  object-fit: contain;
}

.button {
  background-color: inherit;
  font-family: inherit;
  color: inherit;
  outline: none;

  border: black 2px solid;
  border-radius: 8px;
  padding: 10px 50px;
  margin: 10px;
  width: 400px;
  cursor: pointer;
  box-shadow: var(--primary-shadow) 4px 4px 4px;
}
</style>
<script setup>
import {ref} from "vue";

const clicked = ref(false);

let props = defineProps({
  id: String,
  imgUrl: String,
  altText: String,
  color: String,
})

const emit = defineEmits(['card-clicked'])
</script>

<template>
  <div v-if="color==='right'" class="card right" @click="clicked = !clicked; emit('card-clicked', props.id, clicked)"
       :class="{ active: clicked }">
    <img :src="props.imgUrl" :alt="props.altText" draggable="false"/>
  </div>

  <div v-else-if="color==='wrong'" class="card wrong"
       @click="clicked = !clicked; emit('card-clicked', props.id, clicked)" :class="{ active: clicked }">
    <img :src="props.imgUrl" :alt="props.altText" draggable="false"/>
  </div>

  <div v-else class="card" @click="clicked = !clicked; emit('card-clicked', props.id, clicked)"
       :class="{ active: clicked }">
    <img :src="props.imgUrl" :alt="props.altText" draggable="false"/>
  </div>
</template>

<style scoped>

.right {
  border: 10px solid var(--primary-good) !important;

}

.wrong {
  border: 10px solid var(--primary-wrong) !important;
}

.card {
  width: 200px;
  height: 200px;
  box-sizing: border-box;
  overflow: hidden;
  border-radius: 20px;
  user-select: none;
}

.active {
  border: 10px solid var(--primary-color);
  box-shadow: 7px 6px 28px 1px rgba(0, 0, 0, 0.24);
}

img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  display: block;
}

</style>
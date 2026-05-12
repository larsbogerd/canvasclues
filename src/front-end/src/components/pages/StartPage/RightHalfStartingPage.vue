<script setup>
import {ref} from "vue";
import art from '@/assets/images/mainartpiece.png'

const artImage = ref(art)
</script>

<template>
  <div class="right">
    <img :src="artImage"
         alt="Art piece" />
  </div>
</template>

<style scoped>
.right {
  flex: 1;
  overflow: hidden;
}

.right img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
</style>
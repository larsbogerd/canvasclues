<script setup>
const props = defineProps({
  imgUrl: String,
  altText: String,
})
</script>

<template>
<div class="card">
  <img :src="props.imgUrl" :alt="props.altText" />
</div>
</template>

<style scoped>

img{
  width: 200px;
  height: 200px;
  display: block;
  border-radius: 20px;
}

</style>
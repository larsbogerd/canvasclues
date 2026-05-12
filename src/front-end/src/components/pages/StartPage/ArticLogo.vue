<template>
  <div class="logo">
    <img :src="logo"
         alt="Canvas Clues Logo" />
  </div>
</template>

<script>
import logoImage from '@/assets/images/logo-artic.png'

export default {
  name: 'Logo',
  props: {
    size: {
      type: Number,
      default: 80
    }
  },
  computed: {
    logo() {
      return logoImage
    }
  }
}
</script>

<style scoped>
.logo img {
  display: block;
}
</style>
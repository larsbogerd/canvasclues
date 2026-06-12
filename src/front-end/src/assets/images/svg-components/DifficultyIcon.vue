<script setup>
import {computed} from "vue";

const props = defineProps({
  level: {
    type: Number,
    required: true,
  },
})

const STAR_POINTS =
    '0,-9 2.12,-2.91 8.56,-2.78 3.42,1.11 5.29,7.28 0,3.6 -5.29,7.28 -3.42,1.11 -8.56,-2.78 -2.12,-2.91'
const SPACING = 23
const positions = computed(() =>
    Array.from({ length: props.level }, (_, i) => 11 + i * SPACING))
const width = computed(() => props.level * SPACING)
</script>

<template>
  <svg xmlns="http://www.w3.org/2000/svg" :viewBox="`0 0 ${width} 22`" :width="width" height="22"
       role="img" :aria-label="`moeilijkheid ${level} van 3`">
    <defs>
      <linearGradient id="difficulty-gold" x1="0" y1="0" x2="0" y2="1">
        <stop offset="0" stop-color="#FFD93B" />
        <stop offset="1" stop-color="#F5A623" />
      </linearGradient>
    </defs>
    <g stroke="#E8950C" stroke-width="0.75" stroke-linejoin="round">
      <polygon v-for="(x, i) in positions" :key="i"
               :points="STAR_POINTS" :transform="`translate(${x}, 11)`"
               fill="url(#difficulty-gold)" />
    </g>
  </svg>
</template>

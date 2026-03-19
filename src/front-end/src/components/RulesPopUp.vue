<template>
  <div v-if="modelValue" class="overlay" @click="$emit('update:modelValue', false)">
    <div class="popup" @click.stop>
      <button class="close" @click="$emit('update:modelValue', false)">
        ✕
      </button>

      <slot>
        hier komen de spelregels
      </slot>
    </div>
  </div>
</template>

<script setup>
defineProps({
  modelValue: Boolean
})

defineEmits(['update:modelValue'])
</script>

<style scoped>
.overlay {
  position: fixed;
  inset: 0;
  background: rgba(0,0,0,0.5);

  display: flex;
  align-items: center;
  justify-content: center;
}

.popup {
  width: 600px;
  height: 500px;
  background: white;
  border-radius: 8px;
  padding: 20px;
  position: relative;
}

.close {
  position: absolute;
  top: 10px;
  right: 10px;
}
</style>
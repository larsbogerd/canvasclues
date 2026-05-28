<script setup>
</script>
<template>
  <div class="hint-card">
    <div class="hint-header">
      <slot name="hint-header">
      </slot>
    </div>

    <slot name="hint-body">
    </slot>
  </div>
</template>

<style scoped>

.hint-card {
  width: 17vw;
  min-width: 280px;
  max-width: 400px;
  border-radius: 24px;
  border: 1px solid var(--button-border);
  box-shadow: 0 2px 8px var(--primary-shadow);
  background: var(--color-secondary);
}
.hint-header {
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;
  padding: 20px 16px;
  border-bottom: 2px solid var(--hint-border-bottom);
}


</style>
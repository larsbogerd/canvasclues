<script setup>
const props = defineProps({
  currentPage: { type: Number, required: true },
  totalPages:  { type: Number, required: true },
});

const emit = defineEmits(["update:currentPage"]);

function go(page) {
  if (page < 1 || page > props.totalPages) return;
  emit("update:currentPage", page);
}
</script>

<template>
  <nav class="pagination">
    <button
      class="page-button"
      :disabled="currentPage === 1"
      @click="go(currentPage - 1)"
    >
      &#8592;
    </button>

    <span class="page-info">
      Pagina <strong>{{ currentPage }}</strong> van {{ totalPages }}
    </span>

    <button
      class="page-button"
      :disabled="currentPage === totalPages"
      @click="go(currentPage + 1)"
    >
      &#8594;
    </button>
  </nav>
</template>

<style scoped>
.pagination {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
  padding: 10px 4px;
}

.page-button {
  font-family: var(--font-secondary), sans-serif;
  font-size: 1rem;
  min-width: 40px;
  padding: 8px 16px;
  border: 1px solid #ccc;
  border-radius: 999px;
  background-color: white;
  color: var(--text-color);
  cursor: pointer;
  transition: border-color 180ms ease, background-color 180ms ease, opacity 180ms ease;
}

.page-button:hover:not(:disabled) {
  border-color: var(--primary-color);
  background-color: #faf5ec;
}

.page-button:disabled {
  opacity: 0.35;
  cursor: not-allowed;
}

.page-info {
  font-family: var(--font-secondary), sans-serif;
  font-size: 0.9rem;
  color: var(--text-color);
  opacity: 0.7;
  min-width: 150px;
  text-align: center;
}

.page-info strong {
  font-weight: 600;
  opacity: 1;
  color: var(--text-color);
}
</style>

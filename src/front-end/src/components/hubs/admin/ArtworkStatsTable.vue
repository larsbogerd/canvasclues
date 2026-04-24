<script setup>
import { ref, computed, watch } from "vue";
import router from "@/router.js";
import TablePagination from "@/components/hubs/admin/TablePagination.vue";

const props = defineProps({
  rows: {
    type: Array,
    required: true,
  },
});

const search = ref("");
const currentPage = ref(1);
const pageSize = 20;

function normalize(value) {
  return String(value ?? "").toLowerCase();
}

const filteredRows = computed(() => {
  const q = normalize(search.value).trim();

  if (!q) return props.rows;

  return props.rows.filter((artwork) =>
      normalize(artwork.title).includes(q) ||
      normalize(artwork.artistDisplay).includes(q)
  );
});

const totalPages = computed(() => {
  return Math.max(1, Math.ceil(filteredRows.value.length / pageSize));
});

const paginatedRows = computed(() => {
  const start = (currentPage.value - 1) * pageSize;
  const end = start + pageSize;
  return filteredRows.value.slice(start, end);
});

watch(filteredRows, () => {
  currentPage.value = 1;
});

watch(totalPages, (newTotal) => {
  if (currentPage.value > newTotal) {
    currentPage.value = newTotal;
  }
});


function saveAsPdf() {
  window.print();
}

function formatDate(iso) {
  if (!iso) return "-";

  return new Date(iso).toLocaleString("nl-NL", {
    day: "2-digit",
    month: "2-digit",
    year: "numeric",
    hour: "2-digit",
    minute: "2-digit",
  });
}

function thumbUrl(id) {
  return `https://www.artic.edu/iiif/2/${id}/full/96,/0/default.jpg`;
}

</script>

<template>
  <section class="table-wrapper">
    <div class="table-header">
      <input
          v-model="search"
          type="text"
          placeholder="Filter..."
      />

      <div class="title-group">
        <p class="scrollbox-title">Alle kunstwerken</p>
        <span class="result-count">{{ filteredRows.length }} resultaten</span>
      </div>

      <button class="pdf-button" @click="saveAsPdf">
        Opslaan als PDF
      </button>
    </div>

    <TablePagination
      v-if="totalPages > 1"
      v-model:currentPage="currentPage"
      :total-pages="totalPages"
    />

    <div class="table-shell">
      <table>
        <thead>
        <tr>
          <th class="thumb-col"></th>
          <th>Titel</th>
          <th>Kunstenaar</th>
          <th title="Hoe vaak dit kunstwerk in boards is verschenen">Getoond</th>
          <th title="Hoe vaak dit kunstwerk gekozen is door de spymaster">Gekozen</th>
          <th title="Het percentage van boards waarin dit artwork gekozen is voor de hint">Pick ratio</th>
          <th>Laatst gebruikt</th>
        </tr>
        </thead>

        <tbody>
        <tr v-if="filteredRows.length === 0" class="empty">
          <td colspan="8">Nog geen data</td>
        </tr>

        <tr
            v-for="artwork in paginatedRows"
            :key="artwork.id"
            @click="router.push(`/admin/artworks/${artwork.id}`)"
        >
          <td>
            <img
                class="thumb"
                :src="thumbUrl(artwork.id)"
                :alt="artwork.title"
                loading="lazy"
            />
          </td>
          <td>{{ artwork.title }}</td>
          <td>{{ artwork.artistDisplay }}</td>
          <td>{{ artwork.timesLoaded }}</td>
          <td>{{ artwork.timesSpymasterPick }}</td>
          <td>{{ artwork.pickPercentage != null ? `${artwork.pickPercentage}%` : "-" }}</td>
          <td>{{ formatDate(artwork.lastUsedAt) }}</td>
        </tr>
        </tbody>
      </table>
    </div>

    <TablePagination
      v-if="totalPages > 1"
      v-model:currentPage="currentPage"
      :total-pages="totalPages"
    />
  </section>
</template>

<style scoped>
.table-wrapper {
  background: linear-gradient(180deg, rgba(255, 255, 255, 0.78), rgba(255, 255, 255, 0.62));
  border: 1px solid rgba(219, 210, 195, 0.95);
  border-radius: 28px;
  box-shadow: 0 18px 38px rgba(124, 97, 62, 0.12);
  padding: 18px;
  box-sizing: border-box;
}

.table-header {
  display: grid;
  grid-template-columns: 1fr auto 1fr;
  align-items: center;
  width: 100%;
  padding: 0.4rem 0.4rem 0.9rem;
  gap: 1rem;
  box-sizing: border-box;
}

.table-header input {
  justify-self: start;
}

.table-header .pdf-button {
  justify-self: end;
}

.table-header input,
.pdf-button {
  font-family: var(--font-secondary), sans-serif;
  font-size: 0.9rem;
  padding: 12px 16px;
  border: 1px solid #ccc;
  border-radius: 20px;
  outline: none;
  background-color: white;
  transition: border-color 180ms ease;
}

.title-group {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 2px;
}

.scrollbox-title {
  font-family: var(--font-main), serif;
  font-size: 1.5rem;
  font-weight: 500;
  margin: 0;
}

.result-count {
  font-family: var(--font-secondary), sans-serif;
  font-size: 0.8rem;
  color: var(--text-color);
  opacity: 0.5;
}

.pdf-button {
  cursor: pointer;
  color: var(--text-color);
}

.pdf-button:hover {
  border-color: var(--primary-color);
}

.table-shell {
  width: 100%;
  border-radius: 24px;
  background-color: var(--background-color);
  border: 1px solid #e0d6c6;
  box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.65);
  overflow: hidden;
}

table {
  width: 100%;
  border-collapse: collapse;
  font-family: var(--font-secondary), sans-serif;
}

thead {
  background: rgba(247, 241, 230, 0.6);
}

tbody {
  background: white;
}

th, td {
  text-align: left;
  padding: 12px 16px;
  color: var(--text-color);
  border-bottom: 1px solid #efe6d6;
}

th {
  font-weight: 600;
  font-size: 14px;
  white-space: nowrap;
}

th[title] {
  cursor: help;
  text-decoration: underline dotted;
  text-underline-offset: 3px;
}

tr.empty td {
  text-align: center;
  padding: 40px 16px;
  opacity: 0.5;
  font-style: italic;
}

.thumb-col {
  width: 64px;
}

tbody .thumb {
  width: 48px;
  height: 48px;
  border-radius: 8px;
  object-fit: cover;
  display: block;
  background: #efe6d6;
}

tbody tr:not(.empty) {
  cursor: pointer;
  transition: background-color 120ms ease;
}

tbody tr:not(.empty):hover {
  background-color: #faf5ec;
}


</style>

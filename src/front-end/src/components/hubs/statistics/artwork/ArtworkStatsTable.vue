<script setup>
import {computed, ref, watch} from "vue";
import router from "@/router.js";
import TablePagination from "@/components/hubs/statistics/TablePagination.vue";

const props = defineProps({
  rows: {
    type: Array,
    required: true,
  },
});

const search = ref("");
const currentPage = ref(1);
const pageSize = 20;

const sortKey = ref(null);
const sortDir = ref("asc");

const textSortKeys = new Set(["title", "artistDisplay"]);

function defaultDirFor(key) {
  return textSortKeys.has(key) ? "asc" : "desc";
}

function toggleSort(key) {
  if (sortKey.value === key) {
    const initial = defaultDirFor(key);
    if (sortDir.value === initial) {
      sortDir.value = initial === "asc" ? "desc" : "asc";
    } else {
      sortKey.value = null;
      sortDir.value = "asc";
    }
  } else {
    sortKey.value = key;
    sortDir.value = defaultDirFor(key);
  }
  currentPage.value = 1;
}

function compareValues(a, b) {
  if (a == null && b == null) return 0;
  if (a == null) return 1;
  if (b == null) return -1;

  if (typeof a === "number" && typeof b === "number") {
    return a - b;
  }

  if (sortKey.value === "lastUsedAt" || sortKey.value === "firstUsedAt") {
    return new Date(a) - new Date(b);
  }

  return String(a).localeCompare(String(b), "nl", { sensitivity: "base" });
}

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

const sortedRows = computed(() => {
  if (!sortKey.value) return filteredRows.value;

  const dir = sortDir.value === "asc" ? 1 : -1;
  return [...filteredRows.value].sort((a, b) => {
    const av = a[sortKey.value];
    const bv = b[sortKey.value];
    // nulls always last, regardless of direction
    if (av == null && bv == null) return 0;
    if (av == null) return 1;
    if (bv == null) return -1;
    return compareValues(av, bv) * dir;
  });
});

const totalPages = computed(() => {
  return Math.max(1, Math.ceil(sortedRows.value.length / pageSize));
});

const paginatedRows = computed(() => {
  const start = (currentPage.value - 1) * pageSize;
  const end = start + pageSize;
  return sortedRows.value.slice(start, end);
});

watch(filteredRows, () => {
  currentPage.value = 1;
});

watch(totalPages, (newTotal) => {
  if (currentPage.value > newTotal) {
    currentPage.value = newTotal;
  }
});

function formatPropRows(){
  let propRowsHeader = Array.from(new Set(props.rows.flatMap(Object.keys)));
  let propRowsArray = props.rows.map(Object.values);


  propRowsHeader.shift();
  propRowsArray.forEach((row) => {
    row.shift();
    row[0] = row[0].replace(/[,;]/g,"");
    row[1] = row[1].replace(/\n/g," ");
    row[1] = row[1].replace(/[,;]/g,"");
    row[8] = formatDate(row[8]).replace(/[,;]/g,"");
    row[9] = formatDate(row[9]).replace(/[,;]/g,"");
    return row;
  });

  return [
    propRowsHeader,
    propRowsArray
  ];
}

function createTable(){
  const formattedPropRows = formatPropRows();
  const tableHeaders = formattedPropRows[0].join(",");
  const tableRows = formattedPropRows[1].map(row => Object.values(row).join(",")).join("\n");
  return tableHeaders + "\n"+ tableRows;
}

function exportDataToCSV() {
  const table = createTable();

  const blob = new Blob([table], {type: 'text/csv'})
  const url = window.URL.createObjectURL(blob);
  const link = document.createElement('a')

  link.href = url
  link.download = 'artworkData.csv'
  link.click()

  window.URL.revokeObjectURL(url)
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
          class="text-color"
          v-model="search"
          type="text"
          placeholder="Filter..."
      />

      <div class="title-group">
        <p class="scrollbox-title">Alle kunstwerken</p>
        <span class="result-count">{{ filteredRows.length }} resultaten</span>
      </div>

      <button class="export-button"
              @click="exportDataToCSV()">
        Exporteren als CSV
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
          <th class="sortable" :class="{ active: sortKey === 'title' }" @click="toggleSort('title')">
            Titel<span class="sort-indicator">{{ sortKey === 'title' ? (sortDir === 'asc' ? '▲' : '▼') : '' }}</span>
          </th>
          <th class="sortable" :class="{ active: sortKey === 'artistDisplay' }" @click="toggleSort('artistDisplay')">
            Kunstenaar<span
              class="sort-indicator">{{ sortKey === 'artistDisplay' ? (sortDir === 'asc' ? '▲' : '▼') : '' }}</span>
          </th>
          <th class="sortable" :class="{ active: sortKey === 'timesLoaded' }" @click="toggleSort('timesLoaded')"
              title="Hoe vaak dit kunstwerk in gegenereerde borden voorkomt">
            Voorgekomen<span class="sort-indicator">{{
              sortKey === 'timesLoaded' ? (sortDir === 'asc' ? '▲' : '▼') : ''
            }}</span>
          </th>
          <th class="sortable" :class="{ active: sortKey === 'pickPercentage' }" @click="toggleSort('pickPercentage')"
              title="Percentage van boards waarin dit artwork door de spymaster is gekozen">
            Pick-ratio<span class="sort-indicator">{{
              sortKey === 'pickPercentage' ? (sortDir === 'asc' ? '▲' : '▼') : ''
            }}</span>
          </th>
          <th class="sortable" :class="{ active: sortKey === 'guessPercentage' }" @click="toggleSort('guessPercentage')"
              title="Percentage van de keren dat dit artwork correct geraden is">
            Raadbaarheid<span
              class="sort-indicator">{{ sortKey === 'guessPercentage' ? (sortDir === 'asc' ? '▲' : '▼') : '' }}</span>
          </th>
          <th class="sortable" :class="{ active: sortKey === 'lastUsedAt' }" @click="toggleSort('lastUsedAt')"
              title="Datum waarop dit artwork voor het laatst op een bord verscheen">
            Laatst op bord<span
              class="sort-indicator">{{ sortKey === 'lastUsedAt' ? (sortDir === 'asc' ? '▲' : '▼') : '' }}</span>
          </th>
        </tr>
        </thead>

        <tbody>
        <tr v-if="filteredRows.length === 0" class="empty">
          <td colspan="8">Nog geen data</td>
        </tr>

        <tr
            v-for="artwork in paginatedRows"
            :key="artwork.id"
            @click="router.push(`/statistics/artworks/${artwork.id}`)"
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
          <td>{{ artwork.pickPercentage != null ? `${artwork.pickPercentage}%` : "-" }}</td>
          <td>{{ artwork.guessPercentage != null ? `${artwork.guessPercentage}%` : "-" }}</td>
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
.text-color {
  color: var(--text-primary);
}

.table-wrapper {
  background: var(--scrolbox-background-gradient);
  border: 1px solid var(--scrollbox-border-secondary);
  border-radius: 28px;
  box-shadow: 0 18px 38px var(--secondary-shadow);
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

.table-header input:focus {
  border-color: var(--color-primary);
}

.table-header .export-button {
  justify-self: end;
}

.table-header input,
.export-button {
  font-family: var(--font-secondary), sans-serif;
  font-size: 0.9rem;
  padding: 12px 16px;
  border: 1px solid var(--secondary-border);
  border-radius: 20px;
  outline: none;
  background-color: var(--color-secondary);
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
  color:var(--text-primary);
}

.result-count {
  font-family: var(--font-secondary), sans-serif;
  font-size: 0.8rem;
  color: var(--text-primary);
  opacity: 0.5;
}

.export-button {
  cursor: pointer;
  color: var(--text-primary);
}

.export-button:hover {
  border-color: var(--color-primary);
}

.table-shell {
  width: 100%;
  border-radius: 24px;
  background-color: var(--color-bg-base);
  border: 1px solid var(--scrollbox-border);
  box-shadow: inset 0 1px 0 var(--shadow-scrollbox);
  overflow: hidden;
}

table {
  width: 100%;
  border-collapse: collapse;
  font-family: var(--font-secondary), sans-serif;
}

thead {
  background: var(--table-header-color);
}

tbody {
  background: var(--color-secondary);
}

th, td {
  text-align: left;
  padding: 12px 16px;
  color: var(--text-primary);
  border-bottom: 1px solid var(--hub-border-bottom);
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

th.sortable {
  cursor: pointer;
  user-select: none;
}

th.sortable.active {
  color: var(--color-primary);
}

.sort-indicator {
  font-size: 11px;
  margin-left: 4px;
  display: inline-block;
  min-width: 1em;
  text-align: center;
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
  background: var(--hub-border-bottom);
}

tbody tr:not(.empty) {
  cursor: pointer;
  transition: background-color 120ms ease;
}

tbody tr:not(.empty):hover {
  background-color: var(--button-hover);
}


</style>

<script setup>
import axios from 'axios';
import {ref, watch} from "vue";
import {onMounted} from "vue";
import InputField from "@/components/InputField.vue";
import Grid from "@/components/Grid.vue";
import FaseLabel from "@/components/faseLabel.vue";
let hintInput = ref("");
const cards = ref([]);
const hint = ref(null);
const selectedCards = ref([]);
const prop = defineProps(
    {
    gameId: 0
    }
)

onMounted(() => {
  getHint();
});

watch(() => prop.gameId, (newId) => {
  if (newId) {
    getGrid();
  }
});

async function getHint() {
  try {
    const id = prop.gameId
    const response = await axios.get(`http://localhost:8082/api/v1/hints/${id}`);
    hint.value = response.data;

    console.log("Fetched hint:", hint.value);
  } catch(error) {
    console.log(error.status());
  }
}

function endTurn() {
  console.log(hintInput.value);
}

async function getGrid() {
  try {
    const id = prop.gameId
    console.log(id)
    const response = await axios.get(`http://localhost:8082/api/v1/game/${id}`);
    cards.value = response.data;
    console.log("Fetched hint:", cards.value);
  } catch(error) {
    console.log(error);
  }
}

</script>

<template>
  <div class="screen">
      <grid :cards="cards"/>
    <div style="text-align: center;">
      <p>Selected: {{ selectedCards.length }}</p>
      <div class="info">
        <fase-label fase="Operative"/>
        <input-field readonly="readonly" name="end turn" v-on:submit="endTurn" v-model="hintInput"
                     v-if="hint" :label="'jouw hint is: '+hint.hintContent" :InputDisabled="true" />
      </div>
  </div>
  </div>
</template>

<style scoped>
.screen {
  background-color: green;
  gap: 20px;
  display: grid;
  grid-template-columns: 1fr 1fr;
  justify-items: center;
  align-items: center;
}
.info{
  display: grid;
justify-items: center;
}
</style>

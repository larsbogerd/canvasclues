<script setup>
import axios from 'axios';
import {ref, watch} from "vue";
import {onMounted} from "vue";
import InputField from "@/components/InputField.vue";
import Grid from "@/components/Grid.vue";
let hintInput = ref("");
const cards = ref([]);
const hint = ref(null);
const prop = defineProps(
    {
    gameID: 0
    }
)

onMounted(() => {
  getHint();
});


watch(() => prop.gameID, (newId) => {
  if (newId) {
    getGrid();
  }
});

async function getHint() {
  try {
    const response = await axios.get('http://localhost:8082/api/v1/hints');
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
    const id = prop.gameID
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
    <div class="grid">
      <grid :cards="cards"/>
    </div>
    <input-field readonly="readonly" name="end turn" v-on:submit="endTurn" v-model="hintInput"
                 v-if="hint" :label="'jou hint is: '+hint.hintContent" :InputDisabled="true" />
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
</style>

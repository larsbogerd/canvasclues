<script setup>
import axios from 'axios';
import {onMounted, ref} from "vue";
import InputField from "@/components/InputField.vue";
import Grid from "@/components/Grid.vue";
import FaseLabel from "@/components/faseLabel.vue";
import {useRouter} from 'vue-router'

const router = useRouter();
let hintInput = ref("");
const cards = ref([]);
const hint = ref(null);
const selectedCards = ref([]);


onMounted(() => {
  getHint();
  getGrid();
});

async function getGameId(){
  return 1;
}

async function getHint() {
  try {
    const id = await getGameId()
    console.log(id);
    const response = await axios.get(`http://localhost:8082/api/v1/hints/${id}`);
    hint.value = response.data;

    console.log("Fetched hint:", hint.value);
  } catch(error) {
    console.log(error.status);
  }
}

function endTurn() {
  console.log(hintInput.value);
  router.push('/')
}

async function getGrid() {
  try {
    const id = await getGameId()
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

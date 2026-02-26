<script setup>
import {ref} from 'vue'
import ClickableCards from "@/components/ClickableCards.vue";

const cardArray = ref([
  {id: 1, status: true},
  {id: 2, status: false},
  {id: 3, status: false},
  {id: 4, status: false},
  {id: 5, status: false},
  {id: 6, status: false},
  {id: 7, status: false},
  {id: 8, status: false},
  {id: 9, status: false},
  {id: 10, status: false},
  {id: 11, status: false},
  {id: 12, status: true},
  {id: 13, status: false},
  {id: 14, status: false},
  {id: 15, status: true},
  {id: 16, status: false},
]
)
</script>

<template>
  <div class="game-board">
    <ClickableCards v-for="card in cardArray"
                    :key="card.id"
                    :cardArray="card">
    </ClickableCards>
  </div>
</template>
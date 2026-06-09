import {createRouter, createWebHistory} from 'vue-router'

import StartingPage from "@/components/pages/StartPage/StartingPage.vue";
import GameHub from "@/components/hubs/gamehub/GameHub.vue";
import SpyView from './components/pages/SpymasterPhase.vue'
import OperativeHub from "@/components/hubs/operativehub/OperativeHub.vue";
import OperativeView from './components/pages/OperativePhase.vue'
import StatisticsHub from "@/components/hubs/statistics/StatisticsHub.vue";
import StatisticsArtworks from "@/components/hubs/statistics/artwork/StatisticsArtworks.vue";
import ArtworkStats from "@/components/hubs/statistics/artwork/ArtworkStats.vue";
import StatisticsHints from "@/components/hubs/statistics/hint/StatisticsHints.vue";


const routes = [
    { path: '/', component: StartingPage},
    { path: '/game', component: GameHub },
    { path: '/game/curator', component: SpyView },
    { path: '/game/bezoeker', component: OperativeHub },
    { path: '/game/bezoeker/:gameId', component: OperativeView },
    { path: '/statistics', component: StatisticsHub },
    { path: '/statistics/artworks', component: StatisticsArtworks },
    { path: '/statistics/artworks/:artworkId', component: ArtworkStats },
    { path: '/statistics/hints', component: StatisticsHints}
]

const router = createRouter({
    history: createWebHistory(),
    routes,
})

export default router

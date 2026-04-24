import {createRouter, createWebHistory} from 'vue-router'

import StartingPage from "@/components/pages/StartPage/StartingPage.vue";
import gameHub from "@/components/hubs/gamehub/GameHub.vue";
import SpyView from './components/pages/SpymasterPhase.vue'
import OperativeView from './components/pages/OperativePhase.vue'
import operativeHub from "@/components/hubs/operativehub/OperativeHub.vue";
import AdminHub from "@/components/hubs/admin/AdminHub.vue";
import AdminArtworks from "@/components/hubs/admin/AdminArtworks.vue";
import ArtworkStats from "@/components/hubs/admin/ArtworkStats.vue";

const routes = [
    { path: '/', component: StartingPage},
    { path: '/game', component: gameHub },
    { path: '/game/spymaster', component: SpyView },
    { path: '/game/operative', component: operativeHub },
    { path: '/game/operative/:gameId', component: OperativeView },
    { path: '/admin', component: AdminHub },
    { path: '/admin/artworks', component: AdminArtworks },
    { path: '/admin/artworks/:artworkId', component: ArtworkStats }
]

const router = createRouter({
    history: createWebHistory(),
    routes,
})

export default router
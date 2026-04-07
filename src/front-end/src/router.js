import {createRouter, createWebHistory} from 'vue-router'

import StartingPage from "@/components/pages/StartingPage.vue";
import gameHub from "@/components/hubs/gamehub/GameHub.vue";
import SpyView from './components/pages/SpymasterPhase.vue'
import OperativeView from './components/pages/OperativePhase.vue'
import operativeHub from "@/components/hubs/operativehub/OperativeHub.vue";

const routes = [
    { path: '/', component: StartingPage},
    { path: '/game', component: gameHub },
    { path: '/game/spymaster', component: SpyView },
    { path: '/game/operative', component: operativeHub },
    { path: '/game/operative/:gameId', component: OperativeView },
]

const router = createRouter({
    history: createWebHistory(),
    routes,
})

export default router
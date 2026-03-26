import {createRouter, createWebHistory} from 'vue-router'

import StartingPage from "@/components/pages/StartingPage.vue";
import gameHub from "@/components/pages/GameHub.vue";
import SpyView from './components/pages/SpymasterPhase.vue'
import OperativeView from './components/pages/OperativePhase.vue'

const routes = [
    { path: '/', component: StartingPage},
    { path: '/gamehub', component: gameHub },
    { path: '/spy', component: SpyView },
    { path: '/operative', component: OperativeView },
]

const router = createRouter({
    history: createWebHistory(),
    routes,
})

export default router
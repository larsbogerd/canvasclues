import {createRouter, createWebHistory} from 'vue-router'

import SpyView from './components/pages/SpymasterPhase.vue'
import OperativeView from './components/pages/OperativePhase.vue'
import StartingPage from "@/components/pages/StartingPage.vue";

const routes = [
    { path: '/', component: StartingPage},
    { path: '/spy', component: SpyView },
    { path: '/operative', component: OperativeView },
]

const router = createRouter({
    history: createWebHistory(),
    routes,
})

export default router
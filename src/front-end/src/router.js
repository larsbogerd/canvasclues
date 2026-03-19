import {createRouter, createWebHashHistory} from 'vue-router'

import SpyView from './components/pages/SpymasterPhase.vue'
import OperativeView from './components/pages/OperativePhase.vue'
import StartingPage from "@/components/pages/StartingPage.vue";

const routes = [
    { path: '/', component: StartingPage},
    { path: '/Spy', component: SpyView },
    { path: '/Operative', component: OperativeView },
]

const router = createRouter({
    history: createWebHashHistory(),
    routes,
})

export default router
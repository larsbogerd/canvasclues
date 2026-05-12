import { createApp } from 'vue'
import App from './App.vue'
import './assets/ColorStyles.css'
import './assets/FontStyles.css'
import './assets/modal.css'
import router from './router'

createApp(App).use(router).mount('#app')

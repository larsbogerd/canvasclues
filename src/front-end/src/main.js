import { createApp } from 'vue'
import App from './App.vue'
import './assets/styles.css'
import './assets/fonts.css'
import './assets/modal.css'
import router from './router'

createApp(App).use(router).mount('#app')

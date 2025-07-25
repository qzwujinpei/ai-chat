import { createApp } from 'vue'
import { createPinia } from 'pinia'
import pinia from './stores'
import App from './App.vue'
import router from './router'
import './styles/index.scss'
import './permission'
const app = createApp(App)
app.use(createPinia())
app.use(router)
app.use(pinia)
app.mount('#app')

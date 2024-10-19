import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'

import { library } from '@fortawesome/fontawesome-svg-core'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'

import {faUserSecret, faHouse, faFile, faUserTie} from '@fortawesome/free-solid-svg-icons'

import App from './App.vue'
import router from './router'
import './assets/main.css'

library.add(
    faUserSecret,
    faHouse,
    faFile,
    faUserTie
)

const app = createApp(App)
    .component('font-awesome-icon', FontAwesomeIcon)

app.use(createPinia())
app.use(router)

app.mount('#app')

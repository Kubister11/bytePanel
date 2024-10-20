import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import Toast, {POSITION} from "vue-toastification";

import { library } from '@fortawesome/fontawesome-svg-core'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'

import {faUserTie, faServer, faPaperPlane} from '@fortawesome/free-solid-svg-icons'

import App from './App.vue'
import router from './router'
import './assets/main.css'
import "vue-toastification/dist/index.css";

library.add(
    faUserTie,
    faServer,
    faPaperPlane
);

const app = createApp(App)
    .component('font-awesome-icon', FontAwesomeIcon);

app.use(createPinia());
app.use(Toast, {
    position: POSITION.BOTTOM_RIGHT
});
app.use(router);

app.mount('#app');

import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import Toast, {POSITION} from "vue-toastification";

import { library } from '@fortawesome/fontawesome-svg-core'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'

import {
    faUserTie,
    faServer,
    faPaperPlane,
    faPowerOff,
    faRotate, faMicrochip, faMemory, faFloppyDisk, faClock, faTerminal, faRetweet, faGear, faUser
} from '@fortawesome/free-solid-svg-icons'

import {
    faCircleStop, faFile
} from '@fortawesome/free-regular-svg-icons'

import App from './App.vue'
import router from './router'
import './assets/main.css'
import "vue-toastification/dist/index.css";

import AOS from 'aos';
import 'aos/dist/aos.css';

AOS.init();

library.add(
    faUserTie,
    faServer,
    faPaperPlane,
    faPowerOff,
    faCircleStop,
    faRotate,
    faClock,
    faMicrochip,
    faMemory,
    faFloppyDisk,
    faTerminal,
    faFile,
    faRetweet,
    faGear,
    faUser
);

const app = createApp(App)
    .component('font-awesome-icon', FontAwesomeIcon);

app.use(createPinia());
app.use(Toast, {
    position: POSITION.BOTTOM_RIGHT
});
app.use(router);

app.mount('#app');

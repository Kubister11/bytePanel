import { createRouter, createWebHistory } from 'vue-router'
import ConsoleView from "@/views/ConsoleView.vue";
import ServersView from "@/views/ServersView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/console',
      name: 'console',
      component: ConsoleView
    },
    {
      path: '/servers',
      name: 'servers',
      component: ServersView
    }
  ]
})

export default router

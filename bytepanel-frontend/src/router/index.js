import { createRouter, createWebHistory } from 'vue-router'
import ConsoleView from "@/views/ConsoleView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'console',
      component: ConsoleView
    },
    {
      path: '/about',
      component: () => import('../views/AboutView.vue')
    }
  ]
})

export default router

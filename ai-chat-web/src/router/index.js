import { createRouter, createWebHistory } from 'vue-router'
import eventEmitter from '@/utils/EventEmitter'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: () => import('@/layout/index.vue'),
      redirect: '/ai-chat',
      children: [
        {
          path: '/ai-chat/:chatId?',
          name: 'ai-chat',
          props: true,
          component: () => import('@/views/AiChat/index.vue'),
        },
      ],
    },
    // 登录
    {
      path: '/login',
      name: 'login',
      component: () => import('@/views/login/index.vue'),
    },
    // 注册
    {
      path: '/register',
      name: 'register',
      component: () => import('@/views/register/index.vue'),
    },
    {
      path: '/welcome',
      name: 'welcome',
      component: () => import('@/views/FunctionList/index.vue'),
    },
  ],
})
eventEmitter.on('ROUTE_CHANGE', (to) => {
  router.push(to)
})
export default router

import NProgress from 'nprogress'
import { useUserStore } from '@/stores/index.js'
import router from './router'

const whiteList = ['/login', '/register']

router.beforeEach(async (to) => {
  NProgress.start()
  const token = useUserStore().token
  if (token) {
    if (to.path === '/login' || to.path === '/register') {
      return { path: '/' }
    } else {
      return true
    }
  } else {
    if (whiteList.includes(to.path)) {
      // 如果没有登录但是试图访问白名单中的路径，则直接通过
      return true
    } else {
      // 如果没有登录并且试图访问非白名单中的路径，则重定向至登录页面
      return { path: '/login', query: { redirect: to.path } }
    }
  }
})

router.afterEach(() => {
  NProgress.done()
})

export default router

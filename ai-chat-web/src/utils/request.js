import axios from 'axios'
import { useUserStore } from '@/stores/modules/user-store.js'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'

const service = axios.create({
  baseURL: '/api',
  timeout: 100000,
  withCredentials: true,
})

// 请求拦截器
service.interceptors.request.use(
  (config) => {
    // 显示加载提示
    /*    loadingInstance = showLoadingToast({
          message: '加载中...',
          forbidClick: true,
          duration: 0,
        })*/
    const token = useUserStore().token
    if (token) {
      config.headers.Authorization = token + ''
    }
    return config
  },
  (error) => {
    return Promise.reject(error)
  },
)

// 响应拦截器
service.interceptors.response.use(
  (response) => {
    // 关闭加载提示
    /*  if (loadingInstance && typeof loadingInstance.close === 'function') {
        loadingInstance.close()
      }*/
    return response.data
  },
  async (error) => {
    // 处理网络错误
    let msg
    let status = error.response?.status
    if (status === 401) {
      const router = useRouter()
      useUserStore().clearToken()
      router.push('/login')
    }

    switch (status) {
      case 401:
        msg = 'token过期'
        break
      case 403:
        msg = '无权访问'
        break
      case 404:
        msg = '请求地址错误'
        break
      case 500:
        msg = '服务器出现问题'
        break
      default:
        msg = '网络异常'
    }
    ElMessage.error(msg)
    return Promise.reject(error || 'error')
  },
)

export default service

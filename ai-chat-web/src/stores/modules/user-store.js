export const useUserStore = defineStore(
  'userStore',
  () => {
    const userInfo = ref()
    const token = ref('')
    const setToken = (value) => {
      token.value = value
    }
    const clearToken = () => {
      token.value = ''
    }
    return { userInfo, token, setToken, clearToken }
  },
  {
    persist: true,
  },
)

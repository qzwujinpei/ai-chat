import { v4 as uuidv4 } from 'uuid'
import {
  getAiHistoryService,
  createNewChatService,
  getAiMemoryService,
  updateChatTitleService,
  deleteAiChatService,
} from '@/api/ai'
import { useAiStore } from '@/stores'
import EventEmitter from '@/utils/EventEmitter'
import { useRoute } from 'vue-router'
export const useHistoryStore = defineStore('historyStore', () => {
  // Store dependencies
  const aiStore = useAiStore()
  const { chatId, currentMessages, isStreaming } = storeToRefs(aiStore)
  // Reactive state
  const historyChat = ref([])
  const isLoading = ref(false)
  const route = useRoute()
  const scrollbarRef = ref(null)
  // Computed properties
  const activeTitle = computed(() => {
    const item = historyChat.value.find((item) => item.conversationId === chatId.value)
    return item ? item.messageTitle : ''
  })

  const groupedItems = computed(() => {
    const now = new Date()
    const today = new Date(now.getFullYear(), now.getMonth(), now.getDate())
    const oneWeekAgo = new Date(now.getTime() - 7 * 24 * 60 * 60 * 1000)
    const thirtyDaysAgo = new Date(now.getTime() - 30 * 24 * 60 * 60 * 1000)
    const halfYearAgo = new Date(now.getTime() - 180 * 24 * 60 * 60 * 1000)

    const groups = {
      今天: [],
      一周内: [],
      '30天内': [],
      半年内: [],
      更久: [],
    }

    historyChat.value.forEach((item) => {
      const lastActiveTime = new Date(item.lastActiveTime)
      if (lastActiveTime >= today) {
        groups['今天'].push(item)
      } else if (lastActiveTime >= oneWeekAgo) {
        groups['一周内'].push(item)
      } else if (lastActiveTime >= thirtyDaysAgo) {
        groups['30天内'].push(item)
      } else if (lastActiveTime >= halfYearAgo) {
        groups['半年内'].push(item)
      } else {
        groups['更久'].push(item)
      }
    })

    // 过滤掉没有数据的分组
    return Object.entries(groups)
      .filter(([_, items]) => items.length > 0)
      .map(([date, items]) => ({
        lastActiveTime: date,
        items: items.sort((a, b) => new Date(b.lastActiveTime) - new Date(a.lastActiveTime)),
      }))
  })

  // Methods
  const hasNewChat = () => {
    return historyChat.value.some((item) => item.messageTitle === '新对话')
  }

  const loadHistory = async () => {
    try {
      isLoading.value = true
      const { data } = await getAiHistoryService()
      historyChat.value = data.records
    } catch (error) {
      ElMessage.error('历史记录加载失败')
      console.error('Load history error:', error)
    } finally {
      isLoading.value = false
    }
  }

  const switchChat = async (conversationId) => {
    if (conversationId === chatId.value) return
    if (isStreaming.value) {
      ElMessage.warning('请等待当前对话完成')
      return
    }
    try {
      const { data } = await getAiMemoryService(conversationId)
      currentMessages.value = data
      chatId.value = conversationId
      EventEmitter.emit('ROUTE_CHANGE', `/ai-chat/${conversationId}`)
    } catch (error) {
      ElMessage.error('切换对话失败')
      console.error('Switch chat error:', error)
    }
  }

  const createNewChat = async () => {
    if (hasNewChat()) {
      ElMessage.warning('已存在新对话，请继续使用当前对话')
      return
    }

    try {
      const newId = uuidv4()
      await createNewChatService(newId)

      const newChat = {
        conversationId: newId,
        lastActiveTime: new Date().toISOString(),
        messageTitle: '新对话',
        uuid: newId,
      }

      historyChat.value.unshift(newChat)
      chatId.value = newId
      currentMessages.value = []
      EventEmitter.emit('ROUTE_CHANGE', `/ai-chat/${newId}`)
    } catch (error) {
      ElMessage.error('创建新对话失败')
      console.error('Create chat error:', error)
    }
  }
  // 新增方法：更新对话标题
  const updateChatTitle = async (conversationId, newTitle) => {
    try {
      // 调用API更新后端
      await updateChatTitleService(conversationId, newTitle)

      // 更新本地数据
      const chatIndex = historyChat.value.findIndex(
        (item) => item.conversationId === conversationId,
      )
      if (chatIndex !== -1) {
        historyChat.value[chatIndex].messageTitle = newTitle
      }
    } catch (error) {
      ElMessage.error('更新标题失败')
      console.error('Update title error:', error)
    }
  }
  const deleteChat = async (conversationId) => {
    await ElMessageBox.confirm('确定删除该对话吗？', '删除对话', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    })
    historyChat.value = historyChat.value.filter((item) => item.conversationId !== conversationId)
    await deleteAiChatService(conversationId)
    EventEmitter.emit('ROUTE_CHANGE', `/ai-chat`)
  }
  // 新增方法：将对话移动到历史记录的第一位
  const moveChatToTop = (conversationId) => {
    const chatIndex = historyChat.value.findIndex((item) => item.conversationId === conversationId)
    if (chatIndex !== -1 && chatIndex !== 0) {
      const currentChat = historyChat.value.splice(chatIndex, 1)[0]
      // 更新 lastActiveTime 为当前时间
      currentChat.lastActiveTime = new Date().toISOString()
      historyChat.value.unshift(currentChat)
      // 滚动到顶部
      if (scrollbarRef.value) {
        scrollbarRef.value.setScrollTop(0)
      }
    }
  }

  // Initialization
  const initialize = async () => {
    await loadHistory()

    if (route.params.chatId) {
      const target = historyChat.value.find((item) => item.conversationId === route.params.chatId)

      if (target) {
        await switchChat(route.params.chatId)
      } else {
        ElMessage.warning('指定对话不存在')
        router.replace('/ai')
      }
    }
  }

  onMounted(initialize)

  return {
    historyChat,
    groupedItems,
    activeTitle,
    chatId,
    isLoading,
    hasNewChat,
    loadHistory,
    switchChat,
    createNewChat,
    updateChatTitle,
    deleteChat,
    moveChatToTop,
    scrollbarRef,
  }
})

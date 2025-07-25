// ai-store.js
import { sendAiMessage, saveAiChatService } from '@/api/ai.js'
import { useHistoryStore } from '@/stores'
import { ElLoading } from 'element-plus'
// 定义消息角色常量
const MessageRole = {
  USER: 'user',
  ASSISTANT: 'assistant',
}

// 定义消息位置常量
const Placement = {
  END: 'end',
  START: 'start',
}

/**
 * 定义AI聊天存储库
 * @returns {Object} 返回包含状态、计算属性和方法的对象
 */
export const useAiStore = defineStore('aiStore', () => {
  // State
  let loadingInstance
  const chatId = ref('') // 当前聊天ID
  const userInput = ref('') // 用户输入内容
  const senderLoading = ref(false) // 发送消息时的加载状态
  const currentMessages = ref([]) // 当前消息列表
  const selectedFiles = ref([])
  const isStreaming = ref(false) // 是否正在流式传输
  const fileInput = ref(null) // 文件输入引用
  const bubbleListRef = ref(null) // 消息列表引用
  const deep = ref(false) // 是否为深度聊天模式
  // Getters
  /**
   * 计算属性：格式化后的消息列表
   * @returns {Array} 返回包含消息对象和附加属性（如头像和位置）的数组
   */
  const messageList = computed(() =>
    currentMessages.value
      ? currentMessages.value.map((message) => ({
          ...message,
          placement: message.role === MessageRole.USER ? Placement.END : Placement.START,
          isMarkdown: true,
          maxWidth: message.role === MessageRole.USER ? '500px' : '900px',
          variant: 'filled',
          avatar:
            message.role === MessageRole.ASSISTANT
              ? 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png'
              : 'https://avatars.githubusercontent.com/u/76239030?v=4',
        }))
      : [],
  )

  // Actions
  /**
   * 创建消息对象
   * @param {string} role - 消息角色（用户或助手）
   * @param {string} content - 消息内容
   * @returns {Object} 返回包含角色、内容、发送时间和位置的消息对象
   */
  const createMessage = (role, content) => ({
    role,
    content,
    sentTime: new Date(),
    placement: role === MessageRole.USER ? Placement.END : Placement.START,
    conversationId: chatId.value,
  })

  /**
   * 处理流式响应
   * @param {ReadableStreamDefaultReader} reader - 流式响应的读取器
   * @param {Object} assistantMessage - 助手消息对象
   * @returns {Object} 返回更新后的助手消息对象
   */
  const handleStreamResponse = async (reader, assistantMessage) => {
    const decoder = new TextDecoder('utf-8')
    let accumulatedContent = ''
    let updatedMessage = assistantMessage

    try {
      while (true) {
        const { value, done } = await reader.read()
        if (done) break
        // 关闭 loadingInstance（如果存在）
        if (loadingInstance) {
          loadingInstance.close()
          loadingInstance = null
        }
        accumulatedContent += decoder.decode(value)
        await nextTick(() => {
          updatedMessage = {
            ...updatedMessage,
            content: accumulatedContent,
            isMarkdown: true,
          }
          const lastIndex = currentMessages.value.length - 1
          currentMessages.value.splice(lastIndex, 1, updatedMessage)
        })
      }
      return updatedMessage
    } catch (error) {
      console.error('Stream reading error:', error)
      throw error
    }
  }

  /**
   * 重置状态
   */
  const resetState = () => {
    selectedFiles.value = []
    fileInput.value = null
    senderLoading.value = false
    isStreaming.value = false
  }

  /**
   * 处理消息提交
   */
  const handleSubmit = async () => {
    if (!userInput.value.trim() && !selectedFiles.value.length) return
    if (senderLoading.value) return
    // 更新历史记录中的对话位置
    const historyStore = useHistoryStore()
    historyStore.moveChatToTop(chatId.value)
    try {
      ElMessage.info('发送中...')
      senderLoading.value = true

      // 如果是深度思考模式，显示加载提示
      if (deep.value) {
        loadingInstance = ElLoading.service({
          lock: true,
          text: 'AI正在深度思考中，请稍候...',
          background: 'rgba(0, 0, 0, 0.7)',
        })
      }
      // 创建用户消息并添加到消息列表
      const userMessage = createMessage(MessageRole.USER, userInput.value.trim())
      currentMessages.value.push(userMessage)
      userInput.value = ''

      // 准备请求数据，包括用户输入和选择的文件
      const formData = new FormData()
      formData.append('prompt', userMessage.content)
      selectedFiles.value.forEach((file) => formData.append('files', file))
      // 创建助手消息占位并添加到消息列表
      const assistantMessage = createMessage(MessageRole.ASSISTANT, '')
      currentMessages.value.push(assistantMessage)
      isStreaming.value = true
      await nextTick(() => {
        if (bubbleListRef.value && typeof bubbleListRef.value.scrollToBottom === 'function') {
          bubbleListRef.value.scrollToBottom()
        }
      })
      // 发送请求并处理流式响应
      const reader = await sendAiMessage(formData, chatId.value, deep.value)
      const updatedMessage = await handleStreamResponse(reader, assistantMessage)

      // 保存用户消息和更新后的助手消息
      await saveAiChatService(userMessage)
      await saveAiChatService(updatedMessage)
      await updateTitle()
    } catch (error) {
      console.error('Submission error:', error)
      ElMessage.error('消息发送失败，请稍后重试')
      currentMessages.value.pop() // 移除助手消息占位
    } finally {
      resetState()
    }
  }

  // 更新标题
  const updateTitle = async () => {
    const historyStore = useHistoryStore()
    const currentChat = historyStore.historyChat.find(
      (item) => item.conversationId === chatId.value,
    )
    // 判断是否是首次消息（消息总数=用户消息1条 + 助手消息1条）
    const isFirstMessage = currentMessages.value.length === 2

    if (currentChat?.messageTitle === '新对话' && isFirstMessage) {
      // 生成标题：取用户消息前20个字符
      const titleContent = currentMessages.value[0].content
        .trim()
        .substring(0, 20)
        .replace(/[\r\n]/g, ' ') // 去除换行符
      await historyStore.updateChatTitle(chatId.value, titleContent)
    }
  }

  // 消息列表操作
  const scrollToBottom = () => {
    if (bubbleListRef.value) {
      bubbleListRef.value.scrollToBottom()
    }
  }

  const scrollToTop = () => {
    if (bubbleListRef.value) {
      bubbleListRef.value.scrollToTop()
    }
  }

  const scrollToBubble = (num) => {
    if (bubbleListRef.value) {
      bubbleListRef.value.scrollToBubble(num)
    }
  }

  return {
    chatId,
    userInput,
    handleSubmit,
    senderLoading,
    currentMessages,
    messageList,
    bubbleListRef,
    selectedFiles,
    fileInput,
    scrollToBottom,
    scrollToTop,
    scrollToBubble,
    isStreaming,
    deep,
  }
})

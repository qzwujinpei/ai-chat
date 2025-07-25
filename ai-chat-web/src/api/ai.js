import request from '@/utils/request.js'
// 给ai发送对话
export const sendAiMessage = async (data, chatId, deep) => {
  try {
    const url = new URL('/api/ai/chat', window.location.origin)
    if (chatId) {
      url.searchParams.append('chatId', chatId)
    }
    if (deep) {
      url.searchParams.append('deep', deep)
    }
    const response = await fetch(url, {
      method: 'POST',
      body: data instanceof FormData ? data : new URLSearchParams({ prompt: data }),
    })

    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`)
    }

    return response.body.getReader()
  } catch (error) {
    console.error('API Error:', error)
    throw error
  }
}
// 获取ai对话历史
export const getAiHistoryService = () => {
  return request.get('/ai/chat/history')
}
// 创建新的对话
export const createNewChatService = (conversationId) => {
  return request({
    url: '/ai/create/chat',
    method: 'get',
    params: {
      conversationId,
    },
  })
}
// 获取ai记忆对话
export const getAiMemoryService = (conversationId) => {
  return request({
    url: '/ai/chat/memory',
    method: 'get',
    params: {
      conversationId,
    },
  })
}
// 保存ai对话
export const saveAiChatService = (data) => {
  return request({
    url: '/ai/chat/add',
    method: 'post',
    data,
  })
}
// 更新对话标题
export const updateChatTitleService = (conversationId, newTitle) => {
  return request({
    url: `/ai/chat/${conversationId}/title`,
    method: 'put',
    params: {
      newTitle,
    },
  })
}
// 删除ai对话
export const deleteAiChatService = (conversationId) => {
  return request({
    url: `/ai/chat/${conversationId}`,
    method: 'delete',
  })
}

<script setup>
import { BubbleList } from 'vue-element-plus-x'
import { useAiStore } from '@/stores'
import setting from '@/setting.js'
import CreateDialog from './components/create-dialog.vue'
import { DocumentCopy, Refresh, Search, Star } from '@element-plus/icons-vue'
import { formatTime } from '@/utils/formatTime.js'
import { marked } from 'marked'
import { useRoute } from 'vue-router' // 引入 useRoute 函数
const route = useRoute() // 获取当前路由实例

const { messageList, bubbleListRef } = storeToRefs(useAiStore())
const handleCopy = async (content) => {
  navigator.clipboard
    .writeText(content)
    .then(() => {
      ElMessage.success('复制成功')
    })
    .catch(() => {
      ElMessage.error('复制失败')
    })
}
</script>

<template>
  <div class="component-container">
    <template v-if="!route.params.chatId">
      <CreateDialog></CreateDialog>
    </template>
    <template v-else>
      <template v-if="messageList.length !== 0">
        <BubbleList ref="bubbleListRef" :list="messageList" max-height="75vh">
          <!-- 自定义头像 -->
          <template #avatar="{ item }">
            <div class="avatar-wrapper">
              <el-image
                round
                style="width: 50px; height: 50px; border-radius: 50px"
                :src="item.avatar"
                fit="contain"
              ></el-image>
            </div>
          </template>
          <!-- 自定义气泡内容 -->
          <template #content="{ item }">
            <div class="content-wrapper">
              <div class="content-text" v-html="marked(item.content)"></div>
            </div>
          </template>
          <!-- 自定义头部 -->
          <template #header="{ item }">
            <div class="header-wrapper">
              <div class="header-name">
                {{
                  item.role === 'assistant'
                    ? `${setting.aiChatName} 🍧`
                    : `🧁 ${setting.userChatName}`
                }}
              </div>
            </div>
          </template>

          <template #footer="{ item }">
            <div class="footer-wrapper">
              <div class="footer-container">
                <el-button type="info" :icon="Refresh" size="small" circle />
                <el-button type="success" :icon="Search" size="small" circle />
                <el-button type="warning" :icon="Star" size="small" circle />
                <el-button
                  @click="handleCopy(item.content)"
                  color="#626aef"
                  :icon="DocumentCopy"
                  size="small"
                  circle
                />
              </div>
              <div class="footer-time">
                {{ formatTime() }}
              </div>
            </div>
          </template>
        </BubbleList>
      </template>

      <div v-else class="welcome-message">
        {{ setting.welcomeChatName }}
      </div>
    </template>
  </div>
</template>

<style scoped lang="scss">
.component-container {
  max-height: calc(100vh - $top-height - $footer-height) !important;
  padding: 0 12px;
  width: $ai-content-width;
  margin: 0 auto;

  .welcome-message {
    text-align: center;
    padding: 40px 24px;
    color: #666;
    font-size: 16px;
    line-height: 1.8;
  }

  .btn-list {
    display: flex;
    flex-wrap: wrap;
    gap: 12px;
  }
  .content-wrapper {
    padding: 12px;
    background: linear-gradient(145deg, #f8f9fa 0%, #e9ecef 100%);
    border-radius: 12px;
    position: relative;
    overflow: hidden;

    &::before {
      content: '';
      position: absolute;
      top: -2px;
      left: -2px;
      right: -2px;
      bottom: -2px;
      background: linear-gradient(45deg, #4facfe 0%, #00f2fe 100%);
      z-index: -1;
      border-radius: inherit;
    }

    .content-text {
      font-size: 14px;
      color: #2c3e50;
      line-height: 1.7;
      position: relative;
    }
  }
  .footer-wrapper {
    display: flex;
    align-items: center;
    gap: 10px;
    .footer-time {
      font-size: 12px;
      margin-top: 3px;
    }
  }
  .footer-container {
    :deep(.el-button + .el-button) {
      margin-left: 8px;
    }
  }
}
:deep(.el-bubble-content) {
  padding: 0 !important;
}
</style>

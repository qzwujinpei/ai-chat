<script setup>
import { useHistoryStore } from '@/stores'
import MoreIcon from './MoreIcon.vue'
const { groupedItems, chatId } = storeToRefs(useHistoryStore())
const historyStore = useHistoryStore()

const { scrollbarRef } = storeToRefs(historyStore)
</script>

<template>
  <el-scrollbar height="75vh" ref="scrollbarRef">
    <div v-for="(group, index) in groupedItems" :key="index" class="scrollbar-group">
      <h4>{{ group.lastActiveTime }}</h4>
      <p
        v-for="item in group.items"
        :key="item.id"
        @click="historyStore.switchChat(item.conversationId)"
        :class="['scrollbar-demo-item', { 'active-dialog': item.conversationId === chatId }]"
      >
        {{ item.messageTitle }}
        <!-- 新增下拉菜单 -->
        <more-icon :current-title="item.messageTitle" :conversation-id="item.conversationId" />
      </p>
    </div>
  </el-scrollbar>
</template>
<style scoped lang="scss">
.el-scrollbar {
  padding: 0 8px;
  background: linear-gradient(145deg, #f8faff 0%, #f0f4ff 100%);

  .scrollbar-group {
    margin-bottom: 1.5rem;
    background: rgba(255, 255, 255, 0.9);
    border-radius: 12px;
    padding: 12px;
    box-shadow:
      0 4px 6px -1px rgba(32, 33, 36, 0.03),
      0 2px 4px -1px rgba(32, 33, 36, 0.02);

    &:last-child {
      margin-bottom: 0.5rem;
    }

    h4 {
      margin: 0 0 12px 4px;
      color: #2c3e50;
      font-size: 0.875rem;
      font-weight: 700;
      letter-spacing: 0.03em;
      display: flex;
      align-items: center;
      padding: 6px 12px;
      background: rgba(64, 158, 255, 0.08);
      border-radius: 8px;

      &::before {
        content: '📅';
        margin-right: 10px;
        font-size: 1.1em;
        filter: drop-shadow(1px 1px 1px rgba(0, 0, 0, 0.1));
      }
    }

    .scrollbar-demo-item {
      --accent-color: #409eff;
      position: relative;
      display: flex;
      align-items: center;
      height: 44px;
      margin: 8px 0;
      padding: 0 16px;
      color: #4a5568;
      border-radius: 8px;
      transition:
        transform 0.25s cubic-bezier(0.4, 0, 0.2, 1),
        box-shadow 0.2s ease,
        background 0.3s ease;
      cursor: pointer;
      background: rgba(255, 255, 255, 0.95);
      border: 1px solid rgba(224, 224, 224, 0.4);
      font:
        500 0.9375rem/1.3 'Inter',
        system-ui,
        sans-serif;
      letter-spacing: 0.01em;

      &::before {
        content: '';
        position: absolute;
        left: -4px;
        top: 50%;
        transform: translateY(-50%);
        width: 3px;
        height: 0;
        background: var(--accent-color);
        border-radius: 2px;
        transition: height 0.3s ease;
      }

      &:hover {
        transform: translateX(6px);
        background: rgba(236, 242, 255, 0.8);
        box-shadow:
          0 2px 8px rgba(64, 158, 255, 0.12),
          0 4px 12px -2px rgba(64, 158, 255, 0.08);

        &::before {
          height: 60%;
        }
      }

      &:active {
        transform: translateX(4px) scale(0.98);
      }

      &.active-dialog {
        --accent-color: #2d6cdb;
        background: linear-gradient(145deg, #3b82f6, #2563eb);
        color: white !important;
        border-color: transparent;
        box-shadow:
          0 2px 12px rgba(59, 130, 246, 0.25),
          0 4px 16px -2px rgba(59, 130, 246, 0.15);

        &:hover {
          background: linear-gradient(145deg, #2563eb, #1d4ed8);
        }
      }
      &.active-dialog {
        .icon-more {
          color: white;
        }
      }
    }
  }
}

@keyframes pulse {
  0% {
    opacity: 0.8;
  }
  50% {
    opacity: 0.4;
  }
  100% {
    opacity: 0.8;
  }
}
</style>

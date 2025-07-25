<script setup>
import { MentionSender } from 'vue-element-plus-x'
import { useAiStore } from '@/stores/index'
import { Promotion } from '@element-plus/icons-vue'
import upload from './components/upload.vue'
import DeepThink from '@/layout/footer/components/deep-think.vue'
import FileList from '@/layout/footer/components/file-list.vue'
const { userInput, senderLoading } = storeToRefs(useAiStore())

const { selectedFiles } = storeToRefs(useAiStore())

const aiStore = useAiStore()
const handleKeyDown = (e) => {
  if (e.key === 'Enter' && !e.shiftKey) {
    e.preventDefault()
    aiStore.handleSubmit()
  }
}
</script>

<template>
  <div class="footer-container">
    <div class="file-list" v-if="selectedFiles.length">
      <file-list />
    </div>
    <MentionSender
      v-model="userInput"
      variant="updown"
      @keydown="handleKeyDown"
      :auto-size="{ minRows: 2, maxRows: 3 }"
      clearable
      allow-speech
      placeholder="💌 在这里你可以输入问题，让AI回答"
    >
      <template #prefix>
        <div class="prefix-container">
          <upload />
          <!-- 动态绑定类名 -->
          <deep-think />
        </div>
      </template>

      <template #action-list>
        <div class="action-list-container">
          <el-button
            @click="aiStore.handleSubmit()"
            v-loading="senderLoading"
            class="promotion-button"
            round
            color="#626aef"
          >
            <el-icon><Promotion /></el-icon>
          </el-button>
        </div>
      </template>
    </MentionSender>
  </div>
</template>
<style scoped lang="scss">
.footer-container {
  --primary-color: #3b82f6;
  --gradient-angle: 145deg;
  width: $input-width;
  margin: 0 auto;
  position: relative;
  bottom: 4rem;
  .file-list {
    position: absolute;
    width: 100%;
    background: rgba(255, 255, 255, 0.95);
    backdrop-filter: blur(12px);
    bottom: calc(100% + 0.5rem);
    left: 0;
    border-radius: 1rem;
    box-shadow: 0 8px 24px rgba(32, 33, 36, 0.08);
    border: 1px solid rgba(224, 232, 255, 0.8);
    padding: 1rem;
    transform: translateY(-0.5rem);
    opacity: 0.95;
    transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  }
}

.prefix-container {
  display: flex;
  align-items: center;
  gap: 1rem;
  padding-right: 1.5rem;
  border-right: 1px solid rgba(224, 232, 255, 0.6);
}

.action-list-container {
  padding-left: 1.5rem;
  border-left: 1px solid rgba(224, 232, 255, 0.6);
}

.promotion-button {
  --btn-size: 3rem;
  width: var(--btn-size);
  height: var(--btn-size);
  border: none;
  background: linear-gradient(var(--gradient-angle), #6366f1 0%, #3b82f6 100%);
  box-shadow: 0 4px 6px rgba(59, 130, 246, 0.15);
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);

  &:hover {
    background: linear-gradient(var(--gradient-angle), #3b82f6 0%, #6366f1 100%);
    transform: scale(1.05);
    box-shadow:
      0 6px 12px rgba(59, 130, 246, 0.25),
      inset 0 2px 2px rgba(255, 255, 255, 0.3);

    .el-icon {
      transform: scale(1.1);
    }
  }

  &:active {
    transform: scale(0.95);
  }

  .el-icon {
    font-size: 1.5rem;
    color: white;
    transition: transform 0.2s ease;
  }
}
</style>

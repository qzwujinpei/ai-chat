<script setup>
import { ElementPlus } from '@element-plus/icons-vue'
import { useAiStore } from '@/stores/index.js'

const { deep } = storeToRefs(useAiStore())
</script>

<template>
  <div :class="{ isSelect: deep, 'thinking-container': true }" @click="deep = !deep">
    <el-icon><ElementPlus /></el-icon>
    <span class="thinking-text">深度思考</span>
  </div>
</template>

<style scoped lang="scss">
/* 深度思考容器样式 */
.thinking-container {
  display: flex;
  align-items: center;
  gap: 4px;
  padding: 2px 12px;
  border: 1px solid silver;
  border-radius: 15px;
  cursor: pointer;
  font-size: 14px;
  /* 深度思考文字样式 */
  .thinking-text {
    padding: 5px;
  }
}
/* 修复样式优先级 */
.isSelect {
  color: #626aef !important; // 确保颜色生效
  border: 1px solid #626aef !important; // 确保边框颜色生效
  background-color: rgba(98, 106, 239, 0.1); // 添加背景色增强视觉效果
  border-radius: 15px;
  padding: 3px 12px;
  font-weight: 700;
}
</style>

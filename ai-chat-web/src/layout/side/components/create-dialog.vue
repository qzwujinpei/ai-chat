<script setup>
import { useHistoryStore } from '@/stores'

const historyStore = useHistoryStore()
</script>

<template>
  <div class="chat-starter" @click="historyStore.createNewChat()">
    <div class="bubble-container">
      <!-- 主气泡 -->
      <div class="main-bubble">
        <!-- 内部白色气泡 -->
        <div class="inner-bubble"></div>
        <!-- 加号图标 -->
        <div class="add-icon">+</div>
      </div>
    </div>
    <span class="action-text">开启新对话</span>
  </div>
</template>

<style scoped lang="scss">
$primary-gradient: linear-gradient(135deg, #3b82f6 0%, #6366f1 100%);
$hover-gradient: linear-gradient(135deg, #6366f1 0%, #3b82f6 100%);

.chat-starter {
  --scale-factor: 1;
  display: flex;
  align-items: center;
  gap: 1rem;
  padding: 0.8rem 1.5rem;
  cursor: pointer;
  background: rgba(255, 255, 255, 0.95);
  border-radius: 1.2rem;
  border: 1px solid rgba(224, 232, 255, 0.8);
  margin: 0 1rem;
  transition: all 0.4s cubic-bezier(0.25, 0.8, 0.25, 1);
  box-shadow:
    0 4px 6px -1px rgba(59, 130, 246, 0.05),
    0 2px 4px -1px rgba(59, 130, 246, 0.02);

  &:hover {
    --scale-factor: 1.02;
    transform: scale(var(--scale-factor)) translateX(4px);
    box-shadow:
      0 6px 12px -1px rgba(59, 130, 246, 0.12),
      0 4px 8px -1px rgba(59, 130, 246, 0.08);

    .main-bubble {
      transform: rotate(180deg);
      filter: brightness(1.1);
    }

    .action-text {
      color: #1d4ed8;
    }
  }

  &:active {
    --scale-factor: 0.98;
    transition-duration: 0.1s;
  }

  .bubble-container {
    position: relative;
    width: 2.8rem;
    height: 2.8rem;
    filter: drop-shadow(0 2px 4px rgba(59, 130, 246, 0.2));

    .main-bubble {
      position: relative;
      width: 100%;
      height: 100%;
      background: $primary-gradient;
      border-radius: 50%;
      display: flex;
      align-items: center;
      justify-content: center;
      transition:
        transform 0.6s cubic-bezier(0.68, -0.55, 0.27, 1.55),
        filter 0.3s ease;

      &::before {
        content: '';
        position: absolute;
        inset: 0;
        background: linear-gradient(45deg, transparent 10%, rgba(255, 255, 255, 0.3) 90%);
        border-radius: 50%;
      }

      .inner-bubble {
        width: 1.4rem;
        height: 1.4rem;
        background: rgba(255, 255, 255, 0.9);
        border-radius: 30%;
        transform: rotate(45deg);
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
      }

      .add-icon {
        position: absolute;
        color: #3b82f6;
        font-size: 1.4rem;
        font-weight: 300;
        transform: rotate(0deg);
        transition: transform 0.4s ease;
      }
    }
  }

  .action-text {
    font:
      500 1.05rem/1 'Inter',
      sans-serif;
    color: #3b82f6;
    letter-spacing: 0.03em;
    background: linear-gradient(135deg, #3b82f6 0%, #6366f1 100%);
    -webkit-background-clip: text;
    background-clip: text;
    -webkit-text-fill-color: transparent;
    transition: all 0.3s ease;
    padding-right: 0.4rem;
  }
}
</style>

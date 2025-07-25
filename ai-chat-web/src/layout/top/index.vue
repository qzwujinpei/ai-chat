<script setup>
import { useHistoryStore } from '@/stores'
import { useRoute } from 'vue-router'

const route = useRoute()

const { activeTitle } = storeToRefs(useHistoryStore())
</script>
<template>
  <div class="top-container">
    <div class="title" v-if="route.params.chatId">
      <h1>{{ activeTitle }}</h1>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.top-container {
  height: $top-height;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 0 1rem;
  background: linear-gradient(145deg, #f8faff 0%, #f0f4ff 100%);
  position: relative;
  box-shadow: 0 4px 6px -1px rgba(32, 33, 36, 0.03);

  &::after {
    content: '';
    position: absolute;
    bottom: 0;
    left: 50%;
    transform: translateX(-50%);
    width: 40%;
    height: 2px;
    background: linear-gradient(90deg, transparent 0%, #3b82f6 50%, transparent 100%);
    opacity: 0.4;
  }

  .title {
    --gradient-angle: 145deg;
    width: min(90%, 50ch);
    padding: 0.75rem 1.5rem;
    transition: all 0.4s cubic-bezier(0.4, 0, 0.2, 1);
    border-radius: 12px;
    background: rgba(255, 255, 255, 0.9);
    backdrop-filter: blur(4px);
    box-shadow:
      0 2px 4px rgba(59, 130, 246, 0.05),
      inset 0 1px 1px rgba(255, 255, 255, 0.5);

    h1 {
      text-align: center;
      margin: 0;
      font:
        600 1.5rem/1.3 'Inter',
        sans-serif;
      background: linear-gradient(var(--gradient-angle), #3b82f6 0%, #6366f1 100%);
      -webkit-background-clip: text;
      background-clip: text;
      -webkit-text-fill-color: transparent;
      letter-spacing: -0.02em;
      position: relative;
      padding-bottom: 0.25em;

      &::after {
        content: '';
        position: absolute;
        bottom: 0;
        left: 50%;
        transform: translateX(-50%);
        width: 0;
        height: 2px;
        background: #3b82f6;
        transition: width 0.4s ease;
      }
    }

    &:hover {
      transform: translateY(-1px);
      box-shadow:
        0 4px 12px rgba(59, 130, 246, 0.12),
        inset 0 1px 1px rgba(255, 255, 255, 0.8);

      h1::after {
        width: 100%;
      }
    }

    &:active {
      transform: translateY(0);
      transition-duration: 0.1s;
    }
  }
}
</style>

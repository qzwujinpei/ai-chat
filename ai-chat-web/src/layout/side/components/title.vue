<script setup>
import settings from '@/setting.js'
</script>

<template>
  <div class="title-box">
    <span>{{ settings.title }}</span>
  </div>
</template>

<style lang="scss" scoped>
.title-box {
  padding: 10px;
  width: 100%;
  height: $header-height;
  display: flex;
  align-items: center;
  justify-content: center;

  span {
    font-size: clamp(1.25rem, 3vw, 1.5rem); // 响应式字体
    font-weight: 700;
    font-family:
      'Segoe UI',
      system-ui,
      -apple-system,
      sans-serif; // 现代字体栈
    letter-spacing: 0.05em; // 字距微调
    line-height: 1.2;
    color: linear-gradient(135deg, #2c3e50 0%, #3498db 100%); // 渐变文字
    background: linear-gradient(135deg, #2c3e50 0%, #3498db 100%);
    -webkit-background-clip: text;
    background-clip: text;
    -webkit-text-fill-color: transparent;
    text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.1); // 柔和阴影
    position: relative;
    transition: all 0.3s ease;

    &::after {
      content: '';
      position: absolute;
      bottom: -4px;
      left: 0;
      width: 100%;
      height: 2px;
      background: currentColor;
      transform: scaleX(0);
      transition: transform 0.3s ease;
    }

    &:hover {
      transform: scale(1.02);
      &::after {
        transform: scaleX(1);
      }
    }
  }
}
</style>

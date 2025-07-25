<script setup>
import { MagicStick, Plus } from '@element-plus/icons-vue'
import { useHistoryStore } from '@/stores/index.js'
const historyStore = useHistoryStore()
</script>

<template>
  <div class="create-new-prompt">
    <div class="prompt-container">
      <div class="icon-box">
        <el-icon class="sparkle-icon"><MagicStick /></el-icon>
      </div>
      <h2 class="prompt-title">开启新的智能对话</h2>
      <p class="prompt-description">
        点击下方按钮开始与元宝对话<br />
        你可以进行问题咨询、文档解析或创意生成
      </p>
      <el-button type="primary" class="start-button" @click="historyStore.createNewChat()">
        <el-icon class="icon-left"><Plus /></el-icon>
        开始新的对话
      </el-button>
    </div>
    <div class="floating-elements">
      <div class="floating-circle circle-1"></div>
      <div class="floating-circle circle-2"></div>
      <div class="floating-circle circle-3"></div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.create-new-prompt {
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;
  min-height: 70vh;
  position: relative;
  overflow: hidden;
  padding: 24px;

  .prompt-container {
    max-width: 500px;
    padding: 40px;
    background: rgba(255, 255, 255, 0.9);
    border-radius: 20px;
    box-shadow: 0 10px 30px rgba(0, 0, 0, 0.05);
    backdrop-filter: blur(5px);
    position: relative;
    z-index: 2;
    transform: translateY(0);
    transition: transform 0.3s ease;

    .icon-box {
      margin: 0 auto 24px;
      background: linear-gradient(135deg, #6a11cb 0%, #2575fc 100%);
      width: 100px;
      height: 100px;
      border-radius: 24px;
      display: flex;
      align-items: center;
      justify-content: center;
      box-shadow: 0 8px 20px rgba(106, 17, 203, 0.2);
      transition: all 0.3s ease;

      &:hover {
        transform: scale(1.05) rotate(5deg);
      }

      .sparkle-icon {
        font-size: 48px;
        color: white;
      }
    }

    .prompt-title {
      font-size: 28px;
      color: #2c3e50;
      margin-bottom: 16px;
      font-weight: 700;
      background: linear-gradient(135deg, #2c3e50, #4ca1af);
      -webkit-background-clip: text;
      -webkit-text-fill-color: transparent;
    }

    .prompt-description {
      color: #6c757d;
      line-height: 1.8;
      margin-bottom: 32px;
      font-size: 16px;
    }

    .start-button {
      padding: 14px 36px;
      font-size: 16px;
      font-weight: 500;
      border-radius: 12px;
      transition: all 0.3s ease;
      background: linear-gradient(135deg, #6a11cb 0%, #2575fc 100%);
      border: none;
      box-shadow: 0 4px 15px rgba(106, 17, 203, 0.3);

      .icon-left {
        margin-right: 8px;
        font-size: 18px;
      }

      &:hover {
        transform: translateY(-3px);
        box-shadow: 0 8px 20px rgba(106, 17, 203, 0.4);
      }

      &:active {
        transform: translateY(0);
      }
    }
  }

  .floating-elements {
    position: absolute;
    width: 100%;
    height: 100%;
    top: 0;
    left: 0;
    z-index: 1;

    .floating-circle {
      position: absolute;
      border-radius: 50%;
      opacity: 0.1;
      filter: blur(30px);
      animation: float 15s infinite ease-in-out;
    }

    .circle-1 {
      width: 300px;
      height: 300px;
      background: #6a11cb;
      top: 20%;
      left: 10%;
      animation-delay: 0s;
    }

    .circle-2 {
      width: 200px;
      height: 200px;
      background: #2575fc;
      bottom: 15%;
      right: 10%;
      animation-delay: 2s;
    }

    .circle-3 {
      width: 150px;
      height: 150px;
      background: #4ca1af;
      top: 60%;
      left: 30%;
      animation-delay: 4s;
    }
  }
}

@keyframes float {
  0%,
  100% {
    transform: translateY(0) rotate(0deg);
  }
  50% {
    transform: translateY(-20px) rotate(5deg);
  }
}
</style>

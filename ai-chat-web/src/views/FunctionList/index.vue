<template>
  <div class="app-container">
    <!-- 导航栏 -->
    <el-row class="nav" justify="space-between" align="middle">
      <el-col :span="4">
        <div class="logo">Heima AI Hub</div>
      </el-col>
      <el-col :span="4" class="text-right">
        <el-icon :size="24" color="#1890ff">
          <Moon />
        </el-icon>
      </el-col>
    </el-row>

    <!-- 主标题 -->
    <h1 class="main-title">黑马AI应用中心</h1>

    <!-- 应用卡片 -->
    <el-row class="card-container" :gutter="30">
      <el-col
        @click="handleRouteChange(app.path)"
        v-for="(app, index) in apps"
        :key="index"
        :xs="24"
        :sm="12"
        :md="8"
        :lg="6"
      >
        <el-card class="app-card" shadow="hover">
          <div class="card-icon">
            <el-icon :size="32" color="#1890ff">
              <component :is="app.icon" />
            </el-icon>
          </div>
          <h3 class="card-title">{{ app.title }}</h3>
          <p class="card-desc">{{ app.description }}</p>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import EventEmitter from '@/utils/EventEmitter'
import { Moon, ChatRound, Headset, Files, VideoPlay } from '@element-plus/icons-vue'

const apps = ref([
  {
    title: 'AI聊天',
    icon: markRaw(ChatRound), // 使用 markRaw 标记组件
    description: '多模态对话机器人，支持图片、音频等',
    path: '/ai-chat',
  },
  {
    title: '哄哄模拟器',
    icon: markRaw(VideoPlay), // 使用 markRaw 标记组件
    description: '一个帮助你练习哄女朋友开心的小游戏',
    path: '/giggle',
  },
  {
    title: '黑马智能客服',
    icon: markRaw(Headset), // 使用 markRaw 标记组件
    description: '24小时在线的智能课程咨询师',
    path: '/ai-customer',
  },
  {
    title: 'ChatPDF',
    icon: markRaw(Files), // 使用 markRaw 标记组件
    description: '打造你的个人知识库，与知识库自由对话',
    path: '/ai-pdf',
  },
])
const handleRouteChange = (path) => {
  EventEmitter.emit('ROUTE_CHANGE', path)
}
</script>
<style lang="scss" scoped>
.app-container {
  background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
  min-height: 100vh;
  padding: 0;
  max-width: 1440px;
  margin: 0 auto;

  .nav {
    padding: 16px 64px;
    background: rgba(255, 255, 255, 0.95);
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.06);
    position: sticky;
    top: 0;
    z-index: 1000;
    backdrop-filter: blur(8px);

    .logo {
      color: #1a1a1a;
      font-size: 28px;
      font-weight: 700;
      letter-spacing: 1px;
      background: linear-gradient(45deg, #1890ff 30%, #2058ff 100%);
      -webkit-background-clip: text;
      background-clip: text;
      -webkit-text-fill-color: transparent;
      transition: all 0.3s ease;
    }

    .el-icon {
      cursor: pointer;
      padding: 8px;
      border-radius: 50%;
      transition: all 0.3s ease;

      &:hover {
        background: rgba(24, 144, 255, 0.1);
        transform: rotate(15deg);
      }
    }
  }

  .main-title {
    color: #1a1a1a;
    font-size: 40px;
    text-align: center;
    margin: 80px 0 60px;
    position: relative;
    font-weight: 600;

    &::after {
      content: '';
      display: block;
      width: 60px;
      height: 3px;
      background: #1890ff;
      margin: 16px auto 0;
      border-radius: 2px;
    }
  }

  .card-container {
    padding: 0 64px;
    margin: 0 auto;

    .app-card {
      margin-bottom: 30px;
      border-radius: 16px;
      border: none;
      transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
      background: rgba(255, 255, 255, 0.9);
      backdrop-filter: blur(8px);
      height: 240px;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      text-align: center;
      cursor: pointer;
      &:hover {
        transform: translateY(-8px);
        box-shadow: 0 12px 24px rgba(24, 144, 255, 0.15);
      }

      .card-icon {
        width: 56px;
        height: 56px;
        background: linear-gradient(
          135deg,
          rgba(24, 144, 255, 0.1) 0%,
          rgba(24, 144, 255, 0.2) 100%
        );
        margin: 0 auto 20px;
        border-radius: 14px;
        transition: all 0.3s ease;
        display: flex;
        justify-content: center;
        align-items: center;
        .el-icon {
          transition: all 0.3s ease;
        }
      }

      .card-title {
        color: #1a1a1a;
        font-size: 20px;
        margin-bottom: 12px;
        font-weight: 600;
        letter-spacing: 0.5px;
      }

      .card-desc {
        color: #666;
        font-size: 14px;
        line-height: 1.6;
        padding: 0 16px;
      }
    }

    :deep(.el-card__body) {
      padding: 40px 24px;
    }
  }

  @media (max-width: 768px) {
    .nav {
      padding: 12px 24px;

      .logo {
        font-size: 22px;
      }
    }

    .main-title {
      font-size: 32px;
      margin: 40px 0;
    }

    .card-container {
      padding: 0 24px;

      .app-card {
        height: 200px;
      }
    }
  }
}
</style>

<script setup>
import { ArrowRight, User, SwitchButton, Setting, Message } from '@element-plus/icons-vue'
import { useUserStore } from '@/stores/index.js'
import EventEmitter from '@/utils/EventEmitter.js'

const userStore = useUserStore()

const handleLogout = () => {
  ElMessageBox.confirm('确定要退出登录吗?', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
    customClass: 'custom-message-box',
  })
    .then(() => {
      userStore.clearToken()
      ElNotification({
        title: '成功',
        message: '您已成功退出登录',
        type: 'success',
        customClass: 'custom-notification',
      })
      EventEmitter.emit('ROUTE_CHANGE', '/login')
    })
    .catch(() => {})
}
</script>

<template>
  <div class="fixed-container">
    <el-dropdown trigger="click" placement="top-start" popper-class="user-dropdown">
      <div class="user-container">
        <div class="avatar-wrapper">
          <el-avatar class="user-avatar">
            <el-icon>
              <User />
            </el-icon>
          </el-avatar>
          <div class="online-indicator"></div>
        </div>
        <div class="user-info">
          <span class="username">{{ userStore.userInfo?.username || '用户' }}</span>
          <span class="user-email">{{ userStore.userInfo?.email || 'xxx@yzt.com' }}</span>
        </div>
        <el-icon class="dropdown-icon">
          <ArrowRight />
        </el-icon>
      </div>

      <template #dropdown>
        <el-dropdown-menu>
          <el-dropdown-item class="dropdown-item">
            <el-icon class="item-icon"><User /></el-icon>
            <span>个人中心</span>
            <div class="badge">3</div>
          </el-dropdown-item>
          <el-dropdown-item divided class="dropdown-item logout" @click="handleLogout">
            <el-icon class="item-icon"><SwitchButton /></el-icon>
            <span>退出登录</span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </template>
    </el-dropdown>
  </div>
</template>

<style lang="scss" scoped>
.fixed-container {
  position: fixed;
  bottom: 0;
  left: 0;
  z-index: 1000;
  width: $sidebar-width;
  padding: 0 1rem 0.1rem;
}

.user-container {
  position: relative;
  padding: 1rem;
  border-radius: 16px;
  background: rgba(255, 255, 255, 0.8);
  border: 1px solid rgba(255, 255, 255, 0.9);
  box-shadow:
    0 4px 6px -1px rgba(0, 0, 0, 0.05),
    0 2px 4px -1px rgba(0, 0, 0, 0.02),
    inset 0 0 0 1px rgba(255, 255, 255, 0.8);
  display: flex;
  align-items: center;
  gap: 1rem;
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  cursor: pointer;
  overflow: hidden;

  &::after {
    content: '';
    position: absolute;
    inset: 0;
    background: linear-gradient(135deg, rgba(99, 102, 241, 0.1) 0%, rgba(59, 130, 246, 0.1) 100%);
    opacity: 0;
    transition: opacity 0.3s ease;
  }

  &:hover {
    transform: translateY(-3px);
    background: rgba(255, 255, 255, 0.95);
    box-shadow:
      0 10px 15px -3px rgba(0, 0, 0, 0.08),
      0 4px 6px -2px rgba(0, 0, 0, 0.05),
      inset 0 0 0 1px rgba(255, 255, 255, 0.9);

    &::after {
      opacity: 1;
    }

    .user-avatar {
      transform: scale(1.1);
      box-shadow: 0 4px 12px rgba(59, 130, 246, 0.25);
    }

    .dropdown-icon {
      opacity: 1;
      transform: translateX(3px);
    }
  }
}

.avatar-wrapper {
  position: relative;
  width: 40px;
  height: 40px;
}

.user-avatar {
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  width: 100%;
  height: 100%;
  background: linear-gradient(135deg, #6366f1 0%, #3b82f6 100%);
  color: white;
  border: 2px solid white;
  box-shadow:
    inset 0 2px 4px rgba(0, 0, 0, 0.1),
    0 2px 4px rgba(0, 0, 0, 0.1);

  .el-icon {
    font-size: 18px;
  }
}

.online-indicator {
  position: absolute;
  bottom: 0;
  right: 0;
  width: 10px;
  height: 10px;
  background: #22c55e;
  border-radius: 50%;
  border: 2px solid white;
  box-shadow: 0 0 0 1px rgba(0, 0, 0, 0.05);
}

.user-info {
  display: flex;
  flex-direction: column;
  flex-grow: 1;
  overflow: hidden;
}

.username {
  font:
    500 0.9375rem/1.3 'Inter',
    sans-serif;
  color: #1e293b;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.user-email {
  font-size: 0.75rem;
  color: #64748b;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.dropdown-icon {
  margin-left: auto;
  opacity: 0.7;
  transition: all 0.3s ease;
  color: #64748b;
  font-size: 14px;
}

:deep(.user-dropdown) {
  .el-dropdown-menu {
    padding: 8px;
    border-radius: 12px;
    box-shadow:
      0 10px 15px -3px rgba(0, 0, 0, 0.1),
      0 4px 6px -2px rgba(0, 0, 0, 0.05);
    border: 1px solid rgba(255, 255, 255, 0.9);
    background: rgba(255, 255, 255, 0.98);
    backdrop-filter: blur(12px);
  }

  .dropdown-item {
    padding: 8px 12px;
    border-radius: 8px;
    margin-bottom: 4px;
    transition: all 0.2s ease;
    display: flex;
    align-items: center;
    position: relative;

    &:hover {
      background: rgba(59, 130, 246, 0.1);
      color: #3b82f6;
    }

    .item-icon {
      margin-right: 10px;
      font-size: 16px;
    }

    .badge {
      position: absolute;
      right: 12px;
      top: 50%;
      transform: translateY(-50%);
      background: #f43f5e;
      color: white;
      font-size: 0.6875rem;
      padding: 2px 6px;
      border-radius: 10px;
      min-width: 20px;
      text-align: center;
      line-height: 1;

      &.new {
        background: #3b82f6;
      }
    }

    &.logout {
      color: #f43f5e;

      &:hover {
        background: rgba(244, 63, 94, 0.1);
      }
    }
  }
}

.custom-message-box {
  border-radius: 16px !important;
  backdrop-filter: blur(12px) !important;
  background: rgba(255, 255, 255, 0.95) !important;
  border: 1px solid rgba(255, 255, 255, 0.9) !important;
}

.custom-notification {
  border-radius: 12px !important;
  backdrop-filter: blur(12px) !important;
  background: rgba(255, 255, 255, 0.95) !important;
  border: 1px solid rgba(255, 255, 255, 0.9) !important;
}
</style>

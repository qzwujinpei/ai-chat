<script setup>
import forgetPasswd from './forgetPasswd.vue'
import { userLoginService } from '@/api/user'
import { Lock, User } from '@element-plus/icons-vue'
import Captcha from '@/components/Captcha.vue'
import { useUserStore } from '@/stores'
import emitter from '@/utils/EventEmitter'

const userStore = useUserStore()
// 数据
const formModel = ref({
  username: '',
  password: '',
  keepLoginStatus: true,
  captcha: '',
  uuid: '',
})

const formRef = ref(null)
// 登录提交检验
// 检验函数
const handleUuid = (uuid) => {
  formModel.value.uuid = uuid
}
const onSubmit = async () => {
  if (formModel.value.username === '' || formModel.value.password === '') {
    ElMessage({ type: 'error', message: '账号或密码不能为空！' })
    return
  }
  if (formModel.value.captcha === '') {
    ElMessage({ type: 'error', message: '验证码不能为空！' })
    return
  }
  if (loading.value) {
    return
  }
  loading.value = true
  const res = await userLoginService(formModel.value)
  if (res.code === 1) {
    ElMessage.success('登录成功')
    userStore.setToken(res.data)
    emitter.emit('ROUTE_CHANGE', { path: '/' })
  } else {
    loading.value = false
    ElMessage.error(res.msg)
  }
}

const showDialog = () => {
  dialogRef.value.open()
}
const dialogRef = ref(null)
const loading = ref(false)
</script>

<template>
  <div class="login-container">
    <div class="login-background">
      <div class="background-overlay"></div>
    </div>

    <div class="login-card">
      <div class="card-header">
        <h1>欢迎登录</h1>
        <p>请输入您的账号信息</p>
      </div>

      <el-form ref="formRef" :model="formModel" class="login-form">
        <!-- 输入框部分 -->
        <div class="form-group">
          <el-input
            v-model="formModel.username"
            :prefix-icon="User"
            placeholder="用户名"
            class="custom-input"
          />
        </div>

        <div class="form-group">
          <el-input
            v-model="formModel.password"
            :prefix-icon="Lock"
            placeholder="密码"
            type="password"
            show-password
            class="custom-input"
          />
        </div>

        <div class="form-group captcha-group">
          <Captcha v-model="formModel.captcha" @currentCaptchaUuid="handleUuid" />
        </div>
         
        <!--
        <div class="form-options">
          <el-checkbox v-model="formModel.keepLoginStatus"> 七天免密登录</el-checkbox>
          <el-link type="primary" @click="showDialog"> 忘记密码?</el-link>
        </div> -->

        <el-button class="login-button" type="primary" :loading="loading" @click="onSubmit">
          立即登录
        </el-button>
        <!-- 新增注册入口 -->
        <div class="register-tip">
          <span>没有账号？</span>
          <el-link type="primary" @click="emitter.emit('ROUTE_CHANGE', { path: '/register' })"
            >立即注册</el-link
          >
        </div>
      </el-form>
    </div>

    <forget-passwd ref="dialogRef" />
  </div>
</template>

<style lang="scss" scoped>
.login-container {
  position: relative;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  overflow: hidden;

  .login-background {
    position: absolute;
    width: 100%;
    height: 100%;
    background: url('@/assets/images/jks.jpg') no-repeat center/cover;
    filter: blur(1px);
    transform: scale(1.02);

    .background-overlay {
      position: absolute;
      width: 100%;
      height: 100%;
      background: rgba(0, 0, 0, 0.4);
    }
  }

  .login-card {
    position: relative;
    width: 90%;
    max-width: 420px;
    background: rgba(255, 255, 255, 0.95);
    border-radius: 16px;
    box-shadow: 0 12px 30px rgba(0, 0, 0, 0.15);
    padding: 40px;
    transform: translateY(-10%);
    transition: all 0.3s ease;

    .card-header {
      text-align: center;
      margin-bottom: 32px;

      h1 {
        font-size: 24px;
        color: #303133;
        margin-bottom: 8px;
      }

      p {
        color: #909399;
        font-size: 14px;
      }
    }

    .login-form {
      .register-tip {
        text-align: center;
        margin-top: 20px;
        color: #606266;
        font-size: 14px;

        .el-link {
          margin-left: 8px;
        }
      }

      .form-group {
        margin-bottom: 22px;

        .custom-input {
          :deep(.el-input__inner) {
            height: 44px;
            border-radius: 8px;
            padding-left: 40px;
            background: #f5f7fa;
            transition: all 0.3s;

            &:focus {
              background: #fff;
              border-color: #409eff;
              box-shadow: 0 0 8px rgba(64, 158, 255, 0.2);
            }
          }

          :deep(.el-input__prefix) {
            left: 12px;
            display: flex;
            align-items: center;
          }
        }
      }

      .captcha-group {
        display: flex;
        gap: 12px;

        .captcha-input {
          flex: 1;
        }

        .-wrapper {
          position: relative;
          cursor: pointer;
          border-radius: 8px;
          overflow: hidden;
          transition: transform 0.3s;

          transform: translateY(-2px);

          .captcha-refresh {
            opacity: 1;
          }

          .captcha-refresh {
            position: absolute;
            bottom: 0;
            left: 0;
            right: 0;
            background: rgba(0, 0, 0, 0.4);
            color: white;
            display: flex;
            justify-content: center;
            padding: 4px;
            opacity: 0;
            transition: opacity 0.3s;
          }
        }
      }

      .form-options {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin: 18px 0;

        :deep(.el-checkbox) {
          color: #606266;
        }
      }

      .login-button {
        width: 100%;
        height: 44px;
        font-size: 16px;
        letter-spacing: 2px;
        background: linear-gradient(45deg, #409eff, #79bbff);
        border: none;
        transition: all 0.3s;

        &:hover {
          opacity: 0.9;
          transform: translateY(-1px);
        }

        &:active {
          transform: translateY(0);
        }
      }
    }
  }
}

@media (max-width: 480px) {
  .login-card {
    padding: 24px;
    width: 85%;

    .card-header {
      h1 {
        font-size: 20px;
      }

      p {
        font-size: 13px;
      }
    }
  }
}
</style>

<script setup>
import { userRegisterService } from '@/api/user'
import { Lock, User, Message } from '@element-plus/icons-vue'
import Captcha from '@/components/Captcha.vue'
import { useUserStore } from '@/stores'
import emitter from '@/utils/EventEmitter'

const userStore = useUserStore()
// 数据
const formModel = ref({
  username: '',
  password: '',
  confirmPassword: '',
  email: '',
  captcha: '',
  uuid: '',
})

const formRef = ref(null)
// 注册提交检验
const handleUuid = (uuid) => {
  formModel.value.uuid = uuid
}

const validatePassword = (rule, value, callback) => {
  if (value !== formModel.value.password) {
    callback(new Error('两次输入的密码不一致!'))
  } else {
    callback()
  }
}

const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 16, message: '长度在 3 到 16 个字符', trigger: 'blur' },
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' },
  ],
  confirmPassword: [
    { required: true, message: '请再次输入密码', trigger: 'blur' },
    { validator: validatePassword, trigger: 'blur' },
  ],
  email: [
    { required: true, message: '请输入qq邮箱，用于找回密码', trigger: 'blur' },
    { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur' },
  ],
  captcha: [{ required: true, message: '请输入验证码', trigger: 'blur' }],
}

const onSubmit = async () => {
  if (!formRef.value) return
  await formRef.value.validate(async (valid) => {
    if (valid) {
      if (loading.value) return
      loading.value = true
      try {
        const res = await userRegisterService(formModel.value)
        if (res.code === 1) {
          ElMessage.success('注册成功')
          userStore.setToken(res.data)
          emitter.emit('ROUTE_CHANGE', { path: '/' })
        } else {
          ElMessage.error(res.msg)
        }
      } finally {
        loading.value = false
      }
    }
  })
}

const showLogin = () => {
  emitter.emit('ROUTE_CHANGE', { path: '/login' })
}

const loading = ref(false)
</script>

<template>
  <div class="login-container">
    <div class="login-background">
      <div class="background-overlay"></div>
    </div>

    <div class="login-card">
      <div class="card-header">
        <h1>用户注册</h1>
        <p>创建您的账号</p>
      </div>

      <el-form ref="formRef" :model="formModel" :rules="rules" class="login-form">
        <!-- 输入框部分 -->
        <div class="form-group">
          <el-form-item prop="username">
            <el-input
              v-model="formModel.username"
              :prefix-icon="User"
              placeholder="用户名"
              class="custom-input"
            />
          </el-form-item>
        </div>
        <div class="form-group">
          <el-form-item prop="email">
            <el-input
              v-model="formModel.email"
              :prefix-icon="Message"
              placeholder="邮箱"
              class="custom-input"
            />
          </el-form-item>
        </div>

        <div class="form-group">
          <el-form-item prop="password">
            <el-input
              v-model="formModel.password"
              :prefix-icon="Lock"
              placeholder="密码"
              type="password"
              show-password
              class="custom-input"
            />
          </el-form-item>
        </div>

        <div class="form-group">
          <el-form-item prop="confirmPassword">
            <el-input
              v-model="formModel.confirmPassword"
              :prefix-icon="Lock"
              placeholder="确认密码"
              type="password"
              show-password
              class="custom-input"
            />
          </el-form-item>
        </div>

        <div class="form-group captcha-group">
          <el-form-item prop="captcha">
            <Captcha v-model="formModel.captcha" @currentCaptchaUuid="handleUuid" />
          </el-form-item>
        </div>

        <el-button class="login-button" type="primary" :loading="loading" @click="onSubmit">
          立即注册
        </el-button>

        <div class="register-tip">
          已有账号?
          <el-link type="primary" @click="showLogin">立即登录</el-link>
        </div>
      </el-form>
    </div>
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
    max-width: 450px;
    background: rgba(255, 255, 255, 0.95);
    border-radius: 16px;
    box-shadow: 0 12px 30px rgba(0, 0, 0, 0.15);
    padding: 40px;
    transform: translateY(-5%);
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
      .form-group {
        margin-bottom: 18px;

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

      .login-button {
        width: 100%;
        height: 44px;
        font-size: 16px;
        letter-spacing: 2px;
        background: linear-gradient(45deg, #409eff, #79bbff);
        border: none;
        transition: all 0.3s;
        margin-top: 10px;

        &:hover {
          opacity: 0.9;
          transform: translateY(-1px);
        }

        &:active {
          transform: translateY(0);
        }
      }

      .register-tip {
        text-align: center;
        margin-top: 20px;
        color: #606266;
        font-size: 14px;

        .el-link {
          margin-left: 5px;
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

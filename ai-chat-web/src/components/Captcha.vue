<script setup>
import { getCaptchaService } from '@/api/captcha'
import { Unlock } from '@element-plus/icons-vue'
// 定义响应式变量
const captchaImage = ref('')
const currentCaptchaUuid = ref('')
const modelValue = defineModel()
const emit = defineEmits(['currentCaptchaUuid'])
// 获取验证码
const fetchCaptcha = async () => {
  try {
    const res = await getCaptchaService()
    const { uuid, img } = res.data

    // 更新 UUID 和图片
    currentCaptchaUuid.value = uuid
    captchaImage.value = img
    emit('currentCaptchaUuid', currentCaptchaUuid.value)
  } catch (error) {
    console.error('获取验证码失败:', error)
  }
}
// 刷新验证码
const refreshCaptcha = () => {
  fetchCaptcha()
}

onMounted(() => {
  // 初始化加载验证码
  fetchCaptcha()
})
</script>

<template>
  <div class="captcha-container">
    <!-- 输入框 -->
    <el-input
      :prefix-icon="Unlock"
      v-model="modelValue"
      placeholder="请输入验证码"
      style="margin-right: 10px"
    ></el-input>
    <!-- 验证码图片 -->
    <img :src="captchaImage" alt="验证码" @click="refreshCaptcha" style="cursor: pointer" />
  </div>
</template>

<style scoped lang="scss">
.captcha-container {
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
}
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
</style>

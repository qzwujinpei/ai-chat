<script setup>
import { resetPassword, sendCode } from '@/api/user'
import { ElMessage, ElMessageBox } from 'element-plus'

const isShow = ref(false)
const open = () => {
  isShow.value = true
}
defineExpose({
  open,
})
const rePwdForm = reactive({
  teacherId: '',
  email: '',
})
const isCounting = ref(false)
const countdownText = computed(() =>
  isCounting.value ? `${countdown.value} 秒后重新发送` : '获取验证码',
)
const refPwdRef = ref(null)
const countdown = ref(0)
const startCountdown = async () => {
  await refPwdRef.value.validate()
  await sendCode(rePwdForm)
  countdown.value = 60 // 设置倒计时时间为 60 秒
  isCounting.value = true
  const interval = setInterval(() => {
    if (countdown.value > 0) {
      countdown.value--
    } else {
      clearInterval(interval)
      isCounting.value = false
    }
  }, 1000)
  ElMessageBox.prompt('已发送qq邮箱验证码，请查看输入', '重置密码', {
    confirmButtonText: '确认',
    cancelButtonText: '取消',
  })
    .then(async ({ value }) => {
      const res = await resetPassword({
        teacherId: rePwdForm.teacherId,
        code: value,
      })
      if (res.code === 1) {
        ElMessage({
          type: 'success',
          message: `你的密码重置为:123456`,
        })
        isShow.value = false
      } else {
        ElMessage({
          type: 'error',
          message: res.msg,
        })
      }
    })
    .catch(() => {
      ElMessage({
        type: 'info',
        message: 'Input canceled',
      })
    })
}
</script>

<template>
  <el-dialog v-model="isShow" title="重置密码" width="600px">
    <el-form ref="refPwdRef" :model="rePwdForm" :rules="rules" size="large">
      <el-form-item>
        <el-input
          v-model="rePwdForm.teacherId"
          class="text"
          clearable
          placeholder="请输入用户名"
          prefix-icon="User"
        />
      </el-form-item>
      <el-form-item prop="email">
        <el-input
          v-model="rePwdForm.email"
          class="text"
          clearable
          placeholder="请输入qq邮箱"
          prefix-icon="Lock"
        />
      </el-form-item>
      <el-button
        :disabled="rePwdForm.teacherId === '' || rePwdForm.email === ''"
        :loading="isCounting"
        :type="isCounting ? 'text' : 'primary'"
        @click="startCountdown"
      >
        {{ countdownText }}
      </el-button>
    </el-form>
  </el-dialog>
</template>

<style lang="scss" scoped></style>

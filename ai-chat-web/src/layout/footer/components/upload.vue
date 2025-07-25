<script setup>
import { Paperclip } from '@element-plus/icons-vue'
import setting from '@/setting.js'
import { useAiStore } from '@/stores'
import { compressImage } from '@/utils/compressImage.js'

const { selectedFiles } = storeToRefs(useAiStore())

const handleExceed = (files, fileList) => {
  ElMessage.warning(
    `当前限制选择 ${setting.maxUploadFileCount} 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`,
  )
}
// 新增handleChange函数
const handleChange = async (file, fileList) => {
  // 更新 selectedFiles
  selectedFiles.value = await Promise.all(fileList.map((item) => compressImage(item.raw)))
}
const customRequest = () => {}
</script>

<template>
  <el-upload
    class="upload-demo"
    multiple
    action="#"
    :limit="setting.maxUploadFileCount"
    :on-exceed="handleExceed"
    :on-change="handleChange"
    :http-request="customRequest"
  >
    <el-button round plain color="#626aef">
      <el-icon><Paperclip /></el-icon>
    </el-button>
  </el-upload>
</template>

<style scoped lang="scss">
.upload-demo {
  text-align: center;
  display: flex;
  align-items: center;
}
:deep(.el-upload-list) {
  display: none;
}
</style>

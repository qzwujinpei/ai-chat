<script setup>
import TopTitle from '@/layout/side/components/title.vue'
import CreateDialog from '@/layout/side/components/create-dialog.vue'
import historyRecord from './components/history-record.vue'
import userInfo from './components/user-info.vue'
</script>
<template>
  <!-- 使用 flex 布局 -->
  <div style="height: 99vh; display: flex; flex-direction: column">
    <top-title></top-title>
    <div class="create-dialog">
      <create-dialog />
    </div>
    <!-- 让 history-record 区域可以自动填充剩余空间 -->
    <div class="history-record" style="flex: 1; overflow-y: auto">
      <history-record />
    </div>
    <div class="user-info">
      <user-info />
    </div>
  </div>
</template>

<style lang="scss" scoped>
.create-dialog {
  margin-top: 10px;
}
.history-record {
  margin-top: 10px;
}
.user-info {
  /* 可以添加一些底部的样式 */
}
</style>

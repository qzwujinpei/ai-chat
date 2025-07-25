<script setup>
import { useHistoryStore } from '@/stores/index.js'

const historyStore = useHistoryStore()

const props = defineProps({
  size: {
    type: [String, Number],
    default: 16,
  },
  opacity: {
    type: Number,
    default: 0.6,
  },
  conversationId: {
    type: String,
    required: true,
  },
  currentTitle: {
    type: String,
    default: '',
  },
})
const emit = defineEmits(['update:title', 'delete'])
const handleMenuClick = (command, conversationId) => {
  if (command === 'rename') {
    showEditDialog(conversationId)
  }
  if (command === 'delete') {
    handleDelete(conversationId)
  }
}
const showEditDialog = async (conversationId) => {
  const { value } = await ElMessageBox.prompt('请输入新标题', '编辑标题', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    inputPattern: /\S+/,
    inputErrorMessage: '标题不能为空',
    inputValue: props.currentTitle,
  })
  await historyStore.updateChatTitle(conversationId, value)
  ElMessage.success(`标题已更新为：${value}`)
}
const handleDelete = (conversationId) => {
  historyStore.deleteChat(conversationId)
}
</script>

<template>
  <el-dropdown
    class="dropdown-menu"
    trigger="click"
    @click.stop
    @command="(command) => handleMenuClick(command, conversationId)"
  >
    <span class="el-dropdown-link">
      <i class="icon-more"></i>
    </span>
    <template #dropdown>
      <el-dropdown-menu>
        <!--        <el-dropdown-item command="multi-select">多选</el-dropdown-item>-->
        <el-dropdown-item command="rename">编辑名称</el-dropdown-item>
        <el-dropdown-item command="pin">置顶</el-dropdown-item>
        <el-dropdown-item command="delete" divided>删除</el-dropdown-item>
      </el-dropdown-menu>
    </template>
  </el-dropdown>
</template>

<style scoped>
.dropdown-menu {
  position: absolute;
  right: 12px;
  top: 50%;
  transform: translateY(-50%);

  .el-dropdown-link {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 24px;
    height: 24px;
    border-radius: 4px;
    cursor: pointer;

    &:hover {
      background: rgba(0, 0, 0, 0.05);
    }
  }

  .icon-more {
    display: inline-block;
    width: 16px;
    height: 16px;
    background: #ffffff; /* 更换为白色，提高对比度 */
    mask-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24'%3E%3Ccircle cx='12' cy='6' r='2'/%3E%3Ccircle cx='12' cy='12' r='2'/%3E%3Ccircle cx='12' cy='18' r='2'/%3E%3C/svg%3E");
    mask-repeat: no-repeat;
    mask-position: center;
    opacity: 0.9;
  }
}
</style>

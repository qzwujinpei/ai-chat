<script setup>
import side from './side/index.vue'
import top from './top/index.vue'
import InputContent from './footer/index.vue'
import { useAiStore } from '@/stores'
import { useRoute } from 'vue-router'

const route = useRoute()
const { chatId } = storeToRefs(useAiStore())
</script>

<template>
  <div class="common-layout">
    <el-container>
      <el-aside>
        <side />
      </el-aside>
      <el-container>
        <el-header><top /></el-header>
        <el-main>
          <router-view v-slot="{ Component }">
            <keep-alive>
              <component :is="Component" />
            </keep-alive>
          </router-view>
        </el-main>
        <el-footer v-if="route.params.chatId">
          <input-content v-show="chatId" />
        </el-footer>
      </el-container>
    </el-container>
  </div>
</template>

<style lang="scss" scoped>
.common-layout {
  .el-container {
    .el-aside {
      max-height: 100vh;
      width: $sidebar-width;
      background-color: $sidebar-bg-color;
    }

    /* 新增布局逻辑 */
    > .el-container {
      flex: 1;
      display: flex;
      flex-direction: column;

      .el-main {
        flex: 1;
      }
    }
  }
}

:deep(.el-header),
:deep(.el-footer) {
  padding: 0;
}
</style>

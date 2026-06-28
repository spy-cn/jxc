<template>
  <el-container style="height:100vh">
    <el-aside :width="isCollapse ? '64px' : '220px'" style="background:#304156">
      <div class="logo">{{ isCollapse ? 'JXC' : '进销存管理系统' }}</div>
      <el-menu :default-active="$route.path" :collapse="isCollapse" background-color="#304156"
               text-color="#bfcbd9" active-text-color="#409EFF" router unique-opened>
        <side-menu :menus="menus" />
      </el-menu>
    </el-aside>
    <el-container>
      <el-header style="background:#fff;border-bottom:1px solid #eee;display:flex;align-items:center;justify-content:space-between">
        <div style="display:flex;align-items:center">
          <el-icon style="font-size:20px;cursor:pointer" @click="isCollapse=!isCollapse"><Fold v-if="!isCollapse" /><Expand v-else /></el-icon>
          <span style="margin-left:12px">欢迎您，{{ userStore.userInfo.trueName || userStore.userInfo.userName }}</span>
        </div>
        <el-dropdown @command="handleCommand">
          <span style="cursor:pointer">{{ userStore.userInfo.userName }} <el-icon><ArrowDown /></el-icon></span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item command="logout">安全退出</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </el-header>
      <el-main style="background:#f0f2f5">
        <router-view v-slot="{ Component }">
          <component :is="Component" />
        </router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '../store/user'
import { Fold, Expand, ArrowDown } from '@element-plus/icons-vue'
import SideMenu from './SideMenu.vue'

const router = useRouter()
const userStore = useUserStore()
const isCollapse = ref(false)
const menus = ref(userStore.menus)

const handleCommand = (cmd) => {
  if (cmd === 'logout') {
    userStore.logout()
    router.push('/login')
  }
}
</script>

<style scoped>
.logo {
  height: 60px;
  line-height: 60px;
  text-align: center;
  color: #fff;
  font-size: 16px;
  font-weight: bold;
  background: #2b3a4d;
  overflow: hidden;
  white-space: nowrap;
}
.el-menu { border-right: none; }
</style>

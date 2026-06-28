<template>
  <template v-for="m in menus" :key="m.menuId">
    <!-- 有子节点：作为可展开的目录 -->
    <el-sub-menu v-if="m.children && m.children.length" :index="String(m.menuId)">
      <template #title>
        <el-icon><Folder /></el-icon>
        <span>{{ m.menuName }}</span>
      </template>
      <side-menu :menus="m.children" />
    </el-sub-menu>
    <!-- 叶子节点 -->
    <el-menu-item v-else :index="resolvePath(m.menuUrl)" @click="handleLeafClick(m)">
      <el-icon><Document /></el-icon>
      <span>{{ m.menuName }}</span>
    </el-menu-item>
  </template>
</template>

<script setup>
import { Folder, Document } from '@element-plus/icons-vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '../store/user'
import { ElMessageBox } from 'element-plus'

const props = defineProps({ menus: { type: Array, default: () => [] } })
const router = useRouter()
const userStore = useUserStore()

const urlMap = {
  '/common/stockSearch.html': '/stock/search',
  '/stock/damageOverflowSearch.html': '/basicData/stock',
  '/purchase/purchaseSearch.html': '/purchase/search',
  '/sale/saleSearch.html': '/sale/search'
}

const resolvePath = (menuUrl) => {
  if (!menuUrl) return ''
  if (urlMap[menuUrl]) return urlMap[menuUrl]
  return '/' + menuUrl.replace(/^\//, '').replace(/\.html$/, '')
}

// 处理无 URL 的特殊叶子（修改密码 / 安全退出）
const handleLeafClick = (m) => {
  if (m.menuUrl) return // 正常有 url 的交给 el-menu router 跳转
  if (m.menuName && m.menuName.indexOf('退出') >= 0) {
    ElMessageBox.confirm('确定要退出系统吗？', '提示', { type: 'warning' })
      .then(() => { userStore.logout(); router.push('/login') })
      .catch(() => {})
  } else if (m.menuName && m.menuName.indexOf('密码') >= 0) {
    // 修改密码：简单弹窗
    ElMessageBox.prompt('请输入新密码', '修改密码', { confirmButtonText: '确定', cancelButtonText: '取消' })
      .then(async ({ value }) => {
        const { updatePassword } = await import('../api')
        await updatePassword({ userId: userStore.userInfo.userId, password: value })
        ElMessageBox.alert('修改成功', '提示')
      })
      .catch(() => {})
  }
}
</script>

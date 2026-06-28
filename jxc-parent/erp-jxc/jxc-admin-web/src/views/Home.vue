<template>
  <div class="app-container">
    <el-row :gutter="16">
      <el-col :span="6" v-for="card in cards" :key="card.title">
        <el-card shadow="hover">
          <div style="display:flex;align-items:center;justify-content:space-between">
            <div>
              <div style="color:#909399;font-size:14px">{{ card.title }}</div>
              <div style="font-size:28px;font-weight:bold;margin-top:8px">{{ card.value }}</div>
            </div>
            <el-icon :size="40" :color="card.color"><component :is="card.icon" /></el-icon>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-card style="margin-top:16px">
      <template #header>欢迎使用进销存管理系统</template>
      <p>本系统涵盖进货管理、销售管理、库存管理、统计报表、基础资料、系统管理等核心模块。</p>
      <p>技术栈：后端 Spring Boot + MyBatis-Plus + MySQL + JWT；前端 Vue 3 + Element Plus + Vite + Pinia + ECharts。</p>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { alarmGoods, listGoods } from '../api'

const cards = ref([
  { title: '库存报警', value: '-', icon: 'Warning', color: '#E6A23C' },
  { title: '商品总数', value: '-', icon: 'Goods', color: '#409EFF' },
  { title: '在售状态', value: '正常运行', icon: 'CircleCheck', color: '#67C23A' },
  { title: '系统版本', value: 'v1.0', icon: 'InfoFilled', color: '#909399' }
])

onMounted(async () => {
  try {
    const alarm = await alarmGoods()
    cards.value[0].value = alarm.data.length
    const goods = await listGoods({})
    cards.value[1].value = goods.data.length
  } catch (e) {}
})
</script>

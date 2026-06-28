<template>
  <div class="app-container">
    <el-card>
      <template #header>库存报警商品（库存数量 ≤ 库存下限）</template>
      <el-table :data="tableData" border stripe v-loading="loading">
        <el-table-column prop="goodsCode" label="编码" width="120" />
        <el-table-column prop="goodsName" label="名称" />
        <el-table-column prop="goodsTypeName" label="类别" width="100" />
        <el-table-column prop="inventoryQuantity" label="当前库存" width="100" />
        <el-table-column prop="minNum" label="库存下限" width="100" />
        <el-table-column label="缺口" width="100"><template #default="{ row }"><span style="color:#F56C6C">{{ row.minNum - row.inventoryQuantity }}</span></template></el-table-column>
      </el-table>
    </el-card>
  </div>
</template>
<script setup>
import { ref, onMounted } from 'vue'
import { alarmGoods } from '../../api'
const loading = ref(false); const tableData = ref([])
const loadData = async () => { loading.value = true; try { const res = await alarmGoods(); tableData.value = res.data } finally { loading.value = false } }
onMounted(loadData)
</script>

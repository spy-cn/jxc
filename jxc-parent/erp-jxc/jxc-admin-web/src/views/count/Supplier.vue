<template>
  <div class="app-container">
    <el-card>
      <div class="search-bar">
        <el-date-picker v-model="dateRange" type="daterange" range-separator="至" start-placeholder="开始" end-placeholder="结束" value-format="YYYY-MM-DD" />
        <el-button type="primary" @click="loadData" style="margin-left:8px">统计</el-button>
      </div>
      <el-table :data="tableData" border stripe v-loading="loading">
        <el-table-column type="index" label="序号" width="60" />
        <el-table-column prop="supplierName" label="供应商" />
        <el-table-column prop="orderCount" label="进货次数" width="100" />
        <el-table-column prop="totalAmount" label="总金额" width="120" />
      </el-table>
    </el-card>
  </div>
</template>
<script setup>
import { ref, onMounted, watch } from 'vue'
import { countSupplier } from '../../api'
const loading = ref(false); const tableData = ref([]); const dateRange = ref([]); const params = ref({})
watch(dateRange, (v) => { params.value.startDate = v && v[0]; params.value.endDate = v && v[1] })
const loadData = async () => { loading.value = true; try { const res = await countSupplier(params.value); tableData.value = res.data } finally { loading.value = false } }
onMounted(loadData)
</script>

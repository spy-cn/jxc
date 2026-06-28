<template>
  <div class="app-container">
    <el-card>
      <div class="search-bar">
        <el-input v-model="query.logType" placeholder="日志类型" clearable style="width:160px" />
        <el-date-picker v-model="dateRange" type="daterange" range-separator="至" start-placeholder="开始" end-placeholder="结束" value-format="YYYY-MM-DD" style="margin-left:8px" />
        <el-button type="primary" @click="loadData" style="margin-left:8px">查询</el-button>
      </div>
      <el-table :data="tableData" border stripe v-loading="loading">
        <el-table-column type="index" label="序号" width="60" />
        <el-table-column prop="logType" label="类型" width="120" />
        <el-table-column prop="content" label="内容" />
        <el-table-column prop="userName" label="操作人" width="100" />
        <el-table-column prop="logDate" label="时间" width="180" />
      </el-table>
    </el-card>
  </div>
</template>
<script setup>
import { ref, onMounted, watch } from 'vue'
import { listLog } from '../../api'
const loading = ref(false); const tableData = ref([]); const query = ref({}); const dateRange = ref([])
watch(dateRange, (v) => { query.value.startDate = v && v[0]; query.value.endDate = v && v[1] })
const loadData = async () => { loading.value = true; try { const res = await listLog(query.value); tableData.value = res.data } finally { loading.value = false } }
onMounted(loadData)
</script>

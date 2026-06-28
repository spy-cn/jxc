<template>
  <div class="app-container">
    <el-card>
      <div class="search-bar">
        <el-date-picker v-model="dateRange" type="daterange" range-separator="至" start-placeholder="开始" end-placeholder="结束" value-format="YYYY-MM-DD" />
        <el-button type="primary" @click="loadData" style="margin-left:8px">统计</el-button>
      </div>
      <div ref="chartRef" style="height:320px;margin:12px 0"></div>
      <el-table :data="tableData" border stripe v-loading="loading">
        <el-table-column type="index" label="序号" width="60" />
        <el-table-column prop="goodsName" label="商品名称" />
        <el-table-column prop="totalNum" label="销售数量" width="100" />
        <el-table-column prop="totalAmount" label="销售金额" width="120" />
      </el-table>
    </el-card>
  </div>
</template>
<script setup>
import { ref, onMounted, watch } from 'vue'
import * as echarts from 'echarts'
import { countSale } from '../../api'
const loading = ref(false); const tableData = ref([]); const dateRange = ref([]); const params = ref({}); const chartRef = ref(null)
let chart
watch(dateRange, (v) => { params.value.startDate = v && v[0]; params.value.endDate = v && v[1] })
const loadData = async () => {
  loading.value = true
  try { const res = await countSale(params.value); tableData.value = res.data; renderChart() } finally { loading.value = false }
}
const renderChart = () => {
  if (!chartRef.value) return
  if (!chart) chart = echarts.init(chartRef.value)
  const top = tableData.value.slice(0, 10)
  chart.setOption({ tooltip: { trigger: 'axis' }, xAxis: { type: 'category', data: top.map(d => d.goodsName), axisLabel: { rotate: 30 } }, yAxis: { type: 'value' }, series: [{ name: '销售金额', type: 'bar', data: top.map(d => d.totalAmount), itemStyle: { color: '#67C23A' } }] })
}
onMounted(async () => { await loadData(); window.addEventListener('resize', () => chart && chart.resize()) })
</script>

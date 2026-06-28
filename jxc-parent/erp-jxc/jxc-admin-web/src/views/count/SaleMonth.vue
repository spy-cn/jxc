<template>
  <div class="app-container">
    <el-card>
      <div class="search-bar">
        <el-date-picker v-model="dateRange" type="daterange" range-separator="至" start-placeholder="开始" end-placeholder="结束" value-format="YYYY-MM-DD" />
        <el-button type="primary" @click="loadData" style="margin-left:8px">统计</el-button>
      </div>
      <div ref="chartRef" style="height:400px;margin:12px 0"></div>
    </el-card>
  </div>
</template>
<script setup>
import { ref, onMounted, watch } from 'vue'
import * as echarts from 'echarts'
import { countSaleMonth } from '../../api'
const dateRange = ref([]); const params = ref({}); const chartRef = ref(null); let chart
watch(dateRange, (v) => { params.value.startDate = v && v[0]; params.value.endDate = v && v[1] })
const loadData = async () => {
  const res = await countSaleMonth(params.value)
  const data = res.data
  if (!chartRef.value) return
  if (!chart) chart = echarts.init(chartRef.value)
  chart.setOption({ title: { text: '按月销售额统计' }, tooltip: { trigger: 'axis' }, xAxis: { type: 'category', data: data.map(d => d.saleMonth) }, yAxis: { type: 'value' }, series: [{ name: '销售额', type: 'bar', data: data.map(d => d.totalAmount), itemStyle: { color: '#E6A23C' } }] })
}
onMounted(async () => { await loadData(); window.addEventListener('resize', () => chart && chart.resize()) })
</script>

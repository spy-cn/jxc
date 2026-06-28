<template>
  <div class="app-container">
    <el-card>
      <div class="search-bar">
        <el-select v-model="query.customerId" placeholder="客户" clearable style="width:180px" filterable>
          <el-option v-for="c in customers" :key="c.customerId" :label="c.customerName" :value="c.customerId" />
        </el-select>
        <el-select v-model="query.state" placeholder="状态" clearable style="width:120px;margin-left:8px">
          <el-option label="已收" :value="1" /><el-option label="未收" :value="2" />
        </el-select>
        <el-date-picker v-model="dateRange" type="daterange" range-separator="至" start-placeholder="开始" end-placeholder="结束" value-format="YYYY-MM-DD" style="margin-left:8px" />
        <el-button type="primary" :icon="Search" @click="loadData" style="margin-left:8px">查询</el-button>
      </div>
      <el-table :data="tableData" border stripe v-loading="loading">
        <el-table-column prop="returnNumber" label="单号" width="160" />
        <el-table-column prop="customerName" label="客户" />
        <el-table-column prop="amountPayable" label="金额" width="100" />
        <el-table-column prop="userName" label="操作员" width="100" />
        <el-table-column prop="returnDate" label="日期" width="120" />
        <el-table-column label="操作" width="80"><template #default="{ row }"><el-button type="primary" link @click="showDetail(row)">明细</el-button></template></el-table-column>
      </el-table>
    </el-card>
    <el-dialog v-model="detailVisible" title="单据明细" width="700px">
      <el-table :data="detail.goodsList" border>
        <el-table-column prop="goodsName" label="名称" /><el-table-column prop="goodsNum" label="数量" width="80" />
        <el-table-column prop="price" label="单价" width="90" /><el-table-column prop="total" label="金额" width="100" />
      </el-table>
    </el-dialog>
  </div>
</template>
<script setup>
import { ref, onMounted, watch } from 'vue'
import { Search } from '@element-plus/icons-vue'
import { listCustomer, listCustomerReturn, detailCustomerReturn } from '../../api'
const loading = ref(false); const tableData = ref([]); const customers = ref([])
const query = ref({}); const dateRange = ref([]); const detailVisible = ref(false); const detail = ref({})
watch(dateRange, (v) => { query.value.startDate = v && v[0]; query.value.endDate = v && v[1] })
const loadData = async () => { loading.value = true; try { const res = await listCustomerReturn(query.value); tableData.value = res.data } finally { loading.value = false } }
const showDetail = async (row) => { const res = await detailCustomerReturn(row.customerReturnListId); detail.value = res.data; detailVisible.value = true }
onMounted(async () => { const res = await listCustomer(); customers.value = res.data; loadData() })
</script>

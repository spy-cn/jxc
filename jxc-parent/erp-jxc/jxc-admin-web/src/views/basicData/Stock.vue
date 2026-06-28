<template>
  <div class="app-container">
    <el-tabs v-model="activeTab">
      <el-tab-pane label="报损记录" name="damage">
        <el-date-picker v-model="dateRange" type="daterange" range-separator="至" start-placeholder="开始" end-placeholder="结束" value-format="YYYY-MM-DD" />
        <el-button type="primary" @click="loadData" style="margin-left:8px">查询</el-button>
        <el-table :data="damageList" border style="margin-top:12px">
          <el-table-column prop="damageNumber" label="单号" width="160" />
          <el-table-column prop="userName" label="操作员" width="100" />
          <el-table-column prop="damageDate" label="日期" width="120" />
          <el-table-column prop="remarks" label="备注" />
          <el-table-column label="操作" width="80"><template #default="{ row }"><el-button link type="primary" @click="showDamage(row)">明细</el-button></template></el-table-column>
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="报溢记录" name="overflow">
        <el-table :data="overflowList" border>
          <el-table-column prop="overflowNumber" label="单号" width="160" />
          <el-table-column prop="userName" label="操作员" width="100" />
          <el-table-column prop="overflowDate" label="日期" width="120" />
          <el-table-column prop="remarks" label="备注" />
          <el-table-column label="操作" width="80"><template #default="{ row }"><el-button link type="primary" @click="showOverflow(row)">明细</el-button></template></el-table-column>
        </el-table>
      </el-tab-pane>
    </el-tabs>
    <el-dialog v-model="detailVisible" title="明细" width="700px">
      <el-table :data="detail.goodsList" border>
        <el-table-column prop="goodsName" label="名称" /><el-table-column prop="goodsNum" label="数量" width="80" /><el-table-column prop="goodsUnit" label="单位" width="80" />
      </el-table>
    </el-dialog>
  </div>
</template>
<script setup>
import { ref, onMounted, watch } from 'vue'
import { listDamage, detailDamage, listOverflow, detailOverflow } from '../../api'
const activeTab = ref('damage'); const damageList = ref([]); const overflowList = ref([])
const dateRange = ref([]); const detailVisible = ref(false); const detail = ref({})
const query = ref({})
watch(dateRange, (v) => { query.value.startDate = v && v[0]; query.value.endDate = v && v[1] })
const loadData = async () => { const d = await listDamage(query.value); damageList.value = d.data; const o = await listOverflow({}); overflowList.value = o.data }
const showDamage = async (row) => { const r = await detailDamage(row.damageListId); detail.value = r.data; detailVisible.value = true }
const showOverflow = async (row) => { const r = await detailOverflow(row.overflowListId); detail.value = r.data; detailVisible.value = true }
onMounted(loadData)
</script>

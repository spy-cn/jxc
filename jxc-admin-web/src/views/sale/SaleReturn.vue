<template>
  <div class="app-container">
    <el-card>
      <div class="search-bar">
        <el-select v-model="form.customerId" placeholder="选择客户" style="width:220px" filterable>
          <el-option v-for="c in customers" :key="c.customerId" :label="c.customerName" :value="c.customerId" />
        </el-select>
        <el-input v-model="form.remarks" placeholder="备注" style="width:260px;margin-left:8px" />
        <el-button type="primary" :icon="Check" @click="handleSave" style="margin-left:8px" :disabled="!form.customerId || tableData.length===0">保存退货</el-button>
      </div>
      <el-button type="success" :icon="Plus" @click="dialogVisible=true" style="margin-bottom:12px">选择商品</el-button>
      <el-table :data="tableData" border stripe show-summary :summary-method="getSummary">
        <el-table-column type="index" label="序号" width="60" />
        <el-table-column prop="goodsCode" label="编码" width="120" />
        <el-table-column prop="goodsName" label="名称" />
        <el-table-column prop="goodsModel" label="型号" width="100" />
        <el-table-column label="数量" width="120"><template #default="{ row }"><el-input-number v-model="row.goodsNum" :min="1" size="small" @change="calc(row)" /></template></el-table-column>
        <el-table-column prop="goodsUnit" label="单位" width="70" />
        <el-table-column label="单价" width="120"><template #default="{ row }"><el-input-number v-model="row.price" :min="0" :precision="2" size="small" @change="calc(row)" /></template></el-table-column>
        <el-table-column prop="total" label="金额" width="100" />
        <el-table-column label="操作" width="80"><template #default="{ $index }"><el-button type="danger" link @click="tableData.splice($index,1)">移除</el-button></template></el-table-column>
      </el-table>
    </el-card>
    <el-dialog v-model="dialogVisible" title="选择商品" width="700px">
      <el-input v-model="searchKey" placeholder="编码/名称" style="width:200px;margin-bottom:8px" @keyup.enter="loadGoods" />
      <el-table :data="goodsList" border @selection-change="onSelect" height="400">
        <el-table-column type="selection" width="50" />
        <el-table-column prop="goodsCode" label="编码" width="120" />
        <el-table-column prop="goodsName" label="名称" />
        <el-table-column prop="inventoryQuantity" label="库存" width="80" />
        <el-table-column prop="sellingPrice" label="售价" width="90" />
      </el-table>
      <template #footer><el-button @click="dialogVisible=false">取消</el-button><el-button type="primary" @click="confirmSelect">添加</el-button></template>
    </el-dialog>
  </div>
</template>
<script setup>
import { ref, onMounted } from 'vue'
import { Check, Plus } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import { listCustomer, listGoods, saveCustomerReturn } from '../../api'
const customers = ref([]); const goodsList = ref([]); const tableData = ref([]); const selected = ref([])
const dialogVisible = ref(false); const searchKey = ref(''); const form = ref({})
const calc = (row) => { row.total = (row.price * row.goodsNum).toFixed(2) * 1 }
const getSummary = ({ columns, data }) => columns.map((col, i) => i === 2 ? '合计' : col.property === 'total' ? data.reduce((s, r) => s + (r.total || 0), 0).toFixed(2) : col.property === 'goodsNum' ? data.reduce((s, r) => s + (r.goodsNum || 0), 0) : '')
const loadGoods = async () => { const res = await listGoods({ codeOrName: searchKey.value }); goodsList.value = res.data }
const onSelect = (sel) => { selected.value = sel }
const confirmSelect = () => { selected.value.forEach(g => { if (!tableData.value.find(t => t.goodsId === g.goodsId)) tableData.value.push({ goodsId: g.goodsId, goodsCode: g.goodsCode, goodsName: g.goodsName, goodsModel: g.goodsModel, goodsUnit: g.goodsUnit, goodsTypeId: g.goodsTypeId, goodsNum: 1, price: g.sellingPrice || 0, total: g.sellingPrice || 0 }) }); dialogVisible.value = false }
const handleSave = async () => { await saveCustomerReturn({ customerId: form.value.customerId, remarks: form.value.remarks, goodsList: tableData.value }); ElMessage.success('退货成功'); tableData.value = []; form.value = { remarks: '' } }
onMounted(async () => { const res = await listCustomer(); customers.value = res.data; loadGoods() })
</script>

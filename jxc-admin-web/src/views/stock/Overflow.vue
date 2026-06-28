<template>
  <div class="app-container">
    <el-card>
      <div class="search-bar">
        <el-input v-model="form.remarks" placeholder="备注" style="width:300px" />
        <el-button type="primary" :icon="Check" @click="handleSave" style="margin-left:8px" :disabled="tableData.length===0">保存报溢</el-button>
      </div>
      <el-button type="success" :icon="Plus" @click="dialogVisible=true" style="margin-bottom:12px">选择商品</el-button>
      <el-table :data="tableData" border stripe>
        <el-table-column type="index" label="序号" width="60" />
        <el-table-column prop="goodsCode" label="编码" width="120" />
        <el-table-column prop="goodsName" label="名称" />
        <el-table-column label="报溢数量" width="140"><template #default="{ row }"><el-input-number v-model="row.goodsNum" :min="1" size="small" /></template></el-table-column>
        <el-table-column prop="goodsUnit" label="单位" width="70" />
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
      </el-table>
      <template #footer><el-button @click="dialogVisible=false">取消</el-button><el-button type="primary" @click="confirmSelect">添加</el-button></template>
    </el-dialog>
  </div>
</template>
<script setup>
import { ref, onMounted } from 'vue'
import { Check, Plus } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import { listGoods, saveOverflow } from '../../api'
const goodsList = ref([]); const tableData = ref([]); const selected = ref([])
const dialogVisible = ref(false); const searchKey = ref(''); const form = ref({})
const loadGoods = async () => { const res = await listGoods({ codeOrName: searchKey.value }); goodsList.value = res.data }
const onSelect = (sel) => { selected.value = sel }
const confirmSelect = () => { selected.value.forEach(g => { if (!tableData.value.find(t => t.goodsId === g.goodsId)) tableData.value.push({ goodsId: g.goodsId, goodsCode: g.goodsCode, goodsName: g.goodsName, goodsModel: g.goodsModel, goodsUnit: g.goodsUnit, goodsTypeId: g.goodsTypeId, goodsNum: 1, price: g.purchasingPrice || 0 }) }); dialogVisible.value = false }
const handleSave = async () => { await saveOverflow({ remarks: form.value.remarks, goodsList: tableData.value }); ElMessage.success('报溢成功'); tableData.value = []; form.value = {} }
onMounted(loadGoods)
</script>

<template>
  <div class="app-container">
    <el-row :gutter="12">
      <el-col :span="5">
        <el-card>
          <template #header>商品类别</template>
          <el-tree :data="typeTree" :props="{ label: 'goodsTypeName', children: 'children' }"
                   node-key="goodsTypeId" highlight-current @node-click="handleNodeClick" default-expand-all />
        </el-card>
      </el-col>
      <el-col :span="19">
        <el-card>
          <div class="search-bar">
            <el-input v-model="query.codeOrName" placeholder="编码/名称" style="width:200px" clearable />
            <el-button type="primary" :icon="Search" @click="loadGoods" style="margin-left:8px">查询</el-button>
            <el-button type="primary" :icon="Plus" @click="handleAdd" style="margin-left:8px">新增商品</el-button>
          </div>
          <el-table :data="tableData" border stripe v-loading="loading">
            <el-table-column prop="goodsCode" label="编码" width="120" />
            <el-table-column prop="goodsName" label="名称" />
            <el-table-column prop="goodsTypeName" label="类别" width="100" />
            <el-table-column prop="goodsModel" label="型号" width="100" />
            <el-table-column prop="inventoryQuantity" label="库存" width="80" />
            <el-table-column prop="minNum" label="下限" width="80" />
            <el-table-column prop="goodsUnit" label="单位" width="70" />
            <el-table-column prop="purchasingPrice" label="采购价" width="90" />
            <el-table-column prop="sellingPrice" label="售价" width="90" />
            <el-table-column label="操作" width="150" fixed="right">
              <template #default="{ row }">
                <el-button type="primary" link @click="handleEdit(row)">编辑</el-button>
                <el-popconfirm title="确定删除吗？" @confirm="handleDelete(row.goodsId)">
                  <template #reference><el-button type="danger" link>删除</el-button></template>
                </el-popconfirm>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
    </el-row>

    <el-dialog v-model="dialogVisible" :title="form.goodsId ? '编辑商品' : '新增商品'" width="600px">
      <el-form :model="form" label-width="100px">
        <el-row :gutter="10">
          <el-col :span="12"><el-form-item label="编码"><el-input v-model="form.goodsCode" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="名称"><el-input v-model="form.goodsName" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="型号"><el-input v-model="form.goodsModel" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="单位">
            <el-select v-model="form.goodsUnit" style="width:100%">
              <el-option v-for="u in units" :key="u.unitId" :label="u.unitName" :value="u.unitName" />
            </el-select>
          </el-form-item></el-col>
          <el-col :span="12"><el-form-item label="类别">
            <el-tree-select v-model="form.goodsTypeId" :data="typeOptions" :props="{ label:'goodsTypeName', value:'goodsTypeId', children:'children' }" check-strictly style="width:100%" />
          </el-form-item></el-col>
          <el-col :span="12"><el-form-item label="生产厂商"><el-input v-model="form.goodsProducer" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="库存数量"><el-input-number v-model="form.inventoryQuantity" :min="0" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="库存下限"><el-input-number v-model="form.minNum" :min="0" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="采购价"><el-input-number v-model="form.purchasingPrice" :min="0" :precision="2" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="售价"><el-input-number v-model="form.sellingPrice" :min="0" :precision="2" /></el-form-item></el-col>
        </el-row>
        <el-form-item label="备注"><el-input v-model="form.remarks" type="textarea" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible=false">取消</el-button>
        <el-button type="primary" @click="handleSave">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>
<script setup>
import { ref, onMounted, computed } from 'vue'
import { Plus, Search } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import { listGoods, saveGoods, delGoods, goodsTypeTree, listUnit } from '../../api'

const loading = ref(false)
const tableData = ref([])
const typeTree = ref([])
const units = ref([])
const query = ref({})
const dialogVisible = ref(false)
const form = ref({})

const flatten = (nodes, acc = []) => {
  nodes.forEach(n => { acc.push(n); if (n.children) flatten(n.children, acc) })
  return acc
}
const typeOptions = computed(() => typeTree.value)

const loadType = async () => { const res = await goodsTypeTree(); typeTree.value = res.data }
const loadGoods = async () => { loading.value = true; try { const res = await listGoods(query.value); tableData.value = res.data } finally { loading.value = false } }
const handleNodeClick = (node) => { query.value.typeId = node.goodsTypeId; loadGoods() }
const handleAdd = () => { form.value = { inventoryQuantity: 0, minNum: 0 }; dialogVisible.value = true }
const handleEdit = (row) => { form.value = { ...row }; dialogVisible.value = true }
const handleSave = async () => {
  if (!form.value.goodsCode || !form.value.goodsName) { ElMessage.warning('请填写编码和名称'); return }
  await saveGoods(form.value); ElMessage.success('保存成功'); dialogVisible.value = false; loadGoods()
}
const handleDelete = async (id) => { await delGoods(id); ElMessage.success('删除成功'); loadGoods() }
onMounted(async () => {
  await loadType()
  const u = await listUnit(); units.value = u.data
  loadGoods()
})
</script>

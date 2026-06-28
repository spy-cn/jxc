<template>
  <div class="app-container">
    <el-card>
      <div class="search-bar">
        <el-select v-model="query.typeId" placeholder="商品类别" clearable style="width:180px">
          <el-option v-for="t in flatTypes" :key="t.goodsTypeId" :label="t.goodsTypeName" :value="t.goodsTypeId" />
        </el-select>
        <el-input v-model="query.codeOrName" placeholder="编码/名称" clearable style="width:200px;margin-left:8px" />
        <el-button type="primary" :icon="Search" @click="loadData" style="margin-left:8px">查询</el-button>
      </div>
      <el-table :data="tableData" border stripe v-loading="loading">
        <el-table-column prop="goodsCode" label="编码" width="120" />
        <el-table-column prop="goodsName" label="名称" />
        <el-table-column prop="goodsTypeName" label="类别" width="100" />
        <el-table-column prop="goodsModel" label="型号" width="100" />
        <el-table-column prop="inventoryQuantity" label="库存数量" width="100" />
        <el-table-column prop="minNum" label="库存下限" width="100" />
        <el-table-column prop="goodsUnit" label="单位" width="80" />
        <el-table-column label="状态" width="100"><template #default="{ row }"><el-tag :type="row.inventoryQuantity<=row.minNum?'danger':'success'">{{ row.inventoryQuantity<=row.minNum?'库存报警':'正常' }}</el-tag></template></el-table-column>
      </el-table>
    </el-card>
  </div>
</template>
<script setup>
import { ref, onMounted, computed } from 'vue'
import { Search } from '@element-plus/icons-vue'
import { listGoods, goodsTypeTree } from '../../api'
const loading = ref(false); const tableData = ref([]); const query = ref({}); const typeTree = ref([])
const flatTypes = computed(() => { const acc = []; const f = (n) => { n.forEach(x => { acc.push(x); if (x.children) f(x.children) }) }; f(typeTree.value); return acc })
const loadData = async () => { loading.value = true; try { const res = await listGoods(query.value); tableData.value = res.data } finally { loading.value = false } }
onMounted(async () => { try { const r = await goodsTypeTree(); typeTree.value = r.data } catch(e){}; loadData() })
</script>

<template>
  <div class="app-container">
    <el-card>
      <div class="table-toolbar"><el-button type="primary" :icon="Plus" @click="handleAdd">新增客户</el-button></div>
      <el-table :data="tableData" border stripe v-loading="loading">
        <el-table-column type="index" label="序号" width="60" />
        <el-table-column prop="customerName" label="客户名称" />
        <el-table-column prop="contacts" label="联系人" width="100" />
        <el-table-column prop="phoneNumber" label="联系电话" width="140" />
        <el-table-column prop="address" label="地址" show-overflow-tooltip />
        <el-table-column prop="remarks" label="备注" show-overflow-tooltip />
        <el-table-column label="操作" width="150" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" link @click="handleEdit(row)">编辑</el-button>
            <el-popconfirm title="确定删除吗？" @confirm="handleDelete(row.customerId)">
              <template #reference><el-button type="danger" link>删除</el-button></template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <el-dialog v-model="dialogVisible" :title="form.customerId ? '编辑客户' : '新增客户'" width="500px">
      <el-form :model="form" label-width="90px">
        <el-form-item label="客户名称"><el-input v-model="form.customerName" /></el-form-item>
        <el-form-item label="联系人"><el-input v-model="form.contacts" /></el-form-item>
        <el-form-item label="联系电话"><el-input v-model="form.phoneNumber" /></el-form-item>
        <el-form-item label="地址"><el-input v-model="form.address" /></el-form-item>
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
import { ref, onMounted } from 'vue'
import { Plus } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import { listCustomer, saveCustomer, delCustomer } from '../../api'
const loading = ref(false)
const tableData = ref([])
const dialogVisible = ref(false)
const form = ref({})
const loadData = async () => { loading.value = true; try { const res = await listCustomer(); tableData.value = res.data } finally { loading.value = false } }
const handleAdd = () => { form.value = {}; dialogVisible.value = true }
const handleEdit = (row) => { form.value = { ...row }; dialogVisible.value = true }
const handleSave = async () => {
  if (!form.value.customerName) { ElMessage.warning('请输入名称'); return }
  await saveCustomer(form.value); ElMessage.success('保存成功'); dialogVisible.value = false; loadData()
}
const handleDelete = async (id) => { await delCustomer(id); ElMessage.success('删除成功'); loadData() }
onMounted(loadData)
</script>

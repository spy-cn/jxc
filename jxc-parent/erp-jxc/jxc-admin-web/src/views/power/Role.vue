<template>
  <div class="app-container">
    <el-card>
      <div class="table-toolbar"><el-button type="primary" :icon="Plus" @click="handleAdd">新增角色</el-button></div>
      <el-table :data="tableData" border stripe>
        <el-table-column type="index" label="序号" width="60" />
        <el-table-column prop="roleName" label="角色名称" />
        <el-table-column prop="remarks" label="备注" />
        <el-table-column label="操作" width="150">
          <template #default="{ row }">
            <el-button type="primary" link @click="handleEdit(row)">编辑</el-button>
            <el-popconfirm title="确定删除吗？" @confirm="handleDelete(row.roleId)"><template #reference><el-button type="danger" link>删除</el-button></template></el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <el-dialog v-model="dialogVisible" :title="form.roleId?'编辑角色':'新增角色'" width="500px">
      <el-form :model="form" label-width="90px">
        <el-form-item label="角色名称"><el-input v-model="form.roleName" /></el-form-item>
        <el-form-item label="备注"><el-input v-model="form.remarks" /></el-form-item>
        <el-form-item label="菜单权限">
          <el-tree ref="treeRef" :data="menuTree" :props="{ label:'menuName', children:'children' }" node-key="menuId" show-checkbox :default-checked-keys="form.menuIds||[]" />
        </el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible=false">取消</el-button><el-button type="primary" @click="handleSave">保存</el-button></template>
    </el-dialog>
  </div>
</template>
<script setup>
import { ref, onMounted } from 'vue'
import { Plus } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import { listRole, saveRole, delRole, listMenu } from '../../api'
const tableData = ref([]); const dialogVisible = ref(false); const form = ref({}); const menuTree = ref([]); const treeRef = ref(null)
const buildTree = (list, pid) => list.filter(m => m.pId === pid).map(m => ({ ...m, children: buildTree(list, m.menuId) })).filter(m => m.children && m.children.length || !m.children ? m : m)
const loadData = async () => { const res = await listRole(); tableData.value = res.data }
const handleAdd = () => { form.value = {}; dialogVisible.value = true }
const handleEdit = (row) => { form.value = { ...row }; dialogVisible.value = true }
const handleSave = async () => {
  if (!form.value.roleName) { ElMessage.warning('请输入角色名称'); return }
  form.value.menuIds = treeRef.value.getCheckedKeys().concat(treeRef.value.getHalfCheckedKeys())
  await saveRole(form.value); ElMessage.success('保存成功'); dialogVisible.value = false; loadData()
}
const handleDelete = async (id) => { await delRole(id); ElMessage.success('删除成功'); loadData() }
onMounted(async () => {
  await loadData()
  const m = await listMenu(); menuTree.value = buildTree(m.data, -1)
})
</script>

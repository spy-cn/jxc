<template>
  <div class="app-container">
    <el-card>
      <div class="table-toolbar"><el-button type="primary" :icon="Plus" @click="handleAdd">新增用户</el-button></div>
      <el-table :data="tableData" border stripe>
        <el-table-column type="index" label="序号" width="60" />
        <el-table-column prop="userName" label="用户名" width="120" />
        <el-table-column prop="trueName" label="真实姓名" width="120" />
        <el-table-column prop="remarks" label="备注" />
        <el-table-column label="操作" width="200">
          <template #default="{ row }">
            <el-button type="primary" link @click="handleEdit(row)">编辑</el-button>
            <el-button type="warning" link @click="handlePwd(row)">改密</el-button>
            <el-popconfirm title="确定删除吗？" @confirm="handleDelete(row.userId)"><template #reference><el-button type="danger" link>删除</el-button></template></el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <el-dialog v-model="dialogVisible" :title="form.userId?'编辑用户':'新增用户'" width="500px">
      <el-form :model="form" label-width="90px">
        <el-form-item label="用户名"><el-input v-model="form.userName" :disabled="!!form.userId" /></el-form-item>
        <el-form-item label="真实姓名"><el-input v-model="form.trueName" /></el-form-item>
        <el-form-item v-if="!form.userId" label="密码"><el-input v-model="form.password" /></el-form-item>
        <el-form-item label="角色">
          <el-select v-model="form.roleIds" multiple style="width:100%">
            <el-option v-for="r in roles" :key="r.roleId" :label="r.roleName" :value="r.roleId" />
          </el-select>
        </el-form-item>
        <el-form-item label="备注"><el-input v-model="form.remarks" /></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible=false">取消</el-button><el-button type="primary" @click="handleSave">保存</el-button></template>
    </el-dialog>
    <el-dialog v-model="pwdVisible" title="修改密码" width="400px">
      <el-form label-width="80px"><el-form-item label="新密码"><el-input v-model="pwdForm.password" /></el-form-item></el-form>
      <template #footer><el-button @click="pwdVisible=false">取消</el-button><el-button type="primary" @click="savePwd">确定</el-button></template>
    </el-dialog>
  </div>
</template>
<script setup>
import { ref, onMounted } from 'vue'
import { Plus } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import { listUser, saveUser, delUser, listRole, updatePassword } from '../../api'
const tableData = ref([]); const roles = ref([]); const dialogVisible = ref(false); const form = ref({})
const pwdVisible = ref(false); const pwdForm = ref({})
const loadData = async () => { const res = await listUser({}); tableData.value = res.data }
const handleAdd = () => { form.value = { roleIds: [] }; dialogVisible.value = true }
const handleEdit = (row) => { form.value = { ...row, roleIds: row.roleIds || [] }; dialogVisible.value = true }
const handleSave = async () => {
  if (!form.value.userName) { ElMessage.warning('请输入用户名'); return }
  await saveUser(form.value); ElMessage.success('保存成功'); dialogVisible.value = false; loadData()
}
const handleDelete = async (id) => { await delUser(id); ElMessage.success('删除成功'); loadData() }
const handlePwd = (row) => { pwdForm.value = { userId: row.userId, password: '' }; pwdVisible.value = true }
const savePwd = async () => { await updatePassword(pwdForm.value); ElMessage.success('修改成功'); pwdVisible.value = false }
onMounted(async () => { await loadData(); const r = await listRole(); roles.value = r.data })
</script>

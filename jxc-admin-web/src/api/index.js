import request from '../utils/request'

// 认证
export const login = (data) => request.post('/auth/login', data)

// 基础资料
export const listSupplier = () => request.get('/supplier/list')
export const saveSupplier = (data) => request.post('/supplier', data)
export const delSupplier = (id) => request.delete(`/supplier/${id}`)

export const listCustomer = () => request.get('/customer/list')
export const saveCustomer = (data) => request.post('/customer', data)
export const delCustomer = (id) => request.delete(`/customer/${id}`)

export const listGoods = (params) => request.get('/goods/list', { params })
export const alarmGoods = () => request.get('/goods/alarm')
export const saveGoods = (data) => request.post('/goods', data)
export const delGoods = (id) => request.delete(`/goods/${id}`)

export const goodsTypeTree = () => request.get('/goodsType/tree')
export const saveGoodsType = (data) => request.post('/goodsType', data)
export const delGoodsType = (id) => request.delete(`/goodsType/${id}`)

export const listUnit = () => request.get('/unit/list')
export const saveUnit = (data) => request.post('/unit', data)
export const delUnit = (id) => request.delete(`/unit/${id}`)

// 系统管理
export const listUser = (params) => request.get('/user/list', { params })
export const saveUser = (data) => request.post('/user', data)
export const delUser = (id) => request.delete(`/user/${id}`)
export const updatePassword = (data) => request.put('/user/password', data)

export const listRole = () => request.get('/role/list')
export const saveRole = (data) => request.post('/role', data)
export const delRole = (id) => request.delete(`/role/${id}`)

export const listMenu = () => request.get('/menu/list')

export const listLog = (params) => request.get('/log/list', { params })

// 单据
export const listPurchase = (params) => request.get('/purchase/list', { params })
export const detailPurchase = (id) => request.get(`/purchase/${id}`)
export const savePurchase = (data) => request.post('/purchase', data)
export const settlePurchase = (id) => request.put(`/purchase/settle/${id}`)

export const listReturn = (params) => request.get('/return/list', { params })
export const detailReturn = (id) => request.get(`/return/${id}`)
export const saveReturn = (data) => request.post('/return', data)
export const settleReturn = (id) => request.put(`/return/settle/${id}`)

export const listSale = (params) => request.get('/sale/list', { params })
export const detailSale = (id) => request.get(`/sale/${id}`)
export const saveSale = (data) => request.post('/sale', data)
export const settleSale = (id) => request.put(`/sale/settle/${id}`)

export const listCustomerReturn = (params) => request.get('/customerReturn/list', { params })
export const detailCustomerReturn = (id) => request.get(`/customerReturn/${id}`)
export const saveCustomerReturn = (data) => request.post('/customerReturn', data)

export const listDamage = (params) => request.get('/damage/list', { params })
export const detailDamage = (id) => request.get(`/damage/${id}`)
export const saveDamage = (data) => request.post('/damage', data)

export const listOverflow = (params) => request.get('/overflow/list', { params })
export const detailOverflow = (id) => request.get(`/overflow/${id}`)
export const saveOverflow = (data) => request.post('/overflow', data)

// 统计
export const countPurchase = (params) => request.get('/count/purchase', { params })
export const countSale = (params) => request.get('/count/sale', { params })
export const countSupplier = (params) => request.get('/count/supplier', { params })
export const countCustomer = (params) => request.get('/count/customer', { params })
export const countSaleDay = (params) => request.get('/count/saleDay', { params })
export const countSaleMonth = (params) => request.get('/count/saleMonth', { params })

import { createRouter, createWebHashHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const routes = [
  { path: '/login', component: () => import('../views/login/index.vue') },
  {
    path: '/',
    component: () => import('../layout/index.vue'),
    redirect: '/home',
    children: [
      { path: 'home', component: () => import('../views/Home.vue') },
      { path: 'purchase/purchase', component: () => import('../views/purchase/Purchase.vue') },
      { path: 'purchase/return', component: () => import('../views/purchase/Return.vue') },
      { path: 'purchase/search', component: () => import('../views/purchase/PurchaseSearch.vue') },
      { path: 'purchase/returnSearch', component: () => import('../views/purchase/ReturnSearch.vue') },
      { path: 'sale/saleout', component: () => import('../views/sale/SaleOut.vue') },
      { path: 'sale/salereturn', component: () => import('../views/sale/SaleReturn.vue') },
      { path: 'sale/search', component: () => import('../views/sale/SaleSearch.vue') },
      { path: 'sale/returnSearch', component: () => import('../views/sale/ReturnSearch.vue') },
      { path: 'stock/damage', component: () => import('../views/stock/Damage.vue') },
      { path: 'stock/overflow', component: () => import('../views/stock/Overflow.vue') },
      { path: 'stock/alarm', component: () => import('../views/stock/Alarm.vue') },
      { path: 'stock/search', component: () => import('../views/stock/Search.vue') },
      { path: 'count/supplier', component: () => import('../views/count/Supplier.vue') },
      { path: 'count/customer', component: () => import('../views/count/Customer.vue') },
      { path: 'count/purchase', component: () => import('../views/count/Purchase.vue') },
      { path: 'count/sale', component: () => import('../views/count/Sale.vue') },
      { path: 'count/saleDay', component: () => import('../views/count/SaleDay.vue') },
      { path: 'count/saleMonth', component: () => import('../views/count/SaleMonth.vue') },
      { path: 'basicData/supplier', component: () => import('../views/basicData/Supplier.vue') },
      { path: 'basicData/customer', component: () => import('../views/basicData/Customer.vue') },
      { path: 'basicData/goods', component: () => import('../views/basicData/Goods.vue') },
      { path: 'basicData/stock', component: () => import('../views/basicData/Stock.vue') },
      { path: 'power/role', component: () => import('../views/power/Role.vue') },
      { path: 'power/user', component: () => import('../views/power/User.vue') },
      { path: 'power/log', component: () => import('../views/power/Log.vue') }
    ]
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('jxc_token')
  if (!token && to.path !== '/login') {
    next('/login')
  } else {
    next()
  }
})

export default router

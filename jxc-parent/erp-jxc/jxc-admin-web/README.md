# JXC 进销存管理系统 - 前端工程（jxc-admin-web）

进销存管理系统（JXC）的前端单页应用（SPA），配合后端服务 [jxc-admin] 使用，提供供应商、客户、商品、进货、销售、库存、统计报表、系统管理等完整业务功能的可视化界面。

## 技术栈

| 分类 | 技术 | 版本 |
|------|------|------|
| 核心框架 | Vue | 3.4 |
| 构建工具 | Vite | 5.x |
| UI 组件库 | Element Plus | 2.6 |
| 路由 | Vue Router | 4.x（Hash 模式） |
| 状态管理 | Pinia | 2.1 |
| HTTP 请求 | Axios | 1.6 |
| 图表 | ECharts | 5.5 |
| 包管理 | npm | — |

## 工程结构

```
jxc-admin-web/
├── package.json
├── vite.config.js              # Vite 配置（含开发代理 /api → 后端）
├── index.html
└── src/
    ├── main.js                 # 应用入口
    ├── App.vue
    ├── router/index.js         # 路由配置 + 登录守卫
    ├── store/user.js           # Pinia 用户状态
    ├── utils/request.js        # Axios 封装（token 注入、错误处理）
    ├── api/index.js            # 全部后端 API 定义
    ├── layout/
    │   ├── index.vue           # 主框架（侧边栏 + 顶栏）
    │   └── SideMenu.vue        # 递归菜单组件（支持任意层级）
    ├── assets/                 # 全局样式
    └── views/                  # 业务页面（29 个）
        ├── login/              # 登录
        ├── basicData/          # 供应商/客户/商品/期初库存
        ├── purchase/           # 进货入库/退货/查询
        ├── sale/               # 销售出库/客户退货/查询
        ├── stock/              # 报损/报溢/报警/库存查询
        ├── count/              # 6 个统计页面（含 ECharts）
        └── power/              # 角色/用户/日志
```

## 功能模块

| 模块 | 页面 | 说明 |
|------|------|------|
| 首页 | Home | 数据看板（库存报警数、商品总数） |
| 基础资料 | Supplier / Customer / Goods / Stock | 供应商、客户、商品（类别树）、期初库存 |
| 进货管理 | Purchase / Return / PurchaseSearch / ReturnSearch | 进货入库、采购退货、单据查询 |
| 销售管理 | SaleOut / SaleReturn / SaleSearch / ReturnSearch | 销售出库、客户退货、单据查询 |
| 库存管理 | Damage / Overflow / Alarm / Search | 报损、报溢、库存报警、库存查询 |
| 统计报表 | Supplier / Customer / Purchase / Sale / SaleDay / SaleMonth | 多维度统计（表格 + 柱状图/折线图） |
| 系统管理 | Role / User / Log | 角色管理（菜单权限）、用户管理、系统日志 |

## 环境要求

- Node.js 16+（推荐 18/20 LTS）
- npm 8+

## 快速开始

### 1. 安装依赖

```bash
npm install
```

### 2. 启动开发服务器

```bash
npm run dev
```

默认启动于 `http://localhost:5173`。

> 开发模式下，`/api` 请求会自动代理到后端 `http://localhost:18080`（配置见 `vite.config.js`），请确保后端服务已启动。

### 3. 登录系统

浏览器访问 `http://localhost:5173`，默认账号：`admin / admin123`。

## 后端代理配置

`vite.config.js` 中已配置开发代理：

```js
server: {
  port: 5173,
  proxy: {
    '/api': {
      target: 'http://localhost:18080',   // 后端地址，端口变更时同步修改
      changeOrigin: true
    }
  }
}
```

若后端端口不是 18080，请在此处同步修改 `target`。

## 生产构建

```bash
npm run build      # 构建产物输出到 dist/
npm run preview    # 本地预览构建产物
```

构建产物为纯静态文件，可部署到任意 Web 服务器（Nginx / Apache 等）。部署时需将 `/api` 反向代理到后端服务。

## 交互特性

- **单据录入**：支持商品多选、明细表格内编辑数量/单价、实时计算金额与合计行
- **动态菜单**：根据登录用户权限动态渲染侧边栏菜单
- **响应式布局**：侧边栏可折叠，适配不同屏幕
- **数据可视化**：统计页面集成 ECharts（柱状图、折线图）
- **统一错误处理**：Axios 拦截器统一弹窗提示，登录失效自动跳转登录页

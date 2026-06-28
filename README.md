# ERP-JXC 进销存管理系统

一个基于前后端分离架构的进销存管理系统，涵盖进货管理、销售管理、库存管理、统计报表、基础资料、系统管理等核心业务模块。本仓库为 **Monorepo（单仓库）**，同时包含后端服务与前端工程。

## 仓库结构

```
erp-jxc/
├── README.md                  ← 本文件（项目总览）
├── jxc-admin/                 ← 后端服务（Spring Boot）
│   ├── .gitignore
│   ├── README.md              ← 后端详细文档
│   ├── pom.xml
│   ├── doc/                   ← 设计文档与数据库脚本
│   │   ├── 系统详细设计说明书.md
│   │   └── jxc_schema.sql
│   └── src/
└── jxc-admin-web/             ← 前端工程（Vue 3）
    ├── .gitignore
    ├── README.md              ← 前端详细文档
    ├── package.json
    └── src/
```

> 各子项目的构建产物忽略规则分别由各自的 `.gitignore` 管理（后端忽略 `target/`，前端忽略 `node_modules/`、`dist/`）。

## 子项目说明

| 项目 | 目录 | 技术栈 | 说明 |
|------|------|--------|------|
| 后端服务 | [`jxc-admin`](./jxc-admin) | Spring Boot 2.7 + MyBatis-Plus + MySQL + JWT | 提供 RESTful API，承载全部业务逻辑 |
| 前端工程 | [`jxc-admin-web`](./jxc-admin-web) | Vue 3 + Element Plus + Vite + Pinia + ECharts | 单页应用，提供可视化操作界面 |

各子项目的详细说明（技术栈、工程结构、接口、配置等）见对应目录下的 `README.md`。

## 业务模块

- **进货管理**：进货入库、采购退货、单据查询
- **销售管理**：销售出库、客户退货、单据查询
- **库存管理**：商品报损、商品报溢、库存报警、当前库存查询
- **统计报表**：供应商统计、客户统计、商品采购/销售统计、按日/按月统计分析
- **基础资料**：供应商管理、客户管理、商品管理（类别树）、期初库存
- **系统管理**：角色管理（菜单权限）、用户管理、系统日志

## 环境要求

- JDK 1.8+
- Maven 3.6+
- Node.js 16+
- MySQL 5.7+ / 8.0+

## 快速开始

### 1. 初始化数据库

```bash
mysql -uroot -p123456 < jxc-admin/doc/jxc_schema.sql
```

脚本会创建 `jxc` 数据库、全部 23 张表，并附带系统基础数据与业务演示数据。默认管理员账号：`admin / admin123`。

### 2. 启动后端

```bash
cd jxc-admin
mvn spring-boot:run
```

服务启动于 `http://localhost:18080/api`。

> 如数据库账号/端口不同，修改 `jxc-admin/src/main/resources/application.yml`。

### 3. 启动前端

```bash
cd jxc-admin-web
npm install
npm run dev
```

前端启动于 `http://localhost:5173`，开发模式下 `/api` 请求自动代理到后端。

### 4. 访问系统

浏览器打开 `http://localhost:5173`，使用 `admin / admin123` 登录。

## 相关文档

- [系统详细设计说明书](./jxc-admin/doc/系统详细设计说明书.md)
- [后端 README](./jxc-admin/README.md)
- [前端 README](./jxc-admin-web/README.md)

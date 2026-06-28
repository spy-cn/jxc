# JXC 进销存管理系统 - 管理后台服务（jxc-admin）

进销存管理系统（JXC）的后端服务，基于 Spring Boot 构建，提供 RESTful API，涵盖进货管理、销售管理、库存管理、统计报表、基础资料、系统管理等核心业务模块。

## 技术栈

| 分类 | 技术 | 版本 |
|------|------|------|
| 基础框架 | Spring Boot | 2.7.18 |
| 持久层 | MyBatis-Plus | 3.5.3.1 |
| 数据库 | MySQL | 8.0+ |
| 安全鉴权 | JWT (jjwt) | 0.11.5 |
| 工具库 | Hutool / Lombok | 5.8.22 / 1.18.x |
| 构建 | Maven | 3.6+ |
| JDK | Java | 1.8+ |

## 工程结构

```
jxc-admin/
├── pom.xml
└── src/main/
    ├── java/com/spy/jxc/admin/
    │   ├── JxcApplication.java          # 启动类
    │   ├── annotation/                  # @SysLog 注解 + AOP 日志切面
    │   ├── common/                      # 公共组件
    │   │   ├── result/                  # 统一响应 Result
    │   │   ├── exception/               # 全局异常处理
    │   │   └── jwt/                     # JwtUtil + CurrentUser
    │   ├── config/                      # CORS、JWT 拦截器、MyBatis-Plus 配置
    │   ├── entity/                      # 实体类（23 张表）
    │   ├── mapper/                      # Mapper 接口
    │   ├── service/                     # Service 接口 + impl 业务实现
    │   ├── controller/                  # REST Controller
    │   └── dto/                         # 数据传输对象
    └── resources/
        ├── application.yml              # 应用配置
        ├── mapper/*.xml                 # MyBatis XML
        └── db/jxc.sql                   # 数据库初始化脚本
```

## 分层架构

严格遵循 **Controller → Service → Mapper** 三层架构：
- **Controller**：接收请求、参数校验、调用 Service、返回统一 `Result` 包装
- **Service**：业务逻辑、事务控制、库存联动、操作日志
- **Mapper**：继承 MyBatis-Plus `BaseMapper`，复杂查询使用 XML

## 环境要求

- JDK 1.8 及以上
- Maven 3.6+
- MySQL 5.7+ / 8.0+

## 快速开始

### 1. 初始化数据库

```bash
mysql -uroot -p123456 < src/main/resources/db/jxc.sql
```

默认创建 `jxc` 数据库，含全部表结构与初始数据。默认管理员账号：`admin / admin123`。

### 2. 修改数据库配置（如有不同）

编辑 `src/main/resources/application.yml`：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/jxc?...
    username: root
    password: 123456
```

### 3. 启动服务

```bash
mvn spring-boot:run
```

服务默认启动于 `http://localhost:18080/api`。

> 若端口被占用，修改 `application.yml` 中的 `server.port`。

## 核心业务说明

- **库存联动**：进货入库/客户退货 → 库存增加；销售出库/采购退货/报损 → 库存减少；报溢 → 库存增加。全部在事务中完成。
- **操作日志**：通过 `@SysLog` 注解 + AOP 切面自动记录所有写操作（增删改），无需在业务代码中手动埋点。
- **权限控制**：基于 RBAC（用户-角色-菜单），JWT 鉴权，登录后返回动态菜单树。
- **库存报警**：商品库存数量 ≤ 库存下限时自动预警。

## API 说明

所有接口前缀 `/api`，统一返回格式：

```json
{ "code": 200, "message": "success", "data": {} }
```

主要接口分组：

| 模块 | 路径前缀 | 说明 |
|------|----------|------|
| 认证 | `/auth` | 登录、获取菜单 |
| 基础资料 | `/supplier` `/customer` `/goods` `/goodsType` `/unit` | 供应商/客户/商品/类别/单位 |
| 进货管理 | `/purchase` `/return` | 进货入库、采购退货 |
| 销售管理 | `/sale` `/customerReturn` | 销售出库、客户退货 |
| 库存管理 | `/damage` `/overflow` | 报损、报溢（库存报警 `/goods/alarm`） |
| 统计报表 | `/count/*` | 采购/销售/供应商/客户/按日/按月统计 |
| 系统管理 | `/user` `/role` `/menu` `/log` | 用户/角色/菜单/日志 |

请求需在 Header 携带 `Authorization: Bearer <token>`（登录接口除外）。

## 配置说明

`application.yml` 关键配置：

```yaml
server:
  port: 18080                    # 服务端口
  servlet:
    context-path: /api           # 接口前缀
jxc:
  jwt:
    secret: <生产环境请更换>      # JWT 密钥
    expire: 86400                 # token 有效期（秒），默认 24 小时
```

## 打包部署

```bash
mvn clean package -DskipTests
# 产物：target/jxc-admin.jar
java -jar target/jxc-admin.jar
```

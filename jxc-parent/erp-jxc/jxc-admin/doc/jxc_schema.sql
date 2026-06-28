-- ============================================================================
-- JXC 进销存管理系统 - 数据库建表脚本
-- 数据库：MySQL 5.7+ / 8.0+（已在 9.2 验证通过）
-- 说明：创建 jxc 数据库及全部 23 张表，并插入系统运行所需的基础数据
--       （用户、角色、菜单、商品单位、商品类别），不含商品/单据等业务测试数据。
-- 字符集：utf8mb4
-- ============================================================================

DROP DATABASE IF EXISTS `jxc`;
CREATE DATABASE `jxc` DEFAULT CHARACTER SET utf8mb4;
USE `jxc`;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ============================================================================
-- 一. 一、系统管理表（RBAC 权限模型）：用户、角色、菜单及关联关系
-- ============================================================================

CREATE TABLE `t_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id，主键',
  `user_name` varchar(50) DEFAULT NULL COMMENT '用户名',
  `password` varchar(50) DEFAULT NULL COMMENT '密码',
  `true_name` varchar(50) DEFAULT NULL COMMENT '真实姓名',
  `roles` varchar(255) DEFAULT NULL COMMENT '角色',
  `remarks` varchar(1000) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

CREATE TABLE `t_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色id，主键',
  `role_name` varchar(50) DEFAULT NULL COMMENT '角色名称',
  `remarks` varchar(1000) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

CREATE TABLE `t_user_role` (
  `user_role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`user_role_id`) USING BTREE,
  KEY `role_id` (`role_id`) USING BTREE,
  KEY `user_id` (`user_id`) USING BTREE,
  CONSTRAINT `t_user_role_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `t_role` (`role_id`),
  CONSTRAINT `t_user_role_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

CREATE TABLE `t_menu` (
  `menu_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '菜单id',
  `menu_icon` varchar(100) DEFAULT NULL COMMENT '菜单图片',
  `menu_name` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `p_id` int(11) DEFAULT NULL COMMENT '父菜单id',
  `menu_state` int(11) DEFAULT NULL COMMENT '菜单状态，1表示目录，0表示结点',
  `menu_url` varchar(200) DEFAULT NULL COMMENT '菜单的链接地址',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6051 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

CREATE TABLE `t_role_menu` (
  `role_menu_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色-菜单id',
  `menu_id` int(11) DEFAULT NULL COMMENT '菜单id',
  `role_id` int(11) DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`role_menu_id`) USING BTREE,
  KEY `FKhayg4ib6v7h1wyeyxhq6xlddq` (`menu_id`) USING BTREE,
  KEY `FKsonb0rbt2u99hbrqqvv3r0wse` (`role_id`) USING BTREE,
  CONSTRAINT `t_role_menu_ibfk_1` FOREIGN KEY (`menu_id`) REFERENCES `t_menu` (`menu_id`),
  CONSTRAINT `t_role_menu_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `t_role` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=163 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

CREATE TABLE `t_log` (
  `log_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '日志id，主键',
  `log_type` varchar(50) DEFAULT NULL COMMENT '日志类型',
  `content` varchar(50) DEFAULT NULL COMMENT '日志内容',
  `log_date` datetime DEFAULT NULL COMMENT '日志时间',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id，外键',
  PRIMARY KEY (`log_id`) USING BTREE,
  KEY `FKbvn5yabu3vqwvtjoh32i9r4ip` (`user_id`) USING BTREE,
  CONSTRAINT `t_log_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6794 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ============================================================================
-- 二. 二、基础资料表：供应商、客户、商品类别、商品、商品单位
-- ============================================================================

CREATE TABLE `t_supplier` (
  `supplier_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '供应商id，主键',
  `supplier_name` varchar(100) DEFAULT NULL COMMENT '供应商名称',
  `contacts` varchar(50) DEFAULT NULL COMMENT '联系人',
  `phone_number` varchar(50) DEFAULT NULL COMMENT '联系人电话',
  `address` varchar(200) DEFAULT NULL COMMENT '供应商地址',
  `remarks` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`supplier_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

CREATE TABLE `t_customer` (
  `customer_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '客户编号id，主键',
  `customer_name` varchar(100) DEFAULT NULL COMMENT '客户名称',
  `contacts` varchar(50) DEFAULT NULL COMMENT '联系人',
  `phone_number` varchar(50) DEFAULT NULL COMMENT '联系人电话',
  `address` varchar(200) DEFAULT NULL COMMENT '客户地址',
  `remarks` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`customer_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

CREATE TABLE `t_goods_type` (
  `goods_type_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品类别id，主键',
  `goods_type_name` varchar(50) DEFAULT NULL COMMENT '商品类别名称',
  `p_id` int(11) DEFAULT NULL COMMENT '父商品类别id',
  `goods_type_state` int(11) DEFAULT NULL COMMENT '类别状态，0为叶子节点',
  PRIMARY KEY (`goods_type_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

CREATE TABLE `t_goods` (
  `goods_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品编号id，主键',
  `goods_code` varchar(50) DEFAULT NULL COMMENT '商品编码',
  `goods_name` varchar(50) DEFAULT NULL COMMENT '商品名称',
  `inventory_quantity` int(11) NOT NULL COMMENT '库存数量',
  `min_num` int(11) NOT NULL COMMENT '库存下限',
  `goods_model` varchar(50) DEFAULT NULL COMMENT '商品型号',
  `goods_producer` varchar(200) DEFAULT NULL COMMENT '生产厂商',
  `purchasing_price` float NOT NULL COMMENT '采购价格',
  `last_purchasing_price` float NOT NULL COMMENT '上一次采购价格',
  `remarks` varchar(1000) DEFAULT NULL COMMENT '备注',
  `selling_price` float NOT NULL COMMENT '出售价格',
  `state` int(11) NOT NULL COMMENT '0表示初始值,1表示已入库，2表示有进货或销售单据',
  `goods_unit` varchar(10) DEFAULT NULL COMMENT '商品单位',
  `goods_type_id` int(11) DEFAULT NULL COMMENT '商品类别id，外键',
  PRIMARY KEY (`goods_id`) USING BTREE,
  KEY `goods_type_id` (`goods_type_id`) USING BTREE,
  CONSTRAINT `t_goods_ibfk_1` FOREIGN KEY (`goods_type_id`) REFERENCES `t_goods_type` (`goods_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

CREATE TABLE `t_unit` (
  `unit_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品单位id，主键',
  `unit_name` varchar(10) DEFAULT NULL COMMENT '商品单位名称',
  PRIMARY KEY (`unit_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ============================================================================
-- 三. 三、业务单据表（各含主表与商品明细表）
--     库存影响：进货入库/客户退货/报溢 → 库存增加；销售出库/采购退货/报损 → 库存减少
-- ============================================================================

CREATE TABLE `t_purchase_list` (
  `purchase_list_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '进货单id，主键',
  `purchase_number` varchar(100) DEFAULT NULL COMMENT '进货单号',
  `amount_paid` float NOT NULL COMMENT '实付金额',
  `amount_payable` float NOT NULL COMMENT '应付金额',
  `purchase_date` varchar(20) DEFAULT NULL COMMENT '收货日期',
  `remarks` varchar(1000) DEFAULT NULL COMMENT '备注',
  `state` int(11) DEFAULT NULL COMMENT '状态',
  `supplier_id` int(11) DEFAULT NULL COMMENT '供应商id，外键',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id，外键',
  PRIMARY KEY (`purchase_list_id`) USING BTREE,
  KEY `supplier_id` (`supplier_id`) USING BTREE,
  KEY `user_id` (`user_id`) USING BTREE,
  CONSTRAINT `t_purchase_list_ibfk_1` FOREIGN KEY (`supplier_id`) REFERENCES `t_supplier` (`supplier_id`),
  CONSTRAINT `t_purchase_list_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=136 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

CREATE TABLE `t_purchase_list_goods` (
  `purchase_list_goods_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '进货单商品列表id，主键',
  `goods_id` int(11) DEFAULT NULL COMMENT '商品编号id，外键',
  `goods_code` varchar(50) DEFAULT NULL COMMENT '商品编码',
  `goods_name` varchar(50) DEFAULT NULL COMMENT '商品名称',
  `goods_model` varchar(50) DEFAULT NULL COMMENT '商品型号',
  `goods_unit` varchar(10) DEFAULT NULL COMMENT '商品单位',
  `goods_num` int(11) NOT NULL COMMENT '进货数量',
  `price` float NOT NULL COMMENT '商品单价',
  `total` float NOT NULL COMMENT '总金额',
  `purchase_list_id` int(11) DEFAULT NULL COMMENT '进货单id，外键',
  `goods_type_id` int(11) DEFAULT NULL COMMENT '商品类别id，外键',
  PRIMARY KEY (`purchase_list_goods_id`) USING BTREE,
  KEY `goods_id` (`goods_id`) USING BTREE,
  KEY `purchase_list_id` (`purchase_list_id`) USING BTREE,
  KEY `goods_type_id` (`goods_type_id`) USING BTREE,
  CONSTRAINT `t_purchase_list_goods_ibfk_1` FOREIGN KEY (`goods_id`) REFERENCES `t_goods` (`goods_id`),
  CONSTRAINT `t_purchase_list_goods_ibfk_2` FOREIGN KEY (`purchase_list_id`) REFERENCES `t_purchase_list` (`purchase_list_id`),
  CONSTRAINT `t_purchase_list_goods_ibfk_3` FOREIGN KEY (`goods_type_id`) REFERENCES `t_goods_type` (`goods_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=137 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

CREATE TABLE `t_return_list` (
  `return_list_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '退货单id，主键',
  `return_number` varchar(100) DEFAULT NULL COMMENT '退货单号',
  `return_date` varchar(20) DEFAULT NULL COMMENT '退货日期',
  `amount_paid` float NOT NULL COMMENT '实退金额',
  `amount_payable` float NOT NULL COMMENT '应退金额',
  `remarks` varchar(1000) DEFAULT NULL COMMENT '备注',
  `state` int(11) DEFAULT NULL COMMENT '状态,1表示已退，2表示未退',
  `supplier_id` int(11) DEFAULT NULL COMMENT '供应商id，外键',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id，外键',
  PRIMARY KEY (`return_list_id`) USING BTREE,
  KEY `FK4qxjj8bvj2etne243xluni0vn` (`supplier_id`) USING BTREE,
  KEY `FK904juw2v1hm2av0ig26gae9jb` (`user_id`) USING BTREE,
  CONSTRAINT `t_return_list_ibfk_1` FOREIGN KEY (`supplier_id`) REFERENCES `t_supplier` (`supplier_id`),
  CONSTRAINT `t_return_list_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

CREATE TABLE `t_return_list_goods` (
  `return_list_goods_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '退货单商品列表id，主键',
  `goods_id` int(11) DEFAULT NULL COMMENT '商品编号id，外键',
  `goods_code` varchar(50) DEFAULT NULL COMMENT '商品编码',
  `goods_name` varchar(50) DEFAULT NULL COMMENT '商品名称',
  `goods_model` varchar(50) DEFAULT NULL COMMENT '商品型号',
  `goods_unit` varchar(10) DEFAULT NULL COMMENT '商品单位',
  `goods_num` int(11) DEFAULT NULL COMMENT '商品数量',
  `price` float NOT NULL COMMENT '商品单价',
  `total` float NOT NULL COMMENT '总金额',
  `return_list_id` int(11) DEFAULT NULL COMMENT '退货单id，外键',
  `goods_type_id` int(11) DEFAULT NULL COMMENT '商品类别id，外键',
  PRIMARY KEY (`return_list_goods_id`) USING BTREE,
  KEY `FKemclu281vyvyk063c3foafq1w` (`return_list_id`) USING BTREE,
  KEY `FKpxnqi9jfkw6wdm1uox2kkr0wk` (`goods_type_id`) USING BTREE,
  KEY `goods_id` (`goods_id`) USING BTREE,
  CONSTRAINT `t_return_list_goods_ibfk_1` FOREIGN KEY (`return_list_id`) REFERENCES `t_return_list` (`return_list_id`),
  CONSTRAINT `t_return_list_goods_ibfk_3` FOREIGN KEY (`goods_id`) REFERENCES `t_goods` (`goods_id`),
  CONSTRAINT `t_return_list_goods_ibfk_4` FOREIGN KEY (`goods_type_id`) REFERENCES `t_goods_type` (`goods_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

CREATE TABLE `t_sale_list` (
  `sale_list_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '销售单id，主键',
  `sale_number` varchar(100) DEFAULT NULL COMMENT '销售单号',
  `amount_paid` float NOT NULL COMMENT '实付金额',
  `amount_payable` float NOT NULL COMMENT '应付金额',
  `sale_date` varchar(20) DEFAULT NULL COMMENT '销售单创建日期',
  `state` int(11) DEFAULT NULL COMMENT '状态',
  `remarks` varchar(1000) DEFAULT NULL COMMENT '备注',
  `customer_id` int(11) DEFAULT NULL COMMENT '客户id，外键',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id，外键',
  PRIMARY KEY (`sale_list_id`) USING BTREE,
  KEY `FKox4qfs87eu3fvwdmrvelqhi8e` (`customer_id`) USING BTREE,
  KEY `FK34bnujemrdqimbhg133enp8k8` (`user_id`) USING BTREE,
  CONSTRAINT `t_sale_list_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`user_id`),
  CONSTRAINT `t_sale_list_ibfk_2` FOREIGN KEY (`customer_id`) REFERENCES `t_customer` (`customer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

CREATE TABLE `t_sale_list_goods` (
  `sale_list_goods_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '销售单商品列表id，主键',
  `goods_id` int(11) DEFAULT NULL COMMENT '商品编号id，外键',
  `goods_code` varchar(50) DEFAULT NULL COMMENT '商品编码',
  `goods_name` varchar(50) DEFAULT NULL COMMENT '商品名称',
  `goods_model` varchar(50) DEFAULT NULL COMMENT '商品型号',
  `goods_num` int(11) DEFAULT NULL COMMENT '销售数量',
  `goods_unit` varchar(10) DEFAULT NULL COMMENT '商品单位',
  `price` float NOT NULL COMMENT '商品单价',
  `total` float NOT NULL COMMENT '总金额',
  `sale_list_id` int(11) DEFAULT NULL COMMENT '销售单id，外键',
  `goods_type_id` int(11) DEFAULT NULL COMMENT '商品类别id，外键',
  PRIMARY KEY (`sale_list_goods_id`) USING BTREE,
  KEY `FK20ehd6ta9geyql4hxtdsnhbox` (`sale_list_id`) USING BTREE,
  KEY `FK39ej927qf0ldkykafj2nhyu3u` (`goods_type_id`) USING BTREE,
  KEY `goods_id` (`goods_id`) USING BTREE,
  CONSTRAINT `t_sale_list_goods_ibfk_1` FOREIGN KEY (`goods_id`) REFERENCES `t_goods` (`goods_id`),
  CONSTRAINT `t_sale_list_goods_ibfk_2` FOREIGN KEY (`sale_list_id`) REFERENCES `t_sale_list` (`sale_list_id`),
  CONSTRAINT `t_sale_list_goods_ibfk_3` FOREIGN KEY (`goods_type_id`) REFERENCES `t_goods_type` (`goods_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

CREATE TABLE `t_customer_return_list` (
  `customer_return_list_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '客户退货单id，主键',
  `return_number` varchar(100) DEFAULT NULL COMMENT '退货单号',
  `return_date` varchar(20) DEFAULT NULL COMMENT '退货日期',
  `amount_paid` float NOT NULL COMMENT '实付金额',
  `amount_payable` float NOT NULL COMMENT '应付金额',
  `state` int(11) DEFAULT NULL COMMENT '状态，是否付款',
  `customer_id` int(11) DEFAULT NULL COMMENT '客户编号id，外键',
  `user_id` int(11) DEFAULT NULL COMMENT '操作员，用户id，外键',
  `remarks` varchar(1000) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`customer_return_list_id`) USING BTREE,
  KEY `customer_id` (`customer_id`) USING BTREE,
  KEY `user_id` (`user_id`) USING BTREE,
  CONSTRAINT `t_customer_return_list_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `t_customer` (`customer_id`),
  CONSTRAINT `t_customer_return_list_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

CREATE TABLE `t_customer_return_list_goods` (
  `customer_return_list_goods_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '客户退货单商品列表id，主键',
  `goods_id` int(11) DEFAULT NULL COMMENT '商品编号id，外键',
  `goods_code` varchar(50) DEFAULT NULL COMMENT '商品编码',
  `goods_name` varchar(50) DEFAULT NULL COMMENT '商品名称',
  `goods_model` varchar(50) DEFAULT NULL COMMENT '商品型号',
  `goods_num` int(11) DEFAULT NULL COMMENT '客户退货数量',
  `goods_unit` varchar(10) DEFAULT NULL COMMENT '商品单位',
  `price` float NOT NULL COMMENT '商品单价',
  `total` float NOT NULL COMMENT '总金额',
  `customer_return_list_id` int(11) DEFAULT NULL COMMENT '客户退货单id，外键',
  `goods_type_id` int(11) DEFAULT NULL COMMENT '商品类别id，外键',
  PRIMARY KEY (`customer_return_list_goods_id`) USING BTREE,
  KEY `FKtqt67mbn96lxn8hvtl4piblhi` (`customer_return_list_id`) USING BTREE,
  KEY `FK32ijokbrx3j6h0p6aa9hcccbq` (`goods_type_id`) USING BTREE,
  KEY `goods_id` (`goods_id`) USING BTREE,
  CONSTRAINT `t_customer_return_list_goods_ibfk_1` FOREIGN KEY (`goods_id`) REFERENCES `t_goods` (`goods_id`),
  CONSTRAINT `t_customer_return_list_goods_ibfk_2` FOREIGN KEY (`goods_type_id`) REFERENCES `t_goods_type` (`goods_type_id`),
  CONSTRAINT `t_customer_return_list_goods_ibfk_3` FOREIGN KEY (`customer_return_list_id`) REFERENCES `t_customer_return_list` (`customer_return_list_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

CREATE TABLE `t_damage_list` (
  `damage_list_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品报损单id，主键',
  `damage_number` varchar(100) DEFAULT NULL COMMENT '商品报损单号',
  `damage_date` varchar(20) DEFAULT NULL COMMENT '创建日期',
  `remarks` varchar(1000) DEFAULT NULL COMMENT '备注',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id，外键',
  PRIMARY KEY (`damage_list_id`) USING BTREE,
  KEY `FKpn094ma69sch1icjc2gu7xus` (`user_id`) USING BTREE,
  CONSTRAINT `t_damage_list_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

CREATE TABLE `t_damage_list_goods` (
  `damage_list_goods_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品报损单商品列表id，主键',
  `goods_id` int(11) DEFAULT NULL COMMENT '商品编号id，外键',
  `goods_code` varchar(50) DEFAULT NULL COMMENT '商品编码',
  `goods_name` varchar(50) DEFAULT NULL COMMENT '商品名称',
  `goods_model` varchar(50) DEFAULT NULL COMMENT '商品型号',
  `goods_unit` varchar(10) DEFAULT NULL COMMENT '商品单位',
  `goods_num` int(11) DEFAULT NULL COMMENT '报损数量',
  `price` float NOT NULL COMMENT '商品单价',
  `total` float NOT NULL COMMENT '总金额',
  `damage_list_id` int(11) DEFAULT NULL COMMENT '商品报损单id，外键',
  `goods_type_id` int(11) DEFAULT NULL COMMENT '商品类别id，外键',
  PRIMARY KEY (`damage_list_goods_id`) USING BTREE,
  KEY `FKbf5m8mm3gctrnuubr9xkjamj8` (`damage_list_id`) USING BTREE,
  KEY `FK8r7ietq6opa0ci7uxdqc264yf` (`goods_type_id`) USING BTREE,
  KEY `goods_id` (`goods_id`) USING BTREE,
  CONSTRAINT `t_damage_list_goods_ibfk_1` FOREIGN KEY (`goods_id`) REFERENCES `t_goods` (`goods_id`),
  CONSTRAINT `t_damage_list_goods_ibfk_2` FOREIGN KEY (`damage_list_id`) REFERENCES `t_damage_list` (`damage_list_id`),
  CONSTRAINT `t_damage_list_goods_ibfk_3` FOREIGN KEY (`goods_type_id`) REFERENCES `t_goods_type` (`goods_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

CREATE TABLE `t_overflow_list` (
  `overflow_list_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品报溢单id，主键',
  `overflow_number` varchar(100) DEFAULT NULL COMMENT '商品报溢单号',
  `overflow_date` varchar(20) DEFAULT NULL COMMENT '报溢日期',
  `remarks` varchar(1000) DEFAULT NULL COMMENT '备注',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id，外键',
  PRIMARY KEY (`overflow_list_id`) USING BTREE,
  KEY `FK3bu8hj2xniqwbrtg6ls6b8ej2` (`user_id`) USING BTREE,
  CONSTRAINT `t_overflow_list_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

CREATE TABLE `t_overflow_list_goods` (
  `overflow_list_goods_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品报溢单商品列表id，主键',
  `goods_id` int(11) DEFAULT NULL COMMENT '商品编号id，外键',
  `goods_code` varchar(50) DEFAULT NULL COMMENT '商品编码',
  `goods_name` varchar(50) DEFAULT NULL COMMENT '商品名称',
  `goods_model` varchar(50) DEFAULT NULL COMMENT '商品型号',
  `goods_unit` varchar(10) DEFAULT NULL COMMENT '商品单位',
  `goods_num` int(11) DEFAULT NULL COMMENT '报溢数量',
  `price` float NOT NULL COMMENT '商品单价',
  `total` float NOT NULL COMMENT '总金额',
  `overflow_list_id` int(11) DEFAULT NULL COMMENT '商品报溢单id，外键',
  `goods_type_id` int(11) DEFAULT NULL COMMENT '商品类别id，外键',
  PRIMARY KEY (`overflow_list_goods_id`) USING BTREE,
  KEY `FKd3s9761mgl456tn2xb0d164h7` (`overflow_list_id`) USING BTREE,
  KEY `FK20rudkne4kc8uftcenkrng1mn` (`goods_type_id`) USING BTREE,
  KEY `goods_id` (`goods_id`) USING BTREE,
  CONSTRAINT `t_overflow_list_goods_ibfk_1` FOREIGN KEY (`goods_id`) REFERENCES `t_goods` (`goods_id`),
  CONSTRAINT `t_overflow_list_goods_ibfk_2` FOREIGN KEY (`overflow_list_id`) REFERENCES `t_overflow_list` (`overflow_list_id`),
  CONSTRAINT `t_overflow_list_goods_ibfk_3` FOREIGN KEY (`goods_type_id`) REFERENCES `t_goods_type` (`goods_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ============================================================================
-- 四、系统基础初始化数据
-- ============================================================================

insert  into `t_user`(`user_id`,`user_name`,`password`,`true_name`,`roles`,`remarks`) values (1,'admin','admin123','兰杰',NULL,'管理员'),(3,'marry','123456','玛丽','仓库管理员,销售经理,采购员,主管','销售经理'),(10,'gucaini','123456','古采尼','采购员,主管,销售经理','古大师');

insert  into `t_role`(`role_id`,`role_name`,`remarks`) values (1,'系统管理员','管理员'),(2,'主管','主管'),(4,'采购员','采购员'),(5,'销售经理','销售经理'),(7,'仓库管理员','仓库管理员'),(8,'测试员','测试员');

insert  into `t_user_role`(`user_role_id`,`role_id`,`user_id`) values (1,1,1),(29,2,3),(30,4,3),(31,5,3),(32,7,3),(35,2,10),(36,4,10),(37,5,10);

insert  into `t_menu`(`menu_id`,`menu_icon`,`menu_name`,`p_id`,`menu_state`,`menu_url`) values (1,'menu-plugin','系统菜单',-1,1,NULL),(10,'menu-1','进货管理',1,1,NULL),(20,'menu-2','销售管理',1,1,NULL),(30,'menu-3','库存管理',1,1,NULL),(40,'menu-4','统计报表',1,1,NULL),(50,'menu-5','基础资料',1,1,NULL),(60,'menu-6','系统管理',1,1,NULL),(1010,'menu-11','进货入库',10,0,'/purchase/purchase.html'),(1020,'menu-12','退货出库',10,0,'/purchase/return.html'),(1030,'menu-13','进货单据查询',10,0,'/purchase/purchaseSearch.html'),(1040,'menu-14','退货单据查询',10,0,'/purchase/returnSearch.html'),(1050,'menu-15','当前库存查询',10,0,'/common/stockSearch.html'),(2010,'menu-21','销售出库',20,0,'/sale/saleout.html'),(2020,'menu-22','客户退货',20,0,'/sale/salereturn.html'),(2030,'menu-23','销售单据查询',20,0,'/sale/saleSearch.html'),(2040,'menu-24','客户退货查询',20,0,'/sale/returnSearch.html'),(2050,'menu-25','当前库存查询',20,0,'/common/stockSearch.html'),(3010,'menu-31','商品报损',30,0,'/stock/damage.html'),(3020,'menu-32','商品报溢',30,0,'/stock/overflow.html'),(3030,'menu-33','库存报警',30,0,'/stock/alarm.html'),(3040,'menu-34','报损报溢查询',30,0,'/stock/damageOverflowSearch.html'),(3050,'menu-35','当前库存查询',30,0,'/common/stockSearch.html'),(4010,'menu-41','供应商统计',40,0,'/count/supplier.html'),(4020,'menu-42','客户统计',40,0,'/count/customer.html'),(4030,'menu-43','商品采购统计',40,0,'/count/purchase.html'),(4040,'menu-44','商品销售统计',40,0,'/count/sale.html'),(4050,'menu-45','按日统计分析',40,0,'/count/saleDay.html'),(4060,'menu-46','按月统计分析',40,0,'/count/saleMonth.html'),(5010,'menu-51','供应商管理',50,0,'/basicData/supplier.html'),(5020,'menu-52','客户管理',50,0,'/basicData/customer.html'),(5030,'menu-53','商品管理',50,0,'/basicData/goods.html'),(5040,'menu-54','期初库存',50,0,'/basicData/stock.html'),(6010,'menu-61','角色管理',60,0,'/power/role.html'),(6020,'menu-62','用户管理',60,0,'/power/user.html'),(6030,'menu-65','系统日志',60,0,'/power/log.html'),(6040,'menu-63','修改密码',60,0,NULL),(6050,'menu-64','安全退出',60,0,NULL);

insert  into `t_role_menu`(`role_menu_id`,`menu_id`,`role_id`) values (45,1,4),(46,20,4),(47,2010,4),(48,1,5),(49,30,5),(50,3010,5),(51,1,NULL),(52,10,NULL),(53,1010,NULL),(54,1020,NULL),(55,1030,NULL),(56,1040,NULL),(57,1050,NULL),(58,20,NULL),(59,2010,NULL),(60,2020,NULL),(61,2030,NULL),(62,2040,NULL),(63,2050,NULL),(65,1,7),(66,10,7),(67,1010,7),(68,1020,7),(69,1030,7),(70,1040,7),(71,1050,7),(72,20,7),(73,2010,7),(74,2020,7),(75,2030,7),(76,40,7),(77,4010,7),(78,4020,7),(79,1,NULL),(80,10,NULL),(81,1010,NULL),(82,1020,NULL),(83,1030,NULL),(84,1040,NULL),(85,1050,NULL),(99,1,8),(100,50,8),(101,5010,8),(102,5020,8),(103,5030,8),(104,5040,8),(117,1,2),(118,10,2),(119,1010,2),(120,1020,2),(121,1030,2),(122,1040,2),(123,1050,2),(124,60,2),(125,6030,2),(126,6040,2),(127,6050,2),(128,1,1),(129,10,1),(130,1010,1),(131,1020,1),(132,1030,1),(133,1040,1),(134,1050,1),(135,20,1),(136,2010,1),(137,2020,1),(138,2030,1),(139,2040,1),(140,2050,1),(141,30,1),(142,3010,1),(143,3020,1),(144,3030,1),(145,3040,1),(146,3050,1),(147,40,1),(148,4010,1),(149,4020,1),(150,4030,1),(151,4040,1),(152,4050,1),(153,4060,1),(154,50,1),(155,5010,1),(156,5020,1),(157,60,1),(158,6010,1),(159,6020,1),(160,6030,1),(161,6040,1),(162,6050,1);

insert  into `t_unit`(`unit_id`,`unit_name`) values (1,'元'),(6,'户'),(7,'斤'),(8,'台'),(9,'瓶'),(10,'袋'),(11,'盒'),(12,'条'),(13,'吨'),(14,'个'),(15,'件');

insert  into `t_goods_type`(`goods_type_id`,`goods_type_name`,`p_id`,`goods_type_state`) values (1,'所有类别',-1,1),(2,'服饰',1,1),(3,'食品',1,1),(4,'家电',1,1),(5,'数码',1,1),(6,'连衣裙',2,0),(7,'男士西装',2,0),(8,'牛仔裤',2,0),(9,'进口食品',3,0),(10,'地方特产',3,0),(11,'休闲食品',3,0),(12,'电视机',4,0),(13,'洗衣机',4,0),(14,'冰箱',4,0),(15,'相机',5,0),(16,'手机',5,0),(17,'音箱',5,0);


-- ============================================================================

-- ============================================================================
-- 五、业务演示数据（供应商、客户、商品、各类单据，外键已严格对齐）
-- ============================================================================

-- ---- 供应商（id 1~3）----
insert into `t_supplier`(`supplier_id`,`supplier_name`,`contacts`,`phone_number`,`address`,`remarks`) values
(1,'四川耀荣汇商贸有限公司','张小五','13558873068','成都市金牛区淳风路133号','农副产品供应商'),
(2,'广东省郫县豆瓣股份有限公司','李四','028-87888888','成都市郫都区永安路333号','调味品供应商'),
(3,'深圳市数码电子科技有限公司','王五','0755-28500676','深圳市罗湖区宝丰大厦','数码家电供应商');

-- ---- 客户（id 1~3）----
insert into `t_customer`(`customer_id`,`customer_name`,`contacts`,`phone_number`,`address`,`remarks`) values
(1,'家乐福（青石桥店）','王经理','13555555555','成都市锦江区大业路6号','大型超市稳定客户'),
(2,'永辉超市（温江店）','李四','13888888888','成都市温江区永兴路51号','连锁超市客户'),
(3,'北京华联空港购物中心','王五','028-89460961','成都市双流区锦华路二段166号','购物中心客户');

-- ---- 商品（id 1~6，关联 goods_type 叶子类别；state=2 表示已有进销单据）----
-- goods_type 叶子: 7男士西装 11休闲食品 12电视机 14冰箱 16手机
insert into `t_goods`(`goods_id`,`goods_code`,`goods_name`,`inventory_quantity`,`min_num`,`goods_model`,`goods_producer`,`purchasing_price`,`last_purchasing_price`,`remarks`,`selling_price`,`state`,`goods_unit`,`goods_type_id`) values
(1,'SP0001','老干妈香辣脆油辣椒',148,50,'280g','陶华碧老干妈公司',7.95,7.50,'畅销调味品',12.00,2,'瓶',11),
(2,'SP0002','华为荣耀Note8手机',30,10,'Note8','华为技术有限公司',2152.51,2100.00,'热销手机',2599.00,2,'台',16),
(3,'SP0003','小米电视4A 32英寸',80,20,'4A-32','小米科技有限责任公司',700.00,680.00,'智能液晶电视',899.00,2,'台',12),
(4,'SP0004','海尔双门电冰箱',45,15,'BCD-190','海尔集团公司',1500.00,1480.00,'节能冰箱',1899.00,2,'台',14),
(5,'SP0005','休闲零食大礼包',205,100,'500g','良品铺子股份有限公司',25.00,23.00,'休闲食品',39.90,2,'件',11),
(6,'SP0006','男士商务西装',60,20,'M码','雅戈尔集团股份有限公司',500.00,480.00,'商务正装',699.00,2,'件',7);

-- ============================================================================
-- 六、业务单据（6 类，主表 + 明细，明细 total 之和 = 主表 amount_payable）
-- ============================================================================

-- ---- 进货入库单（user_id=1，供应商分别 1、3）----
-- 单1(supplier_id=1): 商品1×300瓶×7.95=2385 + 商品5×1件×15=15 → 2400
-- 单2(supplier_id=3): 商品2×1台×2152.51 + 商品3×1台×700 → 2852.51
insert into `t_purchase_list`(`purchase_list_id`,`purchase_number`,`amount_paid`,`amount_payable`,`purchase_date`,`remarks`,`state`,`supplier_id`,`user_id`) values
(1,'JH1700000000001',2400.00,2400.00,'2024-01-15','首批调味品进货',1,1,1),
(2,'JH1700000000002',2852.51,2852.51,'2024-02-10','数码产品进货',1,3,1);

insert into `t_purchase_list_goods`(`purchase_list_goods_id`,`goods_id`,`goods_code`,`goods_name`,`goods_model`,`goods_num`,`goods_unit`,`price`,`total`,`purchase_list_id`,`goods_type_id`) values
(1,1,'SP0001','老干妈香辣脆油辣椒','280g',300,'瓶',7.95,2385.00,1,11),
(2,5,'SP0005','休闲零食大礼包','500g',1,'件',15.00,15.00,1,11),
(3,2,'SP0002','华为荣耀Note8手机','Note8',1,'台',2152.51,2152.51,2,16),
(4,3,'SP0003','小米电视4A 32英寸','4A-32',1,'台',700.00,700.00,2,12);

-- ---- 采购退货单（supplier_id=1, user_id=1）----
-- 商品5×1件×15 = 15
insert into `t_return_list`(`return_list_id`,`return_number`,`return_date`,`amount_paid`,`amount_payable`,`remarks`,`state`,`supplier_id`,`user_id`) values
(1,'TH1700000000003','2024-02-20',15.00,15.00,'临期商品退货',1,1,1);

insert into `t_return_list_goods`(`return_list_goods_id`,`goods_id`,`goods_code`,`goods_name`,`goods_model`,`goods_num`,`goods_unit`,`price`,`total`,`return_list_id`,`goods_type_id`) values
(1,5,'SP0005','休闲零食大礼包','500g',1,'件',15.00,15.00,1,11);

-- ---- 销售出库单（user_id=1，客户分别 2、1）----
-- 单1(customer_id=2): 商品4×2台×1899=3798 + 商品6×2件×699=1398 + 商品2×1台×1302=1302 → 6498
-- 单2(customer_id=1): 商品3×1台×899 = 899
insert into `t_sale_list`(`sale_list_id`,`sale_number`,`amount_paid`,`amount_payable`,`sale_date`,`state`,`remarks`,`customer_id`,`user_id`) values
(1,'XS1700000000004',6498.00,6498.00,'2024-03-05',1,'客户采购家电',2,1),
(2,'XS1700000000005',899.00,899.00,'2024-03-12',1,'单台电视销售',1,1);

insert into `t_sale_list_goods`(`sale_list_goods_id`,`goods_id`,`goods_code`,`goods_name`,`goods_model`,`goods_num`,`goods_unit`,`price`,`total`,`sale_list_id`,`goods_type_id`) values
(1,4,'SP0004','海尔双门电冰箱','BCD-190',2,'台',1899.00,3798.00,1,14),
(2,6,'SP0006','男士商务西装','M码',2,'件',699.00,1398.00,1,7),
(3,2,'SP0002','华为荣耀Note8手机','Note8',1,'台',1302.00,1302.00,1,16),
(4,3,'SP0003','小米电视4A 32英寸','4A-32',1,'台',899.00,899.00,2,12);

-- ---- 客户退货单（customer_id=1, user_id=1）----
-- 商品3×1台×899 = 899
insert into `t_customer_return_list`(`customer_return_list_id`,`return_number`,`return_date`,`amount_paid`,`amount_payable`,`state`,`customer_id`,`user_id`,`remarks`) values
(1,'XT1700000000006','2024-03-18',899.00,899.00,1,1,1,'电视存在质量问题退货');

insert into `t_customer_return_list_goods`(`customer_return_list_goods_id`,`goods_id`,`goods_code`,`goods_name`,`goods_model`,`goods_num`,`goods_unit`,`price`,`total`,`customer_return_list_id`,`goods_type_id`) values
(1,3,'SP0003','小米电视4A 32英寸','4A-32',1,'台',899.00,899.00,1,12);

-- ---- 商品报损单（user_id=1）----
-- 商品1×2瓶×7.95 = 15.90
insert into `t_damage_list`(`damage_list_id`,`damage_number`,`damage_date`,`remarks`,`user_id`) values
(1,'BS1700000000007','2024-03-20','运输破损报损',1);

insert into `t_damage_list_goods`(`damage_list_goods_id`,`goods_id`,`goods_code`,`goods_name`,`goods_model`,`goods_unit`,`goods_num`,`price`,`total`,`damage_list_id`,`goods_type_id`) values
(1,1,'SP0001','老干妈香辣脆油辣椒','280g','瓶',2,7.95,15.90,1,11);

-- ---- 商品报溢单（user_id=1）----
-- 商品5×5件×25 = 125
insert into `t_overflow_list`(`overflow_list_id`,`overflow_number`,`overflow_date`,`remarks`,`user_id`) values
(1,'BY1700000000008','2024-03-22','盘点多出商品报溢',1);

insert into `t_overflow_list_goods`(`overflow_list_goods_id`,`goods_id`,`goods_code`,`goods_name`,`goods_model`,`goods_unit`,`goods_num`,`price`,`total`,`overflow_list_id`,`goods_type_id`) values
(1,5,'SP0005','休闲零食大礼包','500g','件',5,25.00,125.00,1,11);

-- ---- 系统操作日志（user_id=1，与上述业务操作对应）----
insert into `t_log`(`log_type`,`content`,`log_date`,`user_id`) values
('登录登出','用户登录：admin','2024-01-15 09:30:00',1),
('进货入库','新增进货单：JH1700000000001','2024-01-15 10:00:00',1),
('进货入库','新增进货单：JH1700000000002','2024-02-10 11:00:00',1),
('退货出库','新增采购退货单：TH1700000000003','2024-02-20 14:00:00',1),
('销售出库','新增销售单：XS1700000000004','2024-03-05 14:20:00',1),
('销售出库','新增销售单：XS1700000000005','2024-03-12 15:30:00',1),
('客户退货','新增客户退货单：XT1700000000006','2024-03-18 10:00:00',1),
('商品报损','新增报损单：BS1700000000007','2024-03-20 16:00:00',1),
('商品报溢','新增报溢单：BY1700000000008','2024-03-22 09:00:00',1);

SET FOREIGN_KEY_CHECKS = 1;

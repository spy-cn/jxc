package com.spy.jxc.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;

@Data
@TableName("t_supplier")
public class Supplier implements Serializable {
    @TableId(value = "supplier_id", type = IdType.AUTO)
    private Integer supplierId;
    private String supplierName;
    private String contacts;
    private String phoneNumber;
    private String address;
    private String remarks;
}

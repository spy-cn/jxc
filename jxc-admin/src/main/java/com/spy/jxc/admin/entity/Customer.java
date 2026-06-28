package com.spy.jxc.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;

@Data
@TableName("t_customer")
public class Customer implements Serializable {
    @TableId(value = "customer_id", type = IdType.AUTO)
    private Integer customerId;
    private String customerName;
    private String contacts;
    private String phoneNumber;
    private String address;
    private String remarks;
}

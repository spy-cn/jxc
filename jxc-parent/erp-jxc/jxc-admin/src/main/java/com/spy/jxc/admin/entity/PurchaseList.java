package com.spy.jxc.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
@TableName("t_purchase_list")
public class PurchaseList implements Serializable {
    @TableId(value = "purchase_list_id", type = IdType.AUTO)
    private Integer purchaseListId;
    private String purchaseNumber;
    private Float amountPaid;
    private Float amountPayable;
    private String purchaseDate;
    private String remarks;
    private Integer state;
    private Integer supplierId;
    private Integer userId;

    @TableField(exist = false)
    private String supplierName;
    @TableField(exist = false)
    private String userName;
    @TableField(exist = false)
    private List<PurchaseListGoods> goodsList;
    @TableField(exist = false)
    private String startDate;
    @TableField(exist = false)
    private String endDate;
}

package com.spy.jxc.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
@TableName("t_sale_list")
public class SaleList implements Serializable {
    @TableId(value = "sale_list_id", type = IdType.AUTO)
    private Integer saleListId;
    private String saleNumber;
    private Float amountPaid;
    private Float amountPayable;
    private String saleDate;
    private Integer state;
    private String remarks;
    private Integer customerId;
    private Integer userId;

    @TableField(exist = false)
    private String customerName;
    @TableField(exist = false)
    private String userName;
    @TableField(exist = false)
    private List<SaleListGoods> goodsList;
    @TableField(exist = false)
    private String startDate;
    @TableField(exist = false)
    private String endDate;
}

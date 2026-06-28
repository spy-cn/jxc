package com.spy.jxc.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
@TableName("t_return_list")
public class ReturnList implements Serializable {
    @TableId(value = "return_list_id", type = IdType.AUTO)
    private Integer returnListId;
    private String returnNumber;
    private String returnDate;
    private Float amountPaid;
    private Float amountPayable;
    private String remarks;
    private Integer state;
    private Integer supplierId;
    private Integer userId;

    @TableField(exist = false)
    private String supplierName;
    @TableField(exist = false)
    private String userName;
    @TableField(exist = false)
    private List<ReturnListGoods> goodsList;
    @TableField(exist = false)
    private String startDate;
    @TableField(exist = false)
    private String endDate;
}

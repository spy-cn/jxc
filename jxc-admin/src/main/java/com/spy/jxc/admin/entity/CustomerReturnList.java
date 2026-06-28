package com.spy.jxc.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
@TableName("t_customer_return_list")
public class CustomerReturnList implements Serializable {
    @TableId(value = "customer_return_list_id", type = IdType.AUTO)
    private Integer customerReturnListId;
    private String returnNumber;
    private String returnDate;
    private Float amountPaid;
    private Float amountPayable;
    private Integer state;
    private Integer customerId;
    private Integer userId;
    private String remarks;

    @TableField(exist = false)
    private String customerName;
    @TableField(exist = false)
    private String userName;
    @TableField(exist = false)
    private List<CustomerReturnListGoods> goodsList;
    @TableField(exist = false)
    private String startDate;
    @TableField(exist = false)
    private String endDate;
}

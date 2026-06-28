package com.spy.jxc.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
@TableName("t_overflow_list")
public class OverflowList implements Serializable {
    @TableId(value = "overflow_list_id", type = IdType.AUTO)
    private Integer overflowListId;
    private String overflowNumber;
    private String overflowDate;
    private String remarks;
    private Integer userId;

    @TableField(exist = false)
    private String userName;
    @TableField(exist = false)
    private List<OverflowListGoods> goodsList;
    @TableField(exist = false)
    private String startDate;
    @TableField(exist = false)
    private String endDate;
}

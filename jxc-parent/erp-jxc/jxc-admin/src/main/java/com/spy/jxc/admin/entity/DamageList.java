package com.spy.jxc.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
@TableName("t_damage_list")
public class DamageList implements Serializable {
    @TableId(value = "damage_list_id", type = IdType.AUTO)
    private Integer damageListId;
    private String damageNumber;
    private String damageDate;
    private String remarks;
    private Integer userId;

    @TableField(exist = false)
    private String userName;
    @TableField(exist = false)
    private List<DamageListGoods> goodsList;
    @TableField(exist = false)
    private String startDate;
    @TableField(exist = false)
    private String endDate;
}

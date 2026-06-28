package com.spy.jxc.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;

@Data
@TableName("t_unit")
public class Unit implements Serializable {
    @TableId(value = "unit_id", type = IdType.AUTO)
    private Integer unitId;
    private String unitName;
}

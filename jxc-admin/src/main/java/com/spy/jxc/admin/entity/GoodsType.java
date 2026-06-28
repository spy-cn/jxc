package com.spy.jxc.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
@TableName("t_goods_type")
public class GoodsType implements Serializable {
    @TableId(value = "goods_type_id", type = IdType.AUTO)
    private Integer goodsTypeId;
    private String goodsTypeName;
    private Integer pId;
    private Integer goodsTypeState;

    @TableField(exist = false)
    private List<GoodsType> children;
}

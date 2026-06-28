package com.spy.jxc.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;

@Data
@TableName("t_damage_list_goods")
public class DamageListGoods implements Serializable {
    @TableId(value = "damage_list_goods_id", type = IdType.AUTO)
    private Integer damageListGoodsId;
    private Integer goodsId;
    private String goodsCode;
    private String goodsName;
    private String goodsModel;
    private String goodsUnit;
    private Integer goodsNum;
    private Float price;
    private Float total;
    private Integer damageListId;
    private Integer goodsTypeId;
}

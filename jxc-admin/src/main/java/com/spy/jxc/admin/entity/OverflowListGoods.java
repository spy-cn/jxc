package com.spy.jxc.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;

@Data
@TableName("t_overflow_list_goods")
public class OverflowListGoods implements Serializable {
    @TableId(value = "overflow_list_goods_id", type = IdType.AUTO)
    private Integer overflowListGoodsId;
    private Integer goodsId;
    private String goodsCode;
    private String goodsName;
    private String goodsModel;
    private String goodsUnit;
    private Integer goodsNum;
    private Float price;
    private Float total;
    private Integer overflowListId;
    private Integer goodsTypeId;
}

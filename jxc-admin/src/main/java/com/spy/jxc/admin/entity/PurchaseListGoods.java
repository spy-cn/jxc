package com.spy.jxc.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;

@Data
@TableName("t_purchase_list_goods")
public class PurchaseListGoods implements Serializable {
    @TableId(value = "purchase_list_goods_id", type = IdType.AUTO)
    private Integer purchaseListGoodsId;
    private Integer goodsId;
    private String goodsCode;
    private String goodsName;
    private String goodsModel;
    private Integer goodsNum;
    private String goodsUnit;
    private Float price;
    private Float total;
    private Integer purchaseListId;
    private Integer goodsTypeId;
}

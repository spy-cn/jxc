package com.spy.jxc.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;

@Data
@TableName("t_sale_list_goods")
public class SaleListGoods implements Serializable {
    @TableId(value = "sale_list_goods_id", type = IdType.AUTO)
    private Integer saleListGoodsId;
    private Integer goodsId;
    private String goodsCode;
    private String goodsName;
    private String goodsModel;
    private Integer goodsNum;
    private String goodsUnit;
    private Float price;
    private Float total;
    private Integer saleListId;
    private Integer goodsTypeId;
}

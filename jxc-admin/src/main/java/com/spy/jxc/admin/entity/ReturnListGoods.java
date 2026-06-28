package com.spy.jxc.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;

@Data
@TableName("t_return_list_goods")
public class ReturnListGoods implements Serializable {
    @TableId(value = "return_list_goods_id", type = IdType.AUTO)
    private Integer returnListGoodsId;
    private Integer goodsId;
    private String goodsCode;
    private String goodsName;
    private String goodsModel;
    private Integer goodsNum;
    private String goodsUnit;
    private Float price;
    private Float total;
    private Integer returnListId;
    private Integer goodsTypeId;
}

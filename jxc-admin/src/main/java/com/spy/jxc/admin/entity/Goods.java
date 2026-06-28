package com.spy.jxc.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;

@Data
@TableName("t_goods")
public class Goods implements Serializable {
    @TableId(value = "goods_id", type = IdType.AUTO)
    private Integer goodsId;
    private String goodsCode;
    private String goodsName;
    private Integer inventoryQuantity;
    private Integer minNum;
    private String goodsModel;
    private String goodsProducer;
    private Float purchasingPrice;
    private Float lastPurchasingPrice;
    private String remarks;
    private Float sellingPrice;
    private Integer state;
    private String goodsUnit;
    private Integer goodsTypeId;

    @TableField(exist = false)
    private String goodsTypeName;
    @TableField(exist = false)
    private Integer alarm; // 1 表示库存报警
}

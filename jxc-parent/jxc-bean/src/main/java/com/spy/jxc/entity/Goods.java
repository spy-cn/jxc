package com.spy.jxc.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author spy
 * @since 2020-12-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_goods")
@ApiModel(value="Goods对象", description="")
public class Goods implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品编号id，主键")
    @TableId(value = "goods_id", type = IdType.AUTO)
    private Integer goodsId;

    @ApiModelProperty(value = "商品编码")
    private String goodsCode;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "库存数量")
    private Integer inventoryQuantity;

    @ApiModelProperty(value = "库存下限")
    private Integer minNum;

    @ApiModelProperty(value = "商品型号")
    private String goodsModel;

    @ApiModelProperty(value = "生产厂商")
    private String goodsProducer;

    @ApiModelProperty(value = "采购价格")
    private Float purchasingPrice;

    @ApiModelProperty(value = "上一次采购价格")
    private Float lastPurchasingPrice;

    @ApiModelProperty(value = "备注")
    private String remarks;

    @ApiModelProperty(value = "出售价格")
    private Float sellingPrice;

    @ApiModelProperty(value = "0表示初始值,1表示已入库，2表示有进货或销售单据")
    private Integer state;

    @ApiModelProperty(value = "商品单位")
    private String goodsUnit;

    @ApiModelProperty(value = "商品类别id，外键")
    private Integer goodsTypeId;


}

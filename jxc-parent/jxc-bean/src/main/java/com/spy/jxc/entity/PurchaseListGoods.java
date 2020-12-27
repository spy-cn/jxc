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
@TableName("t_purchase_list_goods")
@ApiModel(value="PurchaseListGoods对象", description="")
public class PurchaseListGoods implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "进货单商品列表id，主键")
    @TableId(value = "purchase_list_goods_id", type = IdType.AUTO)
    private Integer purchaseListGoodsId;

    @ApiModelProperty(value = "商品编号id，外键")
    private Integer goodsId;

    @ApiModelProperty(value = "商品编码")
    private String goodsCode;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "商品型号")
    private String goodsModel;

    @ApiModelProperty(value = "商品单位")
    private String goodsUnit;

    @ApiModelProperty(value = "进货数量")
    private Integer goodsNum;

    @ApiModelProperty(value = "商品单价")
    private Float price;

    @ApiModelProperty(value = "总金额")
    private Float total;

    @ApiModelProperty(value = "进货单id，外键")
    private Integer purchaseListId;

    @ApiModelProperty(value = "商品类别id，外键")
    private Integer goodsTypeId;


}

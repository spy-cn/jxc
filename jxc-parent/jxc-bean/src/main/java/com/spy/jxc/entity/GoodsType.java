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
@TableName("t_goods_type")
@ApiModel(value="GoodsType对象", description="")
public class GoodsType implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品类别id，主键")
    @TableId(value = "goods_type_id", type = IdType.AUTO)
    private Integer goodsTypeId;

    @ApiModelProperty(value = "商品类别名称")
    private String goodsTypeName;

    @ApiModelProperty(value = "父商品类别id")
    private Integer pId;

    @ApiModelProperty(value = "类别状态，0为叶子节点")
    private Integer goodsTypeState;


}

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
@TableName("t_sale_list")
@ApiModel(value="SaleList对象", description="")
public class SaleList implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "销售单id，主键")
    @TableId(value = "sale_list_id", type = IdType.AUTO)
    private Integer saleListId;

    @ApiModelProperty(value = "销售单号")
    private String saleNumber;

    @ApiModelProperty(value = "实付金额")
    private Float amountPaid;

    @ApiModelProperty(value = "应付金额")
    private Float amountPayable;

    @ApiModelProperty(value = "销售单创建日期")
    private String saleDate;

    @ApiModelProperty(value = "状态")
    private Integer state;

    @ApiModelProperty(value = "备注")
    private String remarks;

    @ApiModelProperty(value = "客户id，外键")
    private Integer customerId;

    @ApiModelProperty(value = "用户id，外键")
    private Integer userId;


}

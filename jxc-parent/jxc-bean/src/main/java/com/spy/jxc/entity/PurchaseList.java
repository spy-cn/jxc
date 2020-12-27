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
@TableName("t_purchase_list")
@ApiModel(value="PurchaseList对象", description="")
public class PurchaseList implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "进货单id，主键")
    @TableId(value = "purchase_list_id", type = IdType.AUTO)
    private Integer purchaseListId;

    @ApiModelProperty(value = "进货单号")
    private String purchaseNumber;

    @ApiModelProperty(value = "实付金额")
    private Float amountPaid;

    @ApiModelProperty(value = "应付金额")
    private Float amountPayable;

    @ApiModelProperty(value = "收货日期")
    private String purchaseDate;

    @ApiModelProperty(value = "备注")
    private String remarks;

    @ApiModelProperty(value = "状态")
    private Integer state;

    @ApiModelProperty(value = "供应商id，外键")
    private Integer supplierId;

    @ApiModelProperty(value = "用户id，外键")
    private Integer userId;


}

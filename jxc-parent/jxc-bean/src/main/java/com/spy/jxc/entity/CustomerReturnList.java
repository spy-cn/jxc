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
@TableName("t_customer_return_list")
@ApiModel(value="CustomerReturnList对象", description="")
public class CustomerReturnList implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "客户退货单id，主键")
    @TableId(value = "customer_return_list_id", type = IdType.AUTO)
    private Integer customerReturnListId;

    @ApiModelProperty(value = "退货单号")
    private String returnNumber;

    @ApiModelProperty(value = "退货日期")
    private String returnDate;

    @ApiModelProperty(value = "实付金额")
    private Float amountPaid;

    @ApiModelProperty(value = "应付金额")
    private Float amountPayable;

    @ApiModelProperty(value = "状态，是否付款")
    private Integer state;

    @ApiModelProperty(value = "客户编号id，外键")
    private Integer customerId;

    @ApiModelProperty(value = "操作员，用户id，外键")
    private Integer userId;

    @ApiModelProperty(value = "备注")
    private String remarks;


}

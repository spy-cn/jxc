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
@TableName("t_return_list")
@ApiModel(value="ReturnList对象", description="")
public class ReturnList implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "退货单id，主键")
    @TableId(value = "return_list_id", type = IdType.AUTO)
    private Integer returnListId;

    @ApiModelProperty(value = "退货单号")
    private String returnNumber;

    @ApiModelProperty(value = "退货日期")
    private String returnDate;

    @ApiModelProperty(value = "实退金额")
    private Float amountPaid;

    @ApiModelProperty(value = "应退金额")
    private Float amountPayable;

    @ApiModelProperty(value = "备注")
    private String remarks;

    @ApiModelProperty(value = "状态,1表示已退，2表示未退")
    private Integer state;

    @ApiModelProperty(value = "供应商id，外键")
    private Integer supplierId;

    @ApiModelProperty(value = "用户id，外键")
    private Integer userId;


}

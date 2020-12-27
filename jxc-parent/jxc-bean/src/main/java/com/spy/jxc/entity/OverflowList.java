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
@TableName("t_overflow_list")
@ApiModel(value="OverflowList对象", description="")
public class OverflowList implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品报溢单id，主键")
    @TableId(value = "overflow_list_id", type = IdType.AUTO)
    private Integer overflowListId;

    @ApiModelProperty(value = "商品报溢单号")
    private String overflowNumber;

    @ApiModelProperty(value = "报溢日期")
    private String overflowDate;

    @ApiModelProperty(value = "备注")
    private String remarks;

    @ApiModelProperty(value = "用户id，外键")
    private Integer userId;


}

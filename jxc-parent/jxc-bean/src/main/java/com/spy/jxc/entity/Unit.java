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
@TableName("t_unit")
@ApiModel(value="Unit对象", description="")
public class Unit implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品单位id，主键")
    @TableId(value = "unit_id", type = IdType.AUTO)
    private Integer unitId;

    @ApiModelProperty(value = "商品单位名称")
    private String unitName;


}

package com.spy.jxc.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;

@Data
@TableName("t_role_menu")
public class RoleMenu implements Serializable {
    @TableId(value = "role_menu_id", type = IdType.AUTO)
    private Integer roleMenuId;
    private Integer menuId;
    private Integer roleId;
}

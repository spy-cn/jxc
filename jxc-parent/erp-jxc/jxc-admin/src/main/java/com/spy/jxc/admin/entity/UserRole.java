package com.spy.jxc.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;

@Data
@TableName("t_user_role")
public class UserRole implements Serializable {
    @TableId(value = "user_role_id", type = IdType.AUTO)
    private Integer userRoleId;
    private Integer roleId;
    private Integer userId;
}

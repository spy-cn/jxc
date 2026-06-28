package com.spy.jxc.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@TableName("t_user")
public class User implements Serializable {
    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;
    private String userName;
    private String password;
    private String trueName;
    private String remarks;

    @TableField(exist = false)
    private String roles;

    @TableField(exist = false)
    private List<Integer> roleIds;
}

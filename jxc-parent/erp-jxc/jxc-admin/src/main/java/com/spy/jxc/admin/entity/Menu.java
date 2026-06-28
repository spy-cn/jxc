package com.spy.jxc.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
@TableName("t_menu")
public class Menu implements Serializable {
    @TableId(value = "menu_id", type = IdType.AUTO)
    private Integer menuId;
    private String menuIcon;
    private String menuName;
    private Integer pId;
    private Integer menuState;
    private String menuUrl;

    @TableField(exist = false)
    private List<Menu> children;
}

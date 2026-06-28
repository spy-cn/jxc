package com.spy.jxc.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_log")
public class Log implements Serializable {
    @TableId(value = "log_id", type = IdType.AUTO)
    private Integer logId;
    private String logType;
    private String content;
    private Date logDate;
    private Integer userId;

    @TableField(exist = false)
    private String userName;
    @TableField(exist = false)
    private String startDate;
    @TableField(exist = false)
    private String endDate;
}

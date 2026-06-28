package com.spy.jxc.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spy.jxc.admin.entity.Log;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface LogMapper extends BaseMapper<Log> {
    List<Log> listWithUser(@Param("log") Log query);
}

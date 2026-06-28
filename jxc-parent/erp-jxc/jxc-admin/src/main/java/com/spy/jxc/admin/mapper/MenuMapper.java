package com.spy.jxc.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spy.jxc.admin.entity.Menu;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface MenuMapper extends BaseMapper<Menu> {
    List<Menu> findMenusByUserId(@Param("userId") Integer userId);
}

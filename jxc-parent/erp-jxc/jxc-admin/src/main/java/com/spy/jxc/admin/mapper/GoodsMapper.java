package com.spy.jxc.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spy.jxc.admin.entity.Goods;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface GoodsMapper extends BaseMapper<Goods> {
    List<Goods> listAlarm();

    List<Goods> listWithTypeName(@Param("typeId") Integer typeId, @Param("codeOrName") String codeOrName);
}

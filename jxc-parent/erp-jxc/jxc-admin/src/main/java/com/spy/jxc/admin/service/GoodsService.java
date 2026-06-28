package com.spy.jxc.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.spy.jxc.admin.entity.Goods;
import java.util.List;

public interface GoodsService extends IService<Goods> {
    List<Goods> listAlarm();
    List<Goods> listWithTypeName(Integer typeId, String codeOrName);
    void deleteById(Integer goodsId);
}

package com.spy.jxc.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spy.jxc.admin.common.exception.BusinessException;
import com.spy.jxc.admin.entity.Goods;
import com.spy.jxc.admin.mapper.GoodsMapper;
import com.spy.jxc.admin.service.GoodsService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {

    @Override
    public List<Goods> listAlarm() {
        return baseMapper.listAlarm();
    }

    @Override
    public List<Goods> listWithTypeName(Integer typeId, String codeOrName) {
        return baseMapper.listWithTypeName(typeId, codeOrName);
    }

    @Override
    public void deleteById(Integer goodsId) {
        Goods goods = baseMapper.selectById(goodsId);
        if (goods == null) {
            throw new BusinessException("商品不存在");
        }
        if (goods.getState() != null && goods.getState() == 2) {
            throw new BusinessException("该商品已有进货或销售单据，不能删除");
        }
        baseMapper.deleteById(goodsId);
    }
}

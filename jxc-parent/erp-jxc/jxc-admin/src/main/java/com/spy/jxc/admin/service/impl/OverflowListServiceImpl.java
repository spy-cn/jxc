package com.spy.jxc.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spy.jxc.admin.common.jwt.CurrentUser;
import com.spy.jxc.admin.entity.*;
import com.spy.jxc.admin.mapper.*;
import com.spy.jxc.admin.service.LogService;
import com.spy.jxc.admin.service.OverflowListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OverflowListServiceImpl extends ServiceImpl<OverflowListMapper, OverflowList> implements OverflowListService {

    private final OverflowListGoodsMapper overflowListGoodsMapper;
    private final GoodsMapper goodsMapper;
    private final UserMapper userMapper;
    private final LogService logService;

    @Override
    @Transactional
    public void saveOverflow(OverflowList overflowList) {
        overflowList.setOverflowNumber("BY" + System.currentTimeMillis());
        overflowList.setOverflowDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        overflowList.setUserId(CurrentUser.getUserId());
        baseMapper.insert(overflowList);
        for (OverflowListGoods g : overflowList.getGoodsList()) {
            g.setOverflowListId(overflowList.getOverflowListId());
            g.setTotal(g.getPrice() * g.getGoodsNum());
            overflowListGoodsMapper.insert(g);
            Goods goods = goodsMapper.selectById(g.getGoodsId());
            if (goods != null) {
                goods.setInventoryQuantity(goods.getInventoryQuantity() + g.getGoodsNum());
                goodsMapper.updateById(goods);
            }
        }
        logService.saveLog("商品报溢", "新增报溢单：" + overflowList.getOverflowNumber(), CurrentUser.getUserId());
    }

    @Override
    public OverflowList getDetail(Integer id) {
        OverflowList ol = baseMapper.selectById(id);
        if (ol != null) {
            ol.setGoodsList(overflowListGoodsMapper.selectList(
                    new LambdaQueryWrapper<OverflowListGoods>().eq(OverflowListGoods::getOverflowListId, id)));
            fillName(ol);
        }
        return ol;
    }

    @Override
    public List<OverflowList> search(OverflowList query) {
        LambdaQueryWrapper<OverflowList> w = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(query.getStartDate())) w.ge(OverflowList::getOverflowDate, query.getStartDate());
        if (StringUtils.hasText(query.getEndDate())) w.le(OverflowList::getOverflowDate, query.getEndDate());
        w.orderByDesc(OverflowList::getOverflowDate);
        List<OverflowList> list = baseMapper.selectList(w);
        list.forEach(this::fillName);
        return list;
    }

    private void fillName(OverflowList ol) {
        if (ol.getUserId() != null) {
            User u = userMapper.selectById(ol.getUserId());
            if (u != null) ol.setUserName(u.getTrueName());
        }
    }
}

package com.spy.jxc.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spy.jxc.admin.common.jwt.CurrentUser;
import com.spy.jxc.admin.entity.*;
import com.spy.jxc.admin.mapper.*;
import com.spy.jxc.admin.service.DamageListService;
import com.spy.jxc.admin.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DamageListServiceImpl extends ServiceImpl<DamageListMapper, DamageList> implements DamageListService {

    private final DamageListGoodsMapper damageListGoodsMapper;
    private final GoodsMapper goodsMapper;
    private final UserMapper userMapper;
    private final LogService logService;

    @Override
    @Transactional
    public void saveDamage(DamageList damageList) {
        damageList.setDamageNumber("BS" + System.currentTimeMillis());
        damageList.setDamageDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        damageList.setUserId(CurrentUser.getUserId());
        baseMapper.insert(damageList);
        for (DamageListGoods g : damageList.getGoodsList()) {
            g.setDamageListId(damageList.getDamageListId());
            g.setTotal(g.getPrice() * g.getGoodsNum());
            damageListGoodsMapper.insert(g);
            Goods goods = goodsMapper.selectById(g.getGoodsId());
            if (goods != null) {
                goods.setInventoryQuantity(goods.getInventoryQuantity() - g.getGoodsNum());
                goodsMapper.updateById(goods);
            }
        }
        logService.saveLog("商品报损", "新增报损单：" + damageList.getDamageNumber(), CurrentUser.getUserId());
    }

    @Override
    public DamageList getDetail(Integer id) {
        DamageList dl = baseMapper.selectById(id);
        if (dl != null) {
            dl.setGoodsList(damageListGoodsMapper.selectList(
                    new LambdaQueryWrapper<DamageListGoods>().eq(DamageListGoods::getDamageListId, id)));
            fillName(dl);
        }
        return dl;
    }

    @Override
    public List<DamageList> search(DamageList query) {
        LambdaQueryWrapper<DamageList> w = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(query.getStartDate())) w.ge(DamageList::getDamageDate, query.getStartDate());
        if (StringUtils.hasText(query.getEndDate())) w.le(DamageList::getDamageDate, query.getEndDate());
        w.orderByDesc(DamageList::getDamageDate);
        List<DamageList> list = baseMapper.selectList(w);
        list.forEach(this::fillName);
        return list;
    }

    private void fillName(DamageList dl) {
        if (dl.getUserId() != null) {
            User u = userMapper.selectById(dl.getUserId());
            if (u != null) dl.setUserName(u.getTrueName());
        }
    }
}

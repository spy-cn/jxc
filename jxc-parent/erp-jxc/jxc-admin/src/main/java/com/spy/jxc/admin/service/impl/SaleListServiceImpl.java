package com.spy.jxc.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spy.jxc.admin.common.jwt.CurrentUser;
import com.spy.jxc.admin.entity.*;
import com.spy.jxc.admin.mapper.*;
import com.spy.jxc.admin.service.LogService;
import com.spy.jxc.admin.service.SaleListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleListServiceImpl extends ServiceImpl<SaleListMapper, SaleList> implements SaleListService {

    private final SaleListGoodsMapper saleListGoodsMapper;
    private final GoodsMapper goodsMapper;
    private final CustomerMapper customerMapper;
    private final UserMapper userMapper;
    private final LogService logService;

    @Override
    @Transactional
    public void saveSale(SaleList saleList) {
        String number = "XS" + System.currentTimeMillis();
        saleList.setSaleNumber(number);
        saleList.setSaleDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        saleList.setUserId(CurrentUser.getUserId());
        if (saleList.getState() == null) saleList.setState(2);
        if (saleList.getAmountPayable() == null) saleList.setAmountPayable(0f);
        if (saleList.getAmountPaid() == null) saleList.setAmountPaid(0f);
        baseMapper.insert(saleList);

        float amountPayable = 0f;
        for (SaleListGoods g : saleList.getGoodsList()) {
            g.setSaleListId(saleList.getSaleListId());
            g.setTotal(g.getPrice() * g.getGoodsNum());
            amountPayable += g.getTotal();
            saleListGoodsMapper.insert(g);
            // 减少库存
            Goods goods = goodsMapper.selectById(g.getGoodsId());
            if (goods != null) {
                goods.setInventoryQuantity(goods.getInventoryQuantity() - g.getGoodsNum());
                goods.setState(2);
                goodsMapper.updateById(goods);
            }
        }
        saleList.setAmountPayable(amountPayable);
        if (saleList.getAmountPaid() == null || saleList.getAmountPaid() == 0f) saleList.setAmountPaid(amountPayable);
        baseMapper.updateById(saleList);
        logService.saveLog("销售出库", "新增销售单：" + number, CurrentUser.getUserId());
    }

    @Override
    public SaleList getDetail(Integer id) {
        SaleList sl = baseMapper.selectById(id);
        if (sl != null) {
            sl.setGoodsList(saleListGoodsMapper.selectList(
                    new LambdaQueryWrapper<SaleListGoods>().eq(SaleListGoods::getSaleListId, id)));
            fillNames(sl);
        }
        return sl;
    }

    @Override
    public List<SaleList> search(SaleList query) {
        LambdaQueryWrapper<SaleList> w = new LambdaQueryWrapper<>();
        if (query.getCustomerId() != null) w.eq(SaleList::getCustomerId, query.getCustomerId());
        if (query.getState() != null) w.eq(SaleList::getState, query.getState());
        if (StringUtils.hasText(query.getStartDate())) w.ge(SaleList::getSaleDate, query.getStartDate());
        if (StringUtils.hasText(query.getEndDate())) w.le(SaleList::getSaleDate, query.getEndDate());
        w.orderByDesc(SaleList::getSaleDate);
        List<SaleList> list = baseMapper.selectList(w);
        list.forEach(this::fillNames);
        return list;
    }

    private void fillNames(SaleList sl) {
        if (sl.getCustomerId() != null) {
            Customer c = customerMapper.selectById(sl.getCustomerId());
            if (c != null) sl.setCustomerName(c.getCustomerName());
        }
        if (sl.getUserId() != null) {
            User u = userMapper.selectById(sl.getUserId());
            if (u != null) sl.setUserName(u.getTrueName());
        }
    }
}

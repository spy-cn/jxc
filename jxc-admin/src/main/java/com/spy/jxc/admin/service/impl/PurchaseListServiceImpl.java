package com.spy.jxc.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spy.jxc.admin.common.jwt.CurrentUser;
import com.spy.jxc.admin.entity.*;
import com.spy.jxc.admin.mapper.*;
import com.spy.jxc.admin.service.LogService;
import com.spy.jxc.admin.service.PurchaseListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseListServiceImpl extends ServiceImpl<PurchaseListMapper, PurchaseList> implements PurchaseListService {

    private final PurchaseListGoodsMapper purchaseListGoodsMapper;
    private final GoodsMapper goodsMapper;
    private final SupplierMapper supplierMapper;
    private final UserMapper userMapper;
    private final LogService logService;

    @Override
    @Transactional
    public void savePurchase(PurchaseList purchaseList) {
        String number = "JH" + System.currentTimeMillis();
        purchaseList.setPurchaseNumber(number);
        purchaseList.setPurchaseDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        purchaseList.setUserId(CurrentUser.getUserId());
        if (purchaseList.getState() == null) {
            purchaseList.setState(2); // 未结算
        }
        if (purchaseList.getAmountPayable() == null) purchaseList.setAmountPayable(0f);
        if (purchaseList.getAmountPaid() == null) purchaseList.setAmountPaid(0f);
        baseMapper.insert(purchaseList);

        float amountPayable = 0f;
        for (PurchaseListGoods g : purchaseList.getGoodsList()) {
            g.setPurchaseListId(purchaseList.getPurchaseListId());
            g.setTotal(g.getPrice() * g.getGoodsNum());
            amountPayable += g.getTotal();
            purchaseListGoodsMapper.insert(g);

            // 增加库存 & 更新采购价
            Goods goods = goodsMapper.selectById(g.getGoodsId());
            if (goods != null) {
                goods.setInventoryQuantity(goods.getInventoryQuantity() + g.getGoodsNum());
                goods.setLastPurchasingPrice(goods.getPurchasingPrice());
                goods.setPurchasingPrice(g.getPrice());
                goods.setState(2);
                goodsMapper.updateById(goods);
            }
        }
        purchaseList.setAmountPayable(amountPayable);
        if (purchaseList.getAmountPaid() == null || purchaseList.getAmountPaid() == 0f) {
            purchaseList.setAmountPaid(amountPayable);
        }
        baseMapper.updateById(purchaseList);

        logService.saveLog("进货入库", "新增进货单：" + number, CurrentUser.getUserId());
    }

    @Override
    public PurchaseList getDetail(Integer id) {
        PurchaseList pl = baseMapper.selectById(id);
        if (pl != null) {
            pl.setGoodsList(purchaseListGoodsMapper.selectList(
                    new LambdaQueryWrapper<PurchaseListGoods>().eq(PurchaseListGoods::getPurchaseListId, id)));
            if (pl.getSupplierId() != null) {
                Supplier s = supplierMapper.selectById(pl.getSupplierId());
                if (s != null) pl.setSupplierName(s.getSupplierName());
            }
            if (pl.getUserId() != null) {
                User u = userMapper.selectById(pl.getUserId());
                if (u != null) pl.setUserName(u.getTrueName());
            }
        }
        return pl;
    }

    @Override
    public List<PurchaseList> search(PurchaseList query) {
        LambdaQueryWrapper<PurchaseList> wrapper = new LambdaQueryWrapper<>();
        if (query.getSupplierId() != null) {
            wrapper.eq(PurchaseList::getSupplierId, query.getSupplierId());
        }
        if (query.getState() != null) {
            wrapper.eq(PurchaseList::getState, query.getState());
        }
        if (StringUtils.hasText(query.getStartDate())) {
            wrapper.ge(PurchaseList::getPurchaseDate, query.getStartDate());
        }
        if (StringUtils.hasText(query.getEndDate())) {
            wrapper.le(PurchaseList::getPurchaseDate, query.getEndDate());
        }
        wrapper.orderByDesc(PurchaseList::getPurchaseDate);
        List<PurchaseList> list = baseMapper.selectList(wrapper);
        for (PurchaseList pl : list) {
            if (pl.getSupplierId() != null) {
                Supplier s = supplierMapper.selectById(pl.getSupplierId());
                if (s != null) pl.setSupplierName(s.getSupplierName());
            }
            if (pl.getUserId() != null) {
                User u = userMapper.selectById(pl.getUserId());
                if (u != null) pl.setUserName(u.getTrueName());
            }
        }
        return list;
    }
}

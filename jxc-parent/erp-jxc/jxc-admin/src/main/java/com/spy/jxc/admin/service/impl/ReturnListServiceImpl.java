package com.spy.jxc.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spy.jxc.admin.common.jwt.CurrentUser;
import com.spy.jxc.admin.entity.*;
import com.spy.jxc.admin.mapper.*;
import com.spy.jxc.admin.service.LogService;
import com.spy.jxc.admin.service.ReturnListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReturnListServiceImpl extends ServiceImpl<ReturnListMapper, ReturnList> implements ReturnListService {

    private final ReturnListGoodsMapper returnListGoodsMapper;
    private final GoodsMapper goodsMapper;
    private final SupplierMapper supplierMapper;
    private final UserMapper userMapper;
    private final LogService logService;

    @Override
    @Transactional
    public void saveReturn(ReturnList returnList) {
        String number = "TH" + System.currentTimeMillis();
        returnList.setReturnNumber(number);
        returnList.setReturnDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        returnList.setUserId(CurrentUser.getUserId());
        if (returnList.getState() == null) returnList.setState(2);
        if (returnList.getAmountPayable() == null) returnList.setAmountPayable(0f);
        if (returnList.getAmountPaid() == null) returnList.setAmountPaid(0f);
        baseMapper.insert(returnList);

        float amountPayable = 0f;
        for (ReturnListGoods g : returnList.getGoodsList()) {
            g.setReturnListId(returnList.getReturnListId());
            g.setTotal(g.getPrice() * g.getGoodsNum());
            amountPayable += g.getTotal();
            returnListGoodsMapper.insert(g);
            // 减少库存
            Goods goods = goodsMapper.selectById(g.getGoodsId());
            if (goods != null) {
                goods.setInventoryQuantity(goods.getInventoryQuantity() - g.getGoodsNum());
                goodsMapper.updateById(goods);
            }
        }
        returnList.setAmountPayable(amountPayable);
        if (returnList.getAmountPaid() == null || returnList.getAmountPaid() == 0f) returnList.setAmountPaid(amountPayable);
        baseMapper.updateById(returnList);
        logService.saveLog("退货出库", "新增采购退货单：" + number, CurrentUser.getUserId());
    }

    @Override
    public ReturnList getDetail(Integer id) {
        ReturnList rl = baseMapper.selectById(id);
        if (rl != null) {
            rl.setGoodsList(returnListGoodsMapper.selectList(
                    new LambdaQueryWrapper<ReturnListGoods>().eq(ReturnListGoods::getReturnListId, id)));
            fillNames(rl);
        }
        return rl;
    }

    @Override
    public List<ReturnList> search(ReturnList query) {
        LambdaQueryWrapper<ReturnList> w = new LambdaQueryWrapper<>();
        if (query.getSupplierId() != null) w.eq(ReturnList::getSupplierId, query.getSupplierId());
        if (query.getState() != null) w.eq(ReturnList::getState, query.getState());
        if (StringUtils.hasText(query.getStartDate())) w.ge(ReturnList::getReturnDate, query.getStartDate());
        if (StringUtils.hasText(query.getEndDate())) w.le(ReturnList::getReturnDate, query.getEndDate());
        w.orderByDesc(ReturnList::getReturnDate);
        List<ReturnList> list = baseMapper.selectList(w);
        list.forEach(this::fillNames);
        return list;
    }

    private void fillNames(ReturnList rl) {
        if (rl.getSupplierId() != null) {
            Supplier s = supplierMapper.selectById(rl.getSupplierId());
            if (s != null) rl.setSupplierName(s.getSupplierName());
        }
        if (rl.getUserId() != null) {
            User u = userMapper.selectById(rl.getUserId());
            if (u != null) rl.setUserName(u.getTrueName());
        }
    }
}

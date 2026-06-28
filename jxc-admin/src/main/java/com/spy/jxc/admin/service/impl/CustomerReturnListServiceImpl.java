package com.spy.jxc.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spy.jxc.admin.common.jwt.CurrentUser;
import com.spy.jxc.admin.entity.*;
import com.spy.jxc.admin.mapper.*;
import com.spy.jxc.admin.service.CustomerReturnListService;
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
public class CustomerReturnListServiceImpl extends ServiceImpl<CustomerReturnListMapper, CustomerReturnList> implements CustomerReturnListService {

    private final CustomerReturnListGoodsMapper goodsMapper2;
    private final GoodsMapper goodsMapper;
    private final CustomerMapper customerMapper;
    private final UserMapper userMapper;
    private final LogService logService;

    @Override
    @Transactional
    public void saveCustomerReturn(CustomerReturnList crl) {
        String number = "XT" + System.currentTimeMillis();
        crl.setReturnNumber(number);
        crl.setReturnDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        crl.setUserId(CurrentUser.getUserId());
        if (crl.getState() == null) crl.setState(1);
        if (crl.getAmountPayable() == null) crl.setAmountPayable(0f);
        if (crl.getAmountPaid() == null) crl.setAmountPaid(0f);
        baseMapper.insert(crl);

        float amountPayable = 0f;
        for (CustomerReturnListGoods g : crl.getGoodsList()) {
            g.setCustomerReturnListId(crl.getCustomerReturnListId());
            g.setTotal(g.getPrice() * g.getGoodsNum());
            amountPayable += g.getTotal();
            goodsMapper2.insert(g);
            // 增加库存
            Goods goods = goodsMapper.selectById(g.getGoodsId());
            if (goods != null) {
                goods.setInventoryQuantity(goods.getInventoryQuantity() + g.getGoodsNum());
                goodsMapper.updateById(goods);
            }
        }
        crl.setAmountPayable(amountPayable);
        if (crl.getAmountPaid() == null || crl.getAmountPaid() == 0f) crl.setAmountPaid(amountPayable);
        baseMapper.updateById(crl);
        logService.saveLog("客户退货", "新增客户退货单：" + number, CurrentUser.getUserId());
    }

    @Override
    public CustomerReturnList getDetail(Integer id) {
        CustomerReturnList crl = baseMapper.selectById(id);
        if (crl != null) {
            crl.setGoodsList(goodsMapper2.selectList(
                    new LambdaQueryWrapper<CustomerReturnListGoods>().eq(CustomerReturnListGoods::getCustomerReturnListId, id)));
            fillNames(crl);
        }
        return crl;
    }

    @Override
    public List<CustomerReturnList> search(CustomerReturnList query) {
        LambdaQueryWrapper<CustomerReturnList> w = new LambdaQueryWrapper<>();
        if (query.getCustomerId() != null) w.eq(CustomerReturnList::getCustomerId, query.getCustomerId());
        if (query.getState() != null) w.eq(CustomerReturnList::getState, query.getState());
        if (StringUtils.hasText(query.getStartDate())) w.ge(CustomerReturnList::getReturnDate, query.getStartDate());
        if (StringUtils.hasText(query.getEndDate())) w.le(CustomerReturnList::getReturnDate, query.getEndDate());
        w.orderByDesc(CustomerReturnList::getReturnDate);
        List<CustomerReturnList> list = baseMapper.selectList(w);
        list.forEach(this::fillNames);
        return list;
    }

    private void fillNames(CustomerReturnList crl) {
        if (crl.getCustomerId() != null) {
            Customer c = customerMapper.selectById(crl.getCustomerId());
            if (c != null) crl.setCustomerName(c.getCustomerName());
        }
        if (crl.getUserId() != null) {
            User u = userMapper.selectById(crl.getUserId());
            if (u != null) crl.setUserName(u.getTrueName());
        }
    }
}

package com.spy.jxc.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.spy.jxc.admin.entity.SaleList;
import java.util.List;

public interface SaleListService extends IService<SaleList> {
    void saveSale(SaleList saleList);
    SaleList getDetail(Integer id);
    List<SaleList> search(SaleList query);
}

package com.spy.jxc.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.spy.jxc.admin.entity.PurchaseList;
import java.util.List;

public interface PurchaseListService extends IService<PurchaseList> {
    void savePurchase(PurchaseList purchaseList);
    PurchaseList getDetail(Integer id);
    List<PurchaseList> search(PurchaseList query);
}

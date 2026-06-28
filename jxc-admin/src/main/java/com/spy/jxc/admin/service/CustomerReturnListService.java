package com.spy.jxc.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.spy.jxc.admin.entity.CustomerReturnList;
import java.util.List;

public interface CustomerReturnListService extends IService<CustomerReturnList> {
    void saveCustomerReturn(CustomerReturnList customerReturnList);
    CustomerReturnList getDetail(Integer id);
    List<CustomerReturnList> search(CustomerReturnList query);
}

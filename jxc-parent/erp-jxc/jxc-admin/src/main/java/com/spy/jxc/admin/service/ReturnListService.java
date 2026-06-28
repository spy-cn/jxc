package com.spy.jxc.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.spy.jxc.admin.entity.ReturnList;
import java.util.List;

public interface ReturnListService extends IService<ReturnList> {
    void saveReturn(ReturnList returnList);
    ReturnList getDetail(Integer id);
    List<ReturnList> search(ReturnList query);
}

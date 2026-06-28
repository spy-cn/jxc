package com.spy.jxc.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.spy.jxc.admin.entity.OverflowList;
import java.util.List;

public interface OverflowListService extends IService<OverflowList> {
    void saveOverflow(OverflowList overflowList);
    OverflowList getDetail(Integer id);
    List<OverflowList> search(OverflowList query);
}

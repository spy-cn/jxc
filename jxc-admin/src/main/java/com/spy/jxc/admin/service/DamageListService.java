package com.spy.jxc.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.spy.jxc.admin.entity.DamageList;
import java.util.List;

public interface DamageListService extends IService<DamageList> {
    void saveDamage(DamageList damageList);
    DamageList getDetail(Integer id);
    List<DamageList> search(DamageList query);
}

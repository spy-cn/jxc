package com.spy.jxc.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.spy.jxc.admin.entity.Role;

public interface RoleService extends IService<Role> {
    void saveWithMenus(Role role);
    void updateWithMenus(Role role);
    void removeWithMenus(Integer roleId);
}

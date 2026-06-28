package com.spy.jxc.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.spy.jxc.admin.entity.User;

public interface UserService extends IService<User> {
    void saveWithRoles(User user);
    void updateWithRoles(User user);
}

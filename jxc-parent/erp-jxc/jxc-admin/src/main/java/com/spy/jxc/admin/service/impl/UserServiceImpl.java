package com.spy.jxc.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spy.jxc.admin.entity.User;
import com.spy.jxc.admin.entity.UserRole;
import com.spy.jxc.admin.mapper.UserMapper;
import com.spy.jxc.admin.mapper.UserRoleMapper;
import com.spy.jxc.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final UserRoleMapper userRoleMapper;

    @Override
    @Transactional
    public void saveWithRoles(User user) {
        baseMapper.insert(user);
        saveRoles(user);
    }

    @Override
    @Transactional
    public void updateWithRoles(User user) {
        baseMapper.updateById(user);
        userRoleMapper.delete(new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<UserRole>()
                .eq(UserRole::getUserId, user.getUserId()));
        saveRoles(user);
    }

    private void saveRoles(User user) {
        List<Integer> roleIds = user.getRoleIds();
        if (roleIds == null) return;
        for (Integer roleId : roleIds) {
            UserRole ur = new UserRole();
            ur.setUserId(user.getUserId());
            ur.setRoleId(roleId);
            userRoleMapper.insert(ur);
        }
    }
}

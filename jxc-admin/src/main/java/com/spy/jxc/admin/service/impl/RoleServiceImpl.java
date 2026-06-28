package com.spy.jxc.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spy.jxc.admin.entity.Role;
import com.spy.jxc.admin.entity.RoleMenu;
import com.spy.jxc.admin.mapper.RoleMapper;
import com.spy.jxc.admin.mapper.RoleMenuMapper;
import com.spy.jxc.admin.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    private final RoleMenuMapper roleMenuMapper;

    @Override
    @Transactional
    public void saveWithMenus(Role role) {
        baseMapper.insert(role);
        saveMenus(role);
    }

    @Override
    @Transactional
    public void updateWithMenus(Role role) {
        baseMapper.updateById(role);
        roleMenuMapper.delete(new LambdaQueryWrapper<RoleMenu>()
                .eq(RoleMenu::getRoleId, role.getRoleId()));
        saveMenus(role);
    }

    @Override
    @Transactional
    public void removeWithMenus(Integer roleId) {
        roleMenuMapper.delete(new LambdaQueryWrapper<RoleMenu>().eq(RoleMenu::getRoleId, roleId));
        baseMapper.deleteById(roleId);
    }

    private void saveMenus(Role role) {
        List<Integer> menuIds = role.getMenuIds();
        if (menuIds == null) return;
        for (Integer menuId : menuIds) {
            RoleMenu rm = new RoleMenu();
            rm.setRoleId(role.getRoleId());
            rm.setMenuId(menuId);
            roleMenuMapper.insert(rm);
        }
    }
}

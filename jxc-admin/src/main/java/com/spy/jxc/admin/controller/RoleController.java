package com.spy.jxc.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.spy.jxc.admin.annotation.SysLog;
import com.spy.jxc.admin.common.result.Result;
import com.spy.jxc.admin.entity.Role;
import com.spy.jxc.admin.entity.RoleMenu;
import com.spy.jxc.admin.mapper.RoleMenuMapper;
import com.spy.jxc.admin.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;
    private final RoleMenuMapper roleMenuMapper;

    @GetMapping("/list")
    public Result<List<Role>> list() {
        List<Role> list = roleService.list();
        list.forEach(r -> {
            List<RoleMenu> rms = roleMenuMapper.selectList(new LambdaQueryWrapper<RoleMenu>().eq(RoleMenu::getRoleId, r.getRoleId()));
            r.setMenuIds(rms.stream().map(RoleMenu::getMenuId).collect(Collectors.toList()));
        });
        return Result.success(list);
    }

    @SysLog(type = "角色", value = "新增或修改角色")
    @PostMapping
    public Result<?> save(@RequestBody Role role) {
        if (role.getRoleId() == null) {
            roleService.saveWithMenus(role);
        } else {
            roleService.updateWithMenus(role);
        }
        return Result.success();
    }

    @SysLog(type = "角色", value = "删除角色")
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Integer id) {
        roleService.removeWithMenus(id);
        return Result.success();
    }
}

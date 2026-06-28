package com.spy.jxc.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.spy.jxc.admin.annotation.SysLog;
import com.spy.jxc.admin.common.result.Result;
import com.spy.jxc.admin.entity.User;
import com.spy.jxc.admin.entity.UserRole;
import com.spy.jxc.admin.mapper.UserRoleMapper;
import com.spy.jxc.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRoleMapper userRoleMapper;

    @GetMapping("/list")
    public Result<List<User>> list(String userName) {
        LambdaQueryWrapper<User> w = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(userName)) w.like(User::getUserName, userName);
        List<User> list = userService.list(w);
        list.forEach(u -> {
            List<UserRole> urs = userRoleMapper.selectList(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, u.getUserId()));
            u.setRoleIds(urs.stream().map(UserRole::getRoleId).collect(Collectors.toList()));
        });
        return Result.success(list);
    }

    @SysLog(type = "用户", value = "新增或修改用户")
    @PostMapping
    public Result<?> save(@RequestBody User user) {
        if (user.getUserId() == null) {
            userService.saveWithRoles(user);
        } else {
            userService.updateWithRoles(user);
        }
        return Result.success();
    }

    @SysLog(type = "用户", value = "删除用户")
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Integer id) {
        userService.removeById(id);
        return Result.success();
    }

    @SysLog(type = "用户", value = "修改密码")
    @PutMapping("/password")
    public Result<?> updatePassword(@RequestBody User user) {
        User db = userService.getById(user.getUserId());
        db.setPassword(user.getPassword());
        userService.updateById(db);
        return Result.success();
    }
}

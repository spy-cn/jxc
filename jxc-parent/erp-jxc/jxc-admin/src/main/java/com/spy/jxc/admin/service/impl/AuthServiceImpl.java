package com.spy.jxc.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.spy.jxc.admin.common.exception.BusinessException;
import com.spy.jxc.admin.common.jwt.JwtUtil;
import com.spy.jxc.admin.entity.Menu;
import com.spy.jxc.admin.entity.User;
import com.spy.jxc.admin.mapper.MenuMapper;
import com.spy.jxc.admin.mapper.UserMapper;
import com.spy.jxc.admin.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;
    private final MenuMapper menuMapper;
    private final com.spy.jxc.admin.service.LogService logService;

    @Override
    public Map<String, Object> login(String userName, String password) {
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUserName, userName));
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        if (!user.getPassword().equals(password)) {
            throw new BusinessException("用户名或密码错误");
        }
        String token = jwtUtil.generate(user.getUserId(), user.getUserName());

        // 记录登录日志
        logService.saveLog("登录登出", "用户登录：" + user.getUserName(), user.getUserId());

        List<Menu> menus;
        if ("admin".equals(userName)) {
            menus = menuMapper.selectList(null);
        } else {
            menus = menuMapper.findMenusByUserId(user.getUserId());
        }
        List<Menu> tree = buildTree(menus, -1);
        // 若顶层为单一虚拟根节点（系统菜单），则下钻一层，使顶层直接展示六大模块
        if (tree.size() == 1 && tree.get(0).getChildren() != null && !tree.get(0).getChildren().isEmpty()) {
            tree = tree.get(0).getChildren();
        }

        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("userId", user.getUserId());
        userInfo.put("userName", user.getUserName());
        userInfo.put("trueName", user.getTrueName());
        userInfo.put("menus", tree);
        result.put("userInfo", userInfo);
        return result;
    }

    private List<Menu> buildTree(List<Menu> all, Integer parentId) {
        return all.stream()
                .filter(m -> Objects.equals(m.getPId(), parentId))
                .peek(m -> m.setChildren(buildTree(all, m.getMenuId())))
                .collect(Collectors.toList());
    }
}

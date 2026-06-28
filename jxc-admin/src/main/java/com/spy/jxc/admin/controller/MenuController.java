package com.spy.jxc.admin.controller;

import com.spy.jxc.admin.annotation.SysLog;
import com.spy.jxc.admin.common.result.Result;
import com.spy.jxc.admin.entity.Menu;
import com.spy.jxc.admin.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;

    @GetMapping("/list")
    public Result<List<Menu>> list() {
        return Result.success(menuService.list());
    }

    @SysLog(type = "菜单", value = "新增或修改菜单")
    @PostMapping
    public Result<?> save(@RequestBody Menu menu) {
        menuService.saveOrUpdate(menu);
        return Result.success();
    }

    @SysLog(type = "菜单", value = "删除菜单")
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Integer id) {
        menuService.removeById(id);
        return Result.success();
    }
}

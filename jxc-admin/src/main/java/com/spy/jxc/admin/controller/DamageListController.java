package com.spy.jxc.admin.controller;

import com.spy.jxc.admin.annotation.SysLog;
import com.spy.jxc.admin.common.result.Result;
import com.spy.jxc.admin.entity.DamageList;
import com.spy.jxc.admin.service.DamageListService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/damage")
@RequiredArgsConstructor
public class DamageListController {
    private final DamageListService damageListService;

    @GetMapping("/list")
    public Result<List<DamageList>> list(DamageList query) {
        return Result.success(damageListService.search(query));
    }

    @GetMapping("/{id}")
    public Result<DamageList> detail(@PathVariable Integer id) {
        return Result.success(damageListService.getDetail(id));
    }

    @SysLog(type = "商品报损", value = "新增或修改商品报损")
    @PostMapping
    public Result<?> save(@RequestBody DamageList damageList) {
        damageListService.saveDamage(damageList);
        return Result.success();
    }
}

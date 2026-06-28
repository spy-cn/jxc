package com.spy.jxc.admin.controller;

import com.spy.jxc.admin.annotation.SysLog;
import com.spy.jxc.admin.common.result.Result;
import com.spy.jxc.admin.entity.Unit;
import com.spy.jxc.admin.service.UnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/unit")
@RequiredArgsConstructor
public class UnitController {

    private final UnitService unitService;

    @GetMapping("/list")
    public Result<List<Unit>> list() {
        return Result.success(unitService.list());
    }

    @SysLog(type = "商品单位", value = "新增或修改商品单位")
    @PostMapping
    public Result<?> save(@RequestBody Unit unit) {
        unitService.saveOrUpdate(unit);
        return Result.success();
    }

    @SysLog(type = "商品单位", value = "删除商品单位")
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Integer id) {
        unitService.removeById(id);
        return Result.success();
    }
}

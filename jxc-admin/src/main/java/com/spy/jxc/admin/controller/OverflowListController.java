package com.spy.jxc.admin.controller;

import com.spy.jxc.admin.annotation.SysLog;
import com.spy.jxc.admin.common.result.Result;
import com.spy.jxc.admin.entity.OverflowList;
import com.spy.jxc.admin.service.OverflowListService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/overflow")
@RequiredArgsConstructor
public class OverflowListController {
    private final OverflowListService overflowListService;

    @GetMapping("/list")
    public Result<List<OverflowList>> list(OverflowList query) {
        return Result.success(overflowListService.search(query));
    }

    @GetMapping("/{id}")
    public Result<OverflowList> detail(@PathVariable Integer id) {
        return Result.success(overflowListService.getDetail(id));
    }

    @SysLog(type = "商品报溢", value = "新增或修改商品报溢")
    @PostMapping
    public Result<?> save(@RequestBody OverflowList overflowList) {
        overflowListService.saveOverflow(overflowList);
        return Result.success();
    }
}

package com.spy.jxc.admin.controller;

import com.spy.jxc.admin.annotation.SysLog;
import com.spy.jxc.admin.common.result.Result;
import com.spy.jxc.admin.entity.SaleList;
import com.spy.jxc.admin.service.SaleListService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/sale")
@RequiredArgsConstructor
public class SaleListController {
    private final SaleListService saleListService;

    @GetMapping("/list")
    public Result<List<SaleList>> list(SaleList query) {
        return Result.success(saleListService.search(query));
    }

    @GetMapping("/{id}")
    public Result<SaleList> detail(@PathVariable Integer id) {
        return Result.success(saleListService.getDetail(id));
    }

    @SysLog(type = "销售出库", value = "新增或修改销售出库")
    @PostMapping
    public Result<?> save(@RequestBody SaleList saleList) {
        saleListService.saveSale(saleList);
        return Result.success();
    }

    @SysLog(type = "销售出库", value = "结算销售出库")
    @PutMapping("/settle/{id}")
    public Result<?> settle(@PathVariable Integer id) {
        SaleList sl = saleListService.getById(id);
        sl.setState(1);
        saleListService.updateById(sl);
        return Result.success();
    }
}

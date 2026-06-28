package com.spy.jxc.admin.controller;

import com.spy.jxc.admin.annotation.SysLog;
import com.spy.jxc.admin.common.result.Result;
import com.spy.jxc.admin.entity.PurchaseList;
import com.spy.jxc.admin.service.PurchaseListService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/purchase")
@RequiredArgsConstructor
public class PurchaseListController {
    private final PurchaseListService purchaseListService;

    @GetMapping("/list")
    public Result<List<PurchaseList>> list(PurchaseList query) {
        return Result.success(purchaseListService.search(query));
    }

    @GetMapping("/{id}")
    public Result<PurchaseList> detail(@PathVariable Integer id) {
        return Result.success(purchaseListService.getDetail(id));
    }

    @SysLog(type = "进货入库", value = "新增或修改进货入库")
    @PostMapping
    public Result<?> save(@RequestBody PurchaseList purchaseList) {
        purchaseListService.savePurchase(purchaseList);
        return Result.success();
    }

    @SysLog(type = "进货入库", value = "结算进货入库")
    @PutMapping("/settle/{id}")
    public Result<?> settle(@PathVariable Integer id) {
        PurchaseList pl = purchaseListService.getById(id);
        pl.setState(1);
        purchaseListService.updateById(pl);
        return Result.success();
    }
}

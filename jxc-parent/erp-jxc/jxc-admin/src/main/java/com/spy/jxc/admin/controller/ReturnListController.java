package com.spy.jxc.admin.controller;

import com.spy.jxc.admin.annotation.SysLog;
import com.spy.jxc.admin.common.result.Result;
import com.spy.jxc.admin.entity.ReturnList;
import com.spy.jxc.admin.service.ReturnListService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/return")
@RequiredArgsConstructor
public class ReturnListController {
    private final ReturnListService returnListService;

    @GetMapping("/list")
    public Result<List<ReturnList>> list(ReturnList query) {
        return Result.success(returnListService.search(query));
    }

    @GetMapping("/{id}")
    public Result<ReturnList> detail(@PathVariable Integer id) {
        return Result.success(returnListService.getDetail(id));
    }

    @SysLog(type = "采购退货", value = "新增或修改采购退货")
    @PostMapping
    public Result<?> save(@RequestBody ReturnList returnList) {
        returnListService.saveReturn(returnList);
        return Result.success();
    }

    @SysLog(type = "采购退货", value = "结算采购退货")
    @PutMapping("/settle/{id}")
    public Result<?> settle(@PathVariable Integer id) {
        ReturnList rl = returnListService.getById(id);
        rl.setState(1);
        returnListService.updateById(rl);
        return Result.success();
    }
}

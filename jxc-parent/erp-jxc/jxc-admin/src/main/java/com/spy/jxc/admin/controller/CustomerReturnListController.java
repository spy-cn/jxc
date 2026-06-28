package com.spy.jxc.admin.controller;

import com.spy.jxc.admin.annotation.SysLog;
import com.spy.jxc.admin.common.result.Result;
import com.spy.jxc.admin.entity.CustomerReturnList;
import com.spy.jxc.admin.service.CustomerReturnListService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/customerReturn")
@RequiredArgsConstructor
public class CustomerReturnListController {
    private final CustomerReturnListService customerReturnListService;

    @GetMapping("/list")
    public Result<List<CustomerReturnList>> list(CustomerReturnList query) {
        return Result.success(customerReturnListService.search(query));
    }

    @GetMapping("/{id}")
    public Result<CustomerReturnList> detail(@PathVariable Integer id) {
        return Result.success(customerReturnListService.getDetail(id));
    }

    @SysLog(type = "客户退货", value = "新增或修改客户退货")
    @PostMapping
    public Result<?> save(@RequestBody CustomerReturnList customerReturnList) {
        customerReturnListService.saveCustomerReturn(customerReturnList);
        return Result.success();
    }
}

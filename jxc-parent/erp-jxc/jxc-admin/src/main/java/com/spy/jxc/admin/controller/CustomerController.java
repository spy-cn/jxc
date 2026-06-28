package com.spy.jxc.admin.controller;

import com.spy.jxc.admin.annotation.SysLog;
import com.spy.jxc.admin.common.result.Result;
import com.spy.jxc.admin.entity.Customer;
import com.spy.jxc.admin.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/list")
    public Result<List<Customer>> list() {
        return Result.success(customerService.list());
    }

    @SysLog(type = "客户", value = "新增或修改客户")
    @PostMapping
    public Result<?> save(@RequestBody Customer customer) {
        customerService.saveOrUpdate(customer);
        return Result.success();
    }

    @SysLog(type = "客户", value = "删除客户")
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Integer id) {
        customerService.removeById(id);
        return Result.success();
    }
}

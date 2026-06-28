package com.spy.jxc.admin.controller;

import com.spy.jxc.admin.annotation.SysLog;
import com.spy.jxc.admin.common.result.Result;
import com.spy.jxc.admin.entity.Supplier;
import com.spy.jxc.admin.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/supplier")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;

    @GetMapping("/list")
    public Result<List<Supplier>> list() {
        return Result.success(supplierService.list());
    }

    @SysLog(type = "供应商", value = "新增或修改供应商")
    @PostMapping
    public Result<?> save(@RequestBody Supplier supplier) {
        supplierService.saveOrUpdate(supplier);
        return Result.success();
    }

    @SysLog(type = "供应商", value = "删除供应商")
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Integer id) {
        supplierService.removeById(id);
        return Result.success();
    }
}

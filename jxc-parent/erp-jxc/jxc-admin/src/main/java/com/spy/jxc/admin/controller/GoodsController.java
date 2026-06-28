package com.spy.jxc.admin.controller;

import com.spy.jxc.admin.annotation.SysLog;
import com.spy.jxc.admin.common.result.Result;
import com.spy.jxc.admin.entity.Goods;
import com.spy.jxc.admin.service.GoodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/goods")
@RequiredArgsConstructor
public class GoodsController {

    private final GoodsService goodsService;

    @GetMapping("/list")
    public Result<List<Goods>> list(Integer typeId, String codeOrName) {
        return Result.success(goodsService.listWithTypeName(typeId, codeOrName));
    }

    @GetMapping("/alarm")
    public Result<List<Goods>> alarm() {
        return Result.success(goodsService.listAlarm());
    }

    @SysLog(type = "商品", value = "新增或修改商品")
    @PostMapping
    public Result<?> save(@RequestBody Goods goods) {
        if (goods.getState() == null) goods.setState(1);
        goodsService.saveOrUpdate(goods);
        return Result.success();
    }

    @SysLog(type = "商品", value = "删除商品")
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Integer id) {
        goodsService.deleteById(id);
        return Result.success();
    }
}

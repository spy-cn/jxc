package com.spy.jxc.admin.controller;

import com.spy.jxc.admin.common.exception.BusinessException;
import com.spy.jxc.admin.annotation.SysLog;
import com.spy.jxc.admin.common.result.Result;
import com.spy.jxc.admin.entity.GoodsType;
import com.spy.jxc.admin.service.GoodsTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/goodsType")
@RequiredArgsConstructor
public class GoodsTypeController {

    private final GoodsTypeService goodsTypeService;

    @GetMapping("/tree")
    public Result<List<GoodsType>> tree() {
        List<GoodsType> all = goodsTypeService.list();
        return Result.success(buildTree(all, -1));
    }

    private List<GoodsType> buildTree(List<GoodsType> all, Integer pid) {
        return all.stream()
                .filter(t -> Objects.equals(t.getPId(), pid))
                .peek(t -> t.setChildren(buildTree(all, t.getGoodsTypeId())))
                .collect(Collectors.toList());
    }

    @SysLog(type = "商品类别", value = "新增或修改商品类别")
    @PostMapping
    public Result<?> save(@RequestBody GoodsType goodsType) {
        if (goodsType.getPId() == null) goodsType.setPId(-1);
        if (goodsType.getGoodsTypeState() == null) goodsType.setGoodsTypeState(1);
        goodsTypeService.saveOrUpdate(goodsType);
        return Result.success();
    }

    @SysLog(type = "商品类别", value = "删除商品类别")
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Integer id) {
        long childCount = goodsTypeService.count(new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<GoodsType>().eq(GoodsType::getPId, id));
        if (childCount > 0) throw new BusinessException("该类别下有子类别，不能删除");
        goodsTypeService.removeById(id);
        return Result.success();
    }
}

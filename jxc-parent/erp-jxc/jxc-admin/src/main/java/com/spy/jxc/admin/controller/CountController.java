package com.spy.jxc.admin.controller;

import com.spy.jxc.admin.common.result.Result;
import com.spy.jxc.admin.service.CountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/count")
@RequiredArgsConstructor
public class CountController {

    private final CountService countService;

    @GetMapping("/purchase")
    public Result<List<Map<String, Object>>> purchase(@RequestParam(required = false) String startDate,
                                                      @RequestParam(required = false) String endDate,
                                                      @RequestParam(required = false) Integer typeId) {
        return Result.success(countService.purchaseCount(startDate, endDate, typeId));
    }

    @GetMapping("/sale")
    public Result<List<Map<String, Object>>> sale(@RequestParam(required = false) String startDate,
                                                   @RequestParam(required = false) String endDate,
                                                   @RequestParam(required = false) Integer typeId) {
        return Result.success(countService.saleCount(startDate, endDate, typeId));
    }

    @GetMapping("/supplier")
    public Result<List<Map<String, Object>>> supplier(@RequestParam(required = false) String startDate,
                                                       @RequestParam(required = false) String endDate) {
        return Result.success(countService.supplierCount(startDate, endDate));
    }

    @GetMapping("/customer")
    public Result<List<Map<String, Object>>> customer(@RequestParam(required = false) String startDate,
                                                       @RequestParam(required = false) String endDate) {
        return Result.success(countService.customerCount(startDate, endDate));
    }

    @GetMapping("/saleDay")
    public Result<List<Map<String, Object>>> saleDay(@RequestParam(required = false) String startDate,
                                                      @RequestParam(required = false) String endDate) {
        return Result.success(countService.saleDayCount(startDate, endDate));
    }

    @GetMapping("/saleMonth")
    public Result<List<Map<String, Object>>> saleMonth(@RequestParam(required = false) String startDate,
                                                        @RequestParam(required = false) String endDate) {
        return Result.success(countService.saleMonthCount(startDate, endDate));
    }
}

package com.spy.jxc.admin.controller;

import com.spy.jxc.admin.common.result.Result;
import com.spy.jxc.admin.entity.Log;
import com.spy.jxc.admin.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/log")
@RequiredArgsConstructor
public class LogController {

    private final LogService logService;

    @GetMapping("/list")
    public Result<List<Log>> list(Log query) {
        return Result.success(logService.listWithUser(query));
    }
}

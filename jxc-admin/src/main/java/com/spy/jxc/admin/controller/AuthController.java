package com.spy.jxc.admin.controller;

import com.spy.jxc.admin.common.result.Result;
import com.spy.jxc.admin.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody Map<String, String> body) {
        return Result.success(authService.login(body.get("userName"), body.get("password")));
    }
}

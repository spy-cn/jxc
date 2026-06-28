package com.spy.jxc.admin.config;

import com.spy.jxc.admin.common.exception.BusinessException;
import com.spy.jxc.admin.common.jwt.CurrentUser;
import com.spy.jxc.admin.common.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            throw new BusinessException("未登录或登录已过期");
        }
        String token = header.substring(7);
        try {
            Claims claims = jwtUtil.parse(token);
            Integer userId = claims.get("userId", Integer.class);
            String userName = claims.get("userName", String.class);
            CurrentUser.set(userId, userName);
            return true;
        } catch (Exception e) {
            throw new BusinessException("登录令牌无效，请重新登录");
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        CurrentUser.clear();
    }
}

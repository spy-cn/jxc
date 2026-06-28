package com.spy.jxc.admin.service;

import java.util.Map;

public interface AuthService {
    Map<String, Object> login(String userName, String password);
}

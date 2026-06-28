package com.spy.jxc.admin.common.jwt;

import java.util.Optional;

public class CurrentUser {
    private static final ThreadLocal<Integer> USER_ID = new ThreadLocal<>();
    private static final ThreadLocal<String> USER_NAME = new ThreadLocal<>();

    public static void set(Integer userId, String userName) {
        USER_ID.set(userId);
        USER_NAME.set(userName);
    }

    public static Integer getUserId() {
        return USER_ID.get();
    }

    public static Optional<String> getUserName() {
        return Optional.ofNullable(USER_NAME.get());
    }

    public static void clear() {
        USER_ID.remove();
        USER_NAME.remove();
    }
}

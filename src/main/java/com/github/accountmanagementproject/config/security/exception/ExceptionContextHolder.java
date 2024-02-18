package com.github.accountmanagementproject.config.security.exception;


import java.util.HashMap;
import java.util.Map;

public class ExceptionContextHolder {
    private static final ThreadLocal<Map<String, String>> JWT_EXCEPTION_THREAD_LOCAL = new ThreadLocal<>();
    public static void setExceptionMessage(String system, String custom) {
        Map<String, String> messageMap = new HashMap<>();
        messageMap.put("systemMessage", system);
        messageMap.put("customMessage", custom);
        JWT_EXCEPTION_THREAD_LOCAL.set(messageMap);
    }

    public static Map<String, String> getExceptionMessage() {
        return JWT_EXCEPTION_THREAD_LOCAL.get();
    }

    public static void removeLocalThread(){
        JWT_EXCEPTION_THREAD_LOCAL.remove();
    }
}

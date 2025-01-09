package com.github.accountmanagementproject.config.properties.server;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ServerUrlFields {
    private String scheme;
    private String serverName;
    private int port;
    public static ServerUrlFields fromRequest(HttpServletRequest request){
        String forwardedProto = request.getHeader("X-Forwarded-Proto");
        boolean isHttps = "https".equalsIgnoreCase(forwardedProto);
        return new ServerUrlFields(
                isHttps ? "https" : "http",
                request.getServerName(),
                isHttps? 443 : request.getServerPort());
    }
}

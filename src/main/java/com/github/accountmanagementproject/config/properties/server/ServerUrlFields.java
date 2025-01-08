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
        return new ServerUrlFields(request.getScheme(), request.getServerName(), request.getServerPort());
    }
}

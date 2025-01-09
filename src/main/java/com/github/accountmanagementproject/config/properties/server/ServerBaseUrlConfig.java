package com.github.accountmanagementproject.config.properties.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class ServerBaseUrlConfig {
    @Bean
    public Function<ServerUrlFields, String> requestBaseUrlProvider() {
        return fields -> {

            String scheme = fields.getScheme();   // "http" 또는 "https"
            System.out.println("찍힘 : "+scheme);
            String serverName = fields.getServerName(); // "localhost" 또는 "sirimp.kro.kr"
            int serverPort = fields.getPort(); // 8080 또는 443 등

            String baseUrl = scheme + "://" + serverName;

            // 기본 포트(80, 443)가 아니라면 포트 추가
            if ((scheme.equals("http") && serverPort != 80) || (scheme.equals("https") && serverPort != 443)) {
                baseUrl += ":" + serverPort;
            }

            return baseUrl;
        };
    }
}

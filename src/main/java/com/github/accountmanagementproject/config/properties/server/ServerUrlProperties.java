package com.github.accountmanagementproject.config.properties.server;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "spring.server-url")
public class ServerUrlProperties {
    private String base;
    private String https;
}

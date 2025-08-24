package com.github.accountmanagementproject.config.sms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cool-sms")
@AllArgsConstructor
@Getter
public class CoolSmsProperties {
    private final String apiKey;
    private final String apiSecret;
    private final String domain;
}
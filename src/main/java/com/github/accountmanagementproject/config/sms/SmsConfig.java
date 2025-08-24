package com.github.accountmanagementproject.config.sms;

import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(CoolSmsProperties.class)// 프로퍼티스 스캔 야믈의 값 가져오는
public class SmsConfig {
    private final CoolSmsProperties coolSmsProperties;
    @Bean
    public DefaultMessageService messageServiceWrapper() {
        return NurigoApp.INSTANCE.initialize(
                coolSmsProperties.getApiKey(),
                coolSmsProperties.getApiSecret(),
                coolSmsProperties.getDomain()
        );
    }
}

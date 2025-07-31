package com.github.accountmanagementproject.config.properties.swagger;

import com.github.accountmanagementproject.config.properties.server.ServerUrlProperties;
import com.github.accountmanagementproject.config.web.swagger.SwaggerConfig;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Profile("dev")
@Configuration
@RequiredArgsConstructor
public class SwaggerDev extends SwaggerConfig {
    private final ServerUrlProperties serverUrlProperties;
    @Override
    protected List<Server> createServers() {
        Server baseServer = new Server()
                .url(serverUrlProperties.getBase())
                .description("Base Server");
        return List.of(baseServer);
    }

}

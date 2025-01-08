package com.github.accountmanagementproject.config.properties.swagger;

import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Profile("dev")
@Configuration
public class SwaggerDev extends SwaggerProperties {
    @Override
    protected List<Server> createServers() {
        Server baseServer = new Server()
                .url("http://localhost:8080")
                .description("Base Server");
        return List.of(baseServer);
    }

}

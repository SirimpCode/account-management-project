package com.github.accountmanagementproject.config.properties.swagger;

import com.github.accountmanagementproject.config.properties.server.ServerUrlProperties;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Profile("default")
@Configuration
@RequiredArgsConstructor
public class SwaggerDefault extends SwaggerProperties {

    private final ServerUrlProperties serverUrlProperties;


    @Override
    protected List<Server> createServers() {
        Server httpsServer = new Server()
                .url(serverUrlProperties.getHttps())
                .description("HTTPS Production Server");
        Server baseServer = new Server()
                .url("http://localhost:8080")
                .description("HTTP Base Server");
        return List.of(httpsServer, baseServer);
    }
}

package com.github.accountmanagementproject.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

//    @Value("${aws.server-url}")
//    private String serverUrl;
//    @Value("${aws.new-server-url}")
//    private String newServerUrl;


    @Bean
    public OpenAPI openAPI(@Value("${springdoc.version}") String openApiVersion) {

        Info info = new Info()
                .title("Account Management Project")
                .version(openApiVersion)
                .description("계정 관리 프로젝트");

//        Server server = new Server();
//        server.setUrl(serverUrl);
//        server.setDescription("Spare Server");
//
//        Server newServer = new Server();
//        newServer.setUrl(newServerUrl);
//        newServer.setDescription("HTTPS Production Server");

        Server localServer = new Server();
        localServer.setUrl("http://localhost:8080/");
        localServer.setDescription("Local Server");

        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("access",
                                new SecurityScheme().type(SecurityScheme.Type.APIKEY).in(SecurityScheme.In.HEADER).name("Authorization"))
                        .addSecuritySchemes("refresh",
                                new SecurityScheme().type(SecurityScheme.Type.APIKEY).in(SecurityScheme.In.HEADER).name("RefreshToken"))
                        .addSecuritySchemes("cookie",
                                new SecurityScheme().type(SecurityScheme.Type.APIKEY).in(SecurityScheme.In.COOKIE).name("RefreshToken"))
                )
                .info(info)
                //일단 로컬 서버만 추가
//                .servers(Arrays.asList(newServer, server, localServer))
                .servers(List.of(localServer))

                .addSecurityItem(new SecurityRequirement().addList("access"))
                .addSecurityItem(new SecurityRequirement().addList("refresh"))
                .addSecurityItem(new SecurityRequirement().addList("cookie"));
//                .addServersItem(new Server().url(serverUrl).description("HTTPS Production Server")) // HTTPS 서버 추가
//                .addServersItem(new Server().url("http://localhost:8080").description("로컬 서버")); // 로컬 서버 추가
    }


}

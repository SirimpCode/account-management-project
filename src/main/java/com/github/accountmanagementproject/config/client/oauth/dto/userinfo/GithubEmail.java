package com.github.accountmanagementproject.config.client.oauth.dto.userinfo;

import lombok.Getter;

@Getter
public class GithubEmail {
    private String email;
    private boolean primary;
    private boolean verified;
    private String visibility;
}

package com.github.accountmanagementproject.config.client.oauth.dto.userinfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.accountmanagementproject.common.myenum.OAuthProvider;
import lombok.Getter;
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GithubUserInfo implements OAuthUserInfo {
    private String id;
    private String login;
    private String nodeId;
    private String avatarUrl;
    private String gravatarId;
    private String url;
    private String htmlUrl;
    private String followersUrl;
    private String followingUrl;
    private String gistsUrl;
    private String starredUrl;
    private String subscriptionsUrl;
    private String organizationsUrl;
    private String reposUrl;
    private String eventsUrl;
    private String receivedEventsUrl;
    private String type;
    private boolean siteAdmin;
    private String name;
    private String company;
    private String blog;
    private String location;
    private String email;
    private boolean hireable;
    private String bio;
    private String twitterUsername;
    private int publicRepos;
    private int publicGists;
    private int followers;
    private int following;
    private String createdAt;
    private String updatedAt;

    @Override
    public GithubUserInfo updateEmailReturnThis(String email){
        this.email = email;
        return this;
    }
    @Override
    public String getSocialId() {
        return this.id;
    }


    @Override
    public String getNickname() {
        return this.name;
    }

    @Override
    public String getProfileImg() {
        return this.avatarUrl;
    }

    @Override
    public OAuthProvider getOAuthProvider() {
        return OAuthProvider.GITHUB;
    }

}

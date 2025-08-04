package com.github.accountmanagementproject.repository.account.socialid;

import com.github.accountmanagementproject.common.converter.custom.OAuthProviderConverter;
import com.github.accountmanagementproject.common.myenum.OAuthProvider;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;

@EqualsAndHashCode(of = { "socialId", "provider" })
@Getter
@Embeddable
public class SocialIdPk implements Serializable {

    @Column(nullable = false)
    private String socialId;

    @Convert(converter = OAuthProviderConverter.class)
    private OAuthProvider provider;

    public static SocialIdPk of(String socialId, OAuthProvider provider) {
        SocialIdPk socialIdPk = new SocialIdPk();
        socialIdPk.socialId = socialId;
        socialIdPk.provider = provider;
        return socialIdPk;
    }

}

package com.github.accountmanagementproject.repository.account.socialids;

import com.github.accountmanagementproject.common.myenum.OAuthProvider;
import com.github.accountmanagementproject.common.converter.custom.OAuthProviderConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@EqualsAndHashCode(of = { "socialId", "provider" })
@NoArgsConstructor
@Getter
@AllArgsConstructor(staticName = "of")
@Embeddable
public class SocialIdPk implements Serializable {

    @Column(unique = true, nullable = false)
    private String socialId;

    @Convert(converter = OAuthProviderConverter.class)
    private OAuthProvider provider;

}

package com.github.accountmanagementproject.repository.account.socialIds;

import com.github.accountmanagementproject.repository.account.users.enums.OAuthProvider;
import com.github.accountmanagementproject.service.mappers.converter.OAuthProviderConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@EqualsAndHashCode
@NoArgsConstructor
@Getter
@AllArgsConstructor
@Embeddable
public class SocialIdPk implements Serializable {

    @Column(unique = true, nullable = false)
    private String socialId;

    @Convert(converter = OAuthProviderConverter.class)
    private OAuthProvider provider;

}

package com.github.accountmanagementproject.service.mappers.converter;

import com.github.accountmanagementproject.repository.account.users.enums.OAuthProvider;
import org.springframework.stereotype.Component;

@Component
public class OAuthProviderConverter extends MyConverter<OAuthProvider> {
    public OAuthProviderConverter() {
        super(OAuthProvider.class);
    }
}

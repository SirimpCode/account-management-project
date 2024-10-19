package com.github.accountmanagementproject.service.mappers.converter;

import com.github.accountmanagementproject.repository.account.users.enums.OAuthProvider;

public class OAuthProviderConverter extends MyConverter<OAuthProvider> {
    public static final Class<OAuthProvider> ENUM_CLASS = OAuthProvider.class;

    public OAuthProviderConverter() {
        super(ENUM_CLASS);
    }
}

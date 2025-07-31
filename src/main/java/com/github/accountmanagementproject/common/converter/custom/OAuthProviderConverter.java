package com.github.accountmanagementproject.common.converter.custom;

import com.github.accountmanagementproject.common.myenum.OAuthProvider;
import org.springframework.stereotype.Component;

@Component
public class OAuthProviderConverter extends MyConverter<OAuthProvider> {
    public OAuthProviderConverter() {
        super(OAuthProvider.class);
    }
}

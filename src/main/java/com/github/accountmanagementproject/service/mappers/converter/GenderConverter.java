package com.github.accountmanagementproject.service.mappers.converter;

import com.github.accountmanagementproject.repository.account.users.enums.Gender;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
@Component
public class GenderConverter extends MyConverter<Gender> {
    public GenderConverter(){
        super(Gender.class);
    }
}

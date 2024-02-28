package com.github.accountmanagementproject.service.mappers.converter;

import com.github.accountmanagementproject.repository.account.users.enums.Gender;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;

public class GenderConverter extends MyConverter<Gender> {
    public static final Class<Gender> ENUM_CLASS = Gender.class;


    public GenderConverter(){
        super(ENUM_CLASS);
    }

}

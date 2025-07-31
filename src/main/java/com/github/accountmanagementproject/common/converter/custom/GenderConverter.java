package com.github.accountmanagementproject.common.converter.custom;

import com.github.accountmanagementproject.common.myenum.Gender;
import org.springframework.stereotype.Component;
@Component
public class GenderConverter extends MyConverter<Gender> {
    public GenderConverter(){
        super(Gender.class);
    }
}

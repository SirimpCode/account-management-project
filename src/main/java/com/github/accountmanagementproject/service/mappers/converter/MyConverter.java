package com.github.accountmanagementproject.service.mappers.converter;
import com.github.accountmanagementproject.repository.account.users.enums.MyEnumInterface;
import jakarta.persistence.AttributeConverter;

import java.util.EnumSet;

public abstract class MyConverter<T extends Enum<T> & MyEnumInterface> implements AttributeConverter<T, String>   {

    private final Class<T> targetEnumClass;

    public MyConverter(Class<T> enumName) {
        this.targetEnumClass = enumName;
    }


    @Override//null 인경우 여기로 안옴
    public String convertToDatabaseColumn(T myEnum) {
        return myEnum.getValue();
    }

    @Override
    public T convertToEntityAttribute(String myEnumName) {
        return myEnumName==null ? null : EnumValueToEnum(myEnumName, targetEnumClass);
    }

    public static <T extends Enum<T> & MyEnumInterface> T EnumValueToEnum(String value, Class<T> enumClass){
        for(T myEnum : EnumSet.allOf(enumClass)){
            if(myEnum.getValue().equals(value)) return myEnum;
        }
        return null;
    }



}

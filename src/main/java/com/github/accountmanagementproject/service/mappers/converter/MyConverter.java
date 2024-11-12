package com.github.accountmanagementproject.service.mappers.converter;
import com.github.accountmanagementproject.repository.account.users.enums.MyEnumInterface;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.EnumSet;

@Converter
public abstract class MyConverter<T extends Enum<T> & MyEnumInterface> implements AttributeConverter<T, String>   {

    private final Class<T> targetEnumClass;

    public MyConverter(Class<T> enumName) {
        this.targetEnumClass = enumName;
    }


    @Override//null 인 경우 jpa 가 호출 안함
    public String convertToDatabaseColumn(T myEnum) {
        return myEnum.getValue();
    }

    @Override
    public T convertToEntityAttribute(String myEnumName) {
        return myEnumName==null ? null : EnumValueToEnum(myEnumName);
    }

    public T EnumValueToEnum(String value){
        for(T myEnum : EnumSet.allOf(this.targetEnumClass)){
            if(myEnum.getValue().equals(value)) return myEnum;
        }
        return null;
    }



}

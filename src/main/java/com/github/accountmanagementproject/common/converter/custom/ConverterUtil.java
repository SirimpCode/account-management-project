//package com.github.accountmanagementproject.service.mappers;
//
//import java.util.EnumSet;
//
//public class ConverterUtil {
//    public static <T extends Enum<T> & MyEnumInterface> T EnumValueToEnum(String value, Class<T> enumClass){
//        for(T myEnum : EnumSet.allOf(enumClass)){
//            if(myEnum.getValue().equals(value)) return myEnum;
//        }
//        return null;
//    }
//    public static <T extends Enum<T> & MyEnumInterface> String EnumToEnumValue(T enumInstance){
//        return enumInstance==null?null: enumInstance.getValue();
//    }
//}

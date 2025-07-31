package com.github.accountmanagementproject.common.converter.custom;
import com.github.accountmanagementproject.common.myenum.MyEnumInterface;
import com.github.accountmanagementproject.common.myenum.RolesEnum;
import jakarta.persistence.AttributeConverter;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import java.util.EnumSet;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
// @RequestParam 등 으로 전달된 값을 convert 메서드를 거쳐 Enum 타입으로 변환하기 위해 하위 Converter 객체들은 @Component 어노테이션을 사용하여 스프링 빈으로 등록되어야 한다.
@jakarta.persistence.Converter
public abstract class MyConverter<T extends Enum<T> & MyEnumInterface> implements AttributeConverter<T, String>, Converter<String, T> {

    private final Map<String, T> valueToEnumMap;
    public MyConverter(Class<T> targetEnumClass) {
        
        this.valueToEnumMap = EnumSet.allOf(targetEnumClass).stream()
                .flatMap(enumValue -> Stream.of(
                        Map.entry(enumValue.getValue(), enumValue),
                        Map.entry(
                                targetEnumClass.equals(RolesEnum.class) && enumValue.name().startsWith("ROLE_")
                                        ? enumValue.name().substring(5)
                                        : enumValue.name(),
                                enumValue
                        )
                ))
                .collect(Collectors.toUnmodifiableMap(
                        Map.Entry::getKey, Map.Entry::getValue
                )
        );
    }

    @Override
    public T convert(@NonNull String source) {
        T result = valueToEnumMap.get(source.toUpperCase());
        if(result!=null) return result;

        throw new IllegalArgumentException("No enum constant for value: " + source);
    }

    @Override//null 인 경우 jpa 가 호출 안함
    public String convertToDatabaseColumn(T myEnum) {
        return myEnum.getValue();
    }

    @Override
    public T convertToEntityAttribute(String myEnumName) {
        return myEnumName==null ? null : valueToEnumMap.get(myEnumName);
    }


}

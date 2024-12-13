package com.github.accountmanagementproject.service.mappers.converter;
import com.github.accountmanagementproject.repository.account.users.enums.MyEnumInterface;
import com.github.accountmanagementproject.repository.account.users.enums.RolesEnum;
import jakarta.persistence.AttributeConverter;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import java.util.EnumSet;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

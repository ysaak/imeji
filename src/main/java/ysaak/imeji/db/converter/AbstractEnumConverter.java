package ysaak.imeji.db.converter;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;

import javax.persistence.AttributeConverter;

public abstract class AbstractEnumConverter<T extends Enum<T> & SerializableEnum> implements AttributeConverter<T, String> {

    private final BiMap<T, String> map;

    public AbstractEnumConverter(Class<T> clazz) {
        ImmutableBiMap.Builder<T, String> builder = ImmutableBiMap.builder();

        for (T enumData : clazz.getEnumConstants()) {
            builder.put(enumData, enumData.getDbValue());
        }

        map = builder.build();
    }

    @Override
    public String convertToDatabaseColumn(T enumData) {
        if (enumData == null) {
            return null;
        }

        return map.get(enumData);
    }

    @Override
    public T convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }

        return map.inverse().get(dbData);
    }
}

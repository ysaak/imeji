package ysaak.imeji.data;

import ysaak.imeji.db.converter.SerializableEnum;

import java.util.Collections;
import java.util.List;

/*
https://konachan.com/help/tags
https://danbooru.donmai.us/wiki_pages/help:tags
*/
public enum TagType implements SerializableEnum {
    GENERAL("GENE", Collections.emptyList()),
    COPYRIGHT("COPY", List.of("copyright", "copy")),
    CHARACTER("CHAR", List.of("character", "char")),
    CIRCLE("CIRC", List.of("circle"))
    ;

    private final String dbValue;
    private final List<String> metadata;

    TagType(String dbValue, List<String> metadata) {
        this.dbValue = dbValue;
        this.metadata = metadata;
    }

    @Override
    public String getDbValue() {
        return dbValue;
    }

    public static TagType fromMetadata(String metadata) {
        for (TagType type : values()) {
            if (type.metadata.contains(metadata)) {
                return type;
            }
        }
        return TagType.GENERAL;
    }
}

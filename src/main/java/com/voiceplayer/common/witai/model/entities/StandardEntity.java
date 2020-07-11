package com.voiceplayer.common.witai.model.entities;

import java.util.Arrays;
import java.util.function.Predicate;

public enum StandardEntity {
    CONTACT("wit/contact", Contact.class),
    DURATION("wit/duration", Duration.class),
    NUMBER("wit/number", Number.class),
    SEARCH_QUERY("wit/search_query", SearchQuery.class);

    private String name;
    private Class clazz;

    StandardEntity(String name, Class clazz) {
        this.name = name;
        this.clazz = clazz;
    }

    public static StandardEntity findUsing(Predicate<StandardEntity> findByFilter) {
        return Arrays.stream(StandardEntity.values())
                .filter(findByFilter)
                .findAny()
                .orElse(null);
    }

    public String getName() {
        return name;
    }

    public Class getClazz() {
        return clazz;
    }
}

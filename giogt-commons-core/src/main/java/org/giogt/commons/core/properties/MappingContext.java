package org.giogt.commons.core.properties;

import java.io.Serializable;

public class MappingContext implements Serializable {
    private static final long serialVersionUID = 1L;

    private Class<?> mappingType;

    public Class<?> getMappingType() {
        return mappingType;
    }

    public void setMappingType(Class<?> mappingType) {
        this.mappingType = mappingType;
    }

    @Override
    public String toString() {
        return "MappingContext{" +
                "mappingType=" + mappingType +
                '}';
    }
}

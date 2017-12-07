package org.giogt.commons.core.properties.stores;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MapPropertiesStore extends ReadOnlyPropertiesStore {

    private final Map<String, String> propertiesMap;

    MapPropertiesStore(Map<String, String> propertiesMap) {
        this.propertiesMap = propertiesMap;
    }

    @Override
    public String getProperty(String key) {
        return propertiesMap.get(key);
    }

    @Override
    public Map<String, String> getProperties() {
        return Collections.unmodifiableMap(propertiesMap);
    }

    @Override
    public void refresh() {
        // in memory map properties are always updated => no need to refresh them
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Map<String, String> map = new HashMap<>();

        public Builder addProperty(String key, String value) {
            map.put(key, value);
            return this;
        }

        public Builder addProperties(Map<String, String> propertiesMap) {
            map.putAll(propertiesMap);
            return this;
        }

        public MapPropertiesStore build() {
            return new MapPropertiesStore(map);
        }
    }
}

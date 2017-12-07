package org.giogt.commons.core.properties.stores;

import org.giogt.commons.core.properties.PropertiesStore;

public abstract class ReadOnlyPropertiesStore implements PropertiesStore {

    @Override
    public void setProperty(String key, String value) {
        throw new UnsupportedOperationException(
                "write operation not supported by the read only property store<" + this.getClass() + ">");
    }

    @Override
    public boolean isReadOnly() {
        return true;
    }

}

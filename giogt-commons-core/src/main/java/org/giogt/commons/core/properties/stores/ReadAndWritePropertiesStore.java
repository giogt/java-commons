package org.giogt.commons.core.properties.stores;

import org.giogt.commons.core.properties.PropertiesStore;

public abstract class ReadAndWritePropertiesStore implements PropertiesStore {

    @Override
    public boolean isReadOnly() {
        return false;
    }

}

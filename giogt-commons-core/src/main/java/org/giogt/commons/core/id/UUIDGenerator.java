package org.giogt.commons.core.id;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class UUIDGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(UUIDGenerator.class);

    public static UUID generateUUID() {
        UUID uuid = UUID.randomUUID();
        LOGGER.debug("UUID generated: " + uuid);
        return uuid;
    }

    public static String generateUUIDString() {
        return generateUUID().toString();
    }

}

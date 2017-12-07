package org.giogt.commons.core.id;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UUIDGeneratorTest {

    @Test
    public void generateUUID_mustReturnANonNullUUID() {
        UUID result = UUIDGenerator.generateUUID();
        assertThat(result, is(Matchers.notNullValue()));
    }

    @Test
    public void generateUUIDString_mustReturnAValidUUIDString() {
        String result = UUIDGenerator.generateUUIDString();

        assertNotNull(result);
        Pattern pattern = Pattern.compile("^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$");
        Matcher matcher = pattern.matcher(result);
        assertThat(matcher.matches(), is(true));
    }

}
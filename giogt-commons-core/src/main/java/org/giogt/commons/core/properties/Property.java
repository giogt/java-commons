package org.giogt.commons.core.properties;


import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotation to mark a field as property, with an optional key and an
 * optional default value.
 */
@Target({FIELD})
@Retention(RUNTIME)
public @interface Property {

    /**
     * The key of the configuration property (optional).
     *
     * Defaults to the name of the field.
     */
    String key() default "";

    /**
     * The default value for the configuration property (optional).
     */
    String defaultValue() default "";

}

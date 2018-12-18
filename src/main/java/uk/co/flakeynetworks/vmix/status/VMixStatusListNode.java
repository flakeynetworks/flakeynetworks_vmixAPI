package uk.co.flakeynetworks.vmix.status;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface VMixStatusListNode {

    String name() default "";
    Class type() default String.class;
}

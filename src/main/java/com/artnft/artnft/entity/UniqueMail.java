package com.artnft.artnft.entity;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = { UniqueMailValidator.class })
public @interface UniqueMail {
    String message() default "{Mail Adress Must Be Unique ..}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}

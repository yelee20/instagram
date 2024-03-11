package com.example.demo.utils;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Past;
import java.lang.annotation.*;
public class ValidationAnnotation {
    @Documented
    @Constraint(validatedBy = {})
    @Target({ElementType.FIELD, ElementType.PARAMETER})
    @Retention(RetentionPolicy.RUNTIME)
    @Past
    public @interface BirthYearAfter {
        String message() default "Invalid birth year";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};

        int value() default 2015;
    }
}


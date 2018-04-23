package com.github.superkoh.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = OneOfIntValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OneOfInt {

  int[] values();

  String message() default "Invalid value for one of";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}

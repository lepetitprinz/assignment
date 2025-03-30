package com.musinsa.assignment.domain.product;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AtLeastOneFieldValidator.class)
public @interface AtLeastOneField {
    String message() default "At least one field (name, price, brand, category) must be provided";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

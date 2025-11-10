package com.cosmocats.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CosmicWordCheckValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CosmicWordCheck {
    String message() default "Product name must contain a cosmic word like star, galaxy, comet, nebula";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

package ridi.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ridi.annotations.validators.NameValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NameValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Name {
    String message() default "Invalid name format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    boolean onlyChars() default true;
}

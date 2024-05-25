package ridi.annotations.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ridi.annotations.Email;

public class EmailValidator implements ConstraintValidator<Email, String> {

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        if (email == null) return false;
        return !email.isEmpty() &&
                email.matches("[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+");
    }
}

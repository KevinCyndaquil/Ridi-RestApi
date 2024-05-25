package ridi.annotations.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ridi.annotations.PhoneNumber;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext consValidator) {
        if (phoneNumber == null) return false;

        return !phoneNumber.isEmpty() &&
                phoneNumber.matches("\\+\\(\\d{2}\\) \\d{3} \\d{3} \\d{4}|\\d{4} \\d{4}");
    }
}

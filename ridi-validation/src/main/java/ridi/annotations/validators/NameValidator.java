package ridi.annotations.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ridi.annotations.Name;

public class NameValidator implements ConstraintValidator<Name, String> {
    boolean onlyChars;

    @Override
    public void initialize(Name constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.onlyChars = constraintAnnotation.onlyChars();
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        if (name == null) return false;
        if (name.isEmpty()) return false;

        String regex = onlyChars ?
                "(?U)^[\\p{Lu}\\p{M}]+( [\\p{Lu}\\p{M}]+)*" :
                "(?U)^[\\p{Lu}\\p{M}\\d]+( [\\p{Lu}\\p{M}\\d]+)*";
        return name.matches(regex);
    }
}

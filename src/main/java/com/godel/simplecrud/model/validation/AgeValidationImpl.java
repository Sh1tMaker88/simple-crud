package com.godel.simplecrud.model.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class AgeValidationImpl implements ConstraintValidator<AgeValidation, LocalDate> {

    @Override
    public void initialize(AgeValidation constraintAnnotation) {

    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        LocalDate thisDate = LocalDate.now().minusYears(18);
        return thisDate.compareTo(value) >= 0;
    }
}

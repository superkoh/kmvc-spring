package com.github.superkoh.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OneOfIntValidator implements ConstraintValidator<OneOfInt, Integer> {

  private int[] allowedValues;

  @Override
  public void initialize(OneOfInt constraintAnnotation) {
    allowedValues = constraintAnnotation.values();
  }

  @Override
  public boolean isValid(Integer value, ConstraintValidatorContext context) {
    for (int num : allowedValues) {
      if (num == value) {
        return true;
      }
    }
    return false;
  }
}

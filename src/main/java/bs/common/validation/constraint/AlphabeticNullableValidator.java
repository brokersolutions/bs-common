package bs.common.validation.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import bs.common.util.ValidatorUtil;
import bs.common.validation.ValidAlphabeticNullable;

public class AlphabeticNullableValidator implements ConstraintValidator<ValidAlphabeticNullable, String> {

    @Override
    public void initialize(ValidAlphabeticNullable constraintAnnotation) {
    	
    }
    
    @Override
    public boolean isValid(String letter, ConstraintValidatorContext context) {   
        return (validateAlphabetic(letter));
    }
    
    private boolean validateAlphabetic(String letter) {
        return ValidatorUtil.validateNullableAlphabetic(letter);
    }
    
}
package bs.common.validator.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import bs.common.util.ValidatorUtil;
import bs.common.validation.ValidAlphabeticWithSpaceNullable;

public class AlphabeticWithSpaceNullableValidator implements ConstraintValidator<ValidAlphabeticWithSpaceNullable, String> {
	
    @Override
    public void initialize(ValidAlphabeticWithSpaceNullable constraintAnnotation) {
    	
    }
    
    @Override
    public boolean isValid(String letter, ConstraintValidatorContext context) {   
        return (validateAlphabetic(letter));
    }
    
    private boolean validateAlphabetic(String letter) {
        return ValidatorUtil.validateNullableAlphabeticWithSpace(letter);
    }
    
}
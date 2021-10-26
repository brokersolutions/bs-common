package bs.common.validator.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import bs.common.util.ValidatorUtil;
import bs.common.validation.ValidAlphabetic;

public class AlphabeticValidator implements ConstraintValidator<ValidAlphabetic, String> {

    @Override
    public void initialize(ValidAlphabetic constraintAnnotation) {
    	
    }
    
    @Override
    public boolean isValid(String letter, ConstraintValidatorContext context) {   
        return (validateAlphabetic(letter));
    }
    
    private boolean validateAlphabetic(String letter) {
        return ValidatorUtil.validateAlphabetic(letter);
    }
    
}
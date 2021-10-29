package bs.common.validation.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import bs.common.util.ValidatorUtil;
import bs.common.validation.ValidAlphabeticWithSpace;

public class AlphabeticWithSpaceValidator implements ConstraintValidator<ValidAlphabeticWithSpace, String> {

	@Override
    public void initialize(ValidAlphabeticWithSpace constraintAnnotation) {
    	
    }
    
    @Override
    public boolean isValid(String letter, ConstraintValidatorContext context) {   
        return (validateAlphabetic(letter));
    }
    
    private boolean validateAlphabetic(String letter) {
        return ValidatorUtil.validateAlphabeticWithSpace(letter);
    }
    
}
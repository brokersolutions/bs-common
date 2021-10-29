package bs.common.validation.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import bs.common.util.ValidatorUtil;
import bs.common.validation.ValidNumericWithSpace;

public class NumericWithSpaceValidator implements ConstraintValidator<ValidNumericWithSpace, String> {

	@Override
    public void initialize(ValidNumericWithSpace constraintAnnotation) {
    	
    }
    
    @Override
    public boolean isValid(String number, ConstraintValidatorContext context) {   
        return (validateNumber(number));
    }
    
    private boolean validateNumber(String number) {
        return ValidatorUtil.validateNumberWithSpace(number);
    }
}
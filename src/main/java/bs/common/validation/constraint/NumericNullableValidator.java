package bs.common.validation.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import bs.common.util.ValidatorUtil;
import bs.common.validation.ValidNumericNullable;

public class NumericNullableValidator implements ConstraintValidator<ValidNumericNullable, String> {

    @Override
    public void initialize(ValidNumericNullable constraintAnnotation) {
    	
    }
    
    @Override
    public boolean isValid(String number, ConstraintValidatorContext context) {   
        return (validateNumber(number));
    }
    
    private boolean validateNumber(String number) {
        return ValidatorUtil.validateNullableNumber(number);
    }
}
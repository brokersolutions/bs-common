package bs.common.validator.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import bs.common.util.ValidatorUtil;
import bs.common.validation.ValidIntNumericNullable;

public class NumericIntNullableValidator implements ConstraintValidator<ValidIntNumericNullable, Integer> {

    @Override
    public void initialize(ValidIntNumericNullable constraintAnnotation) {
    	
    }
    
    @Override
    public boolean isValid(Integer number, ConstraintValidatorContext context) {   
        return (validateNumber(number));
    }
    
    private boolean validateNumber(Integer number) {
        return ValidatorUtil.validateIntNullableNumber(number);
    }
}
package bs.common.validator.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import bs.common.util.ValidatorUtil;
import bs.common.validation.ValidIntNumeric;

public class NumericIntValidator implements ConstraintValidator<ValidIntNumeric, Integer> {

	@Override
    public void initialize(ValidIntNumeric constraintAnnotation) {
    	
    }
    
    @Override
    public boolean isValid(Integer number, ConstraintValidatorContext context) {
        return (validateNumber(number));
    }
    
    private boolean validateNumber(Integer number) {
        return ValidatorUtil.validateIntNumber(number);
    }
}
package bs.common.validator.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import bs.common.util.ValidatorUtil;
import bs.common.validation.ValidDecimal;

public class DecimalValidator implements ConstraintValidator<ValidDecimal, String> {

	@Override
    public void initialize(ValidDecimal constraintAnnotation) {
    	
    }
    
    @Override
    public boolean isValid(String number, ConstraintValidatorContext context) {
        return (validateDecimal(number));
    }
    
    private boolean validateDecimal(String number) {
    	return ValidatorUtil.validateDecimal(number);
    }
    
}
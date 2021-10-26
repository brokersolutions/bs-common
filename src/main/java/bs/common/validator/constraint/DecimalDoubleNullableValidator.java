package bs.common.validator.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import bs.common.util.ValidatorUtil;
import bs.common.validation.ValidDecimalDoubleNullable;

public class DecimalDoubleNullableValidator implements ConstraintValidator<ValidDecimalDoubleNullable, Double> {

	@Override
    public void initialize(ValidDecimalDoubleNullable constraintAnnotation) {
    	
    }
    
    @Override
    public boolean isValid(Double number, ConstraintValidatorContext context) {   
        return (validateDecimal(number));
    }
    
    private boolean validateDecimal(Double number) {
		return ValidatorUtil.validateNullableDecimal(number);
    }
    
}
package bs.common.validator.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import bs.common.util.ValidatorUtil;
import bs.common.validation.ValidDecimalDouble;

public class DecimalDoubleValidator implements ConstraintValidator<ValidDecimalDouble, Double> {

	@Override
    public void initialize(ValidDecimalDouble constraintAnnotation) {
    	
    }
    
    @Override
    public boolean isValid(Double number, ConstraintValidatorContext context) {
        return (validateDecimal(number));
    }
    
    private boolean validateDecimal(Double number) {
    	return ValidatorUtil.validateDecimal(number);
    }
    
}
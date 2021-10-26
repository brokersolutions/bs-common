package bs.common.validator.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import bs.common.util.ValidatorUtil;
import bs.common.validation.ValidDateNullable;

public class DateNullableValidator implements ConstraintValidator<ValidDateNullable, String> {

	@Override
    public void initialize(ValidDateNullable constraintAnnotation) {
    	
    }
    
    @Override
    public boolean isValid(String date, ConstraintValidatorContext context) {   
        return (validateDate(date));
    }
    
    private boolean validateDate(String date) {
    	return ValidatorUtil.validateNullableDate(date);
    }
    
}
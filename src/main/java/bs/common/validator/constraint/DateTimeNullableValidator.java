package bs.common.validator.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import bs.common.util.ValidatorUtil;
import bs.common.validation.ValidDateTimeNullable;

public class DateTimeNullableValidator implements ConstraintValidator<ValidDateTimeNullable, String> {

	@Override
    public void initialize(ValidDateTimeNullable constraintAnnotation) {
    	
    }
    
    @Override
    public boolean isValid(String date, ConstraintValidatorContext context) {   
        return (validateDate(date));
    }
    
    private boolean validateDate(String date) {
    	return ValidatorUtil.validateNullableDateTime(date);
    }
    
}
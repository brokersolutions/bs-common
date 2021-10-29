package bs.common.validation.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import bs.common.util.ValidatorUtil;
import bs.common.validation.ValidDateTime;

public class DateTimeValidator implements ConstraintValidator<ValidDateTime, String> {

	@Override
    public void initialize(ValidDateTime constraintAnnotation) {
    	
    }
    
    @Override
    public boolean isValid(String date, ConstraintValidatorContext context) {   
        return (validateDate(date));
    }
    
    private boolean validateDate(String date) {
    	return ValidatorUtil.validateDateTime(date);
    }
    
}
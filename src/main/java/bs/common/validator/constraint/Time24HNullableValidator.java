package bs.common.validator.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import bs.common.util.ValidatorUtil;
import bs.common.validation.ValidTime24HNullable;

public class Time24HNullableValidator implements ConstraintValidator<ValidTime24HNullable, String> {

	@Override
    public void initialize(ValidTime24HNullable constraintAnnotation) {
    	
    }
    
    @Override
    public boolean isValid(String time, ConstraintValidatorContext context) {   
        return (validateTime(time));
    }
    
    private boolean validateTime(String time) {
    	return ValidatorUtil.validateNullableTime24H(time);
    }
    
}
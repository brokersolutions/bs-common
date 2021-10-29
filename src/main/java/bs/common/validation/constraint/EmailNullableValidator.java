package bs.common.validation.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import bs.common.util.ValidatorUtil;
import bs.common.validation.ValidEmailNullable;

public class EmailNullableValidator implements ConstraintValidator<ValidEmailNullable, String> {

	@Override
    public void initialize(ValidEmailNullable constraintAnnotation) {
    	
    }
    
    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {   
        return (validateEmail(email));
    }
    
    private boolean validateEmail(String email) {
        return ValidatorUtil.validateNullableEmail(email);
    }
    
}
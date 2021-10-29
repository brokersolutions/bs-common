package bs.common.validation.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import bs.common.util.ValidatorUtil;
import bs.common.validation.ValidEmail;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

	@Override
    public void initialize(ValidEmail constraintAnnotation) {
    	
    }
    
    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {   
        return (validateEmail(email));
    }
    
    private boolean validateEmail(String email) {
        return ValidatorUtil.validateEmail(email);
    }
    
}
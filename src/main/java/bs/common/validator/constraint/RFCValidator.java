package bs.common.validator.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import bs.common.util.ValidatorUtil;
import bs.common.validation.ValidRFC;

public class RFCValidator implements ConstraintValidator<ValidRFC, String> {

	@Override
    public void initialize(ValidRFC constraintAnnotation) {
    	
    }
    
    @Override
    public boolean isValid(String rfc, ConstraintValidatorContext context) {   
        return (validateRFC(rfc));
    }
    
    private boolean validateRFC(String rfc) {
        return ValidatorUtil.validateRFC(rfc);
    }
    
}
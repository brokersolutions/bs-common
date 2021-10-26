package bs.common.validator.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import bs.common.util.ValidatorUtil;
import bs.common.validation.ValidRFCNullable;

public class RFCNullableValidator implements ConstraintValidator<ValidRFCNullable, String> {

	@Override
    public void initialize(ValidRFCNullable constraintAnnotation) {
    	
    }
    
    @Override
    public boolean isValid(String rfc, ConstraintValidatorContext context) {   
        return (validateRFC(rfc));
    }
    
    private boolean validateRFC(String rfc) {
        return ValidatorUtil.validateNullableRFC(rfc);
    }
    
}
package bs.common.validation.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import bs.common.util.ValidatorUtil;
import bs.common.validation.ValidRequired;


/**
 * This validator implements the {@link ValidRequired} interface
 * 
 * @author Maikel Guerra Ferrer - mguerraferrer@gmail.com
 * @see ValidRequired	
 */
public class RequiredValidator implements ConstraintValidator<ValidRequired, Object> {
	
	@Override
	public boolean isValid(Object obj, ConstraintValidatorContext context) {
		return ValidatorUtil.validateRequired(obj);
	}

}
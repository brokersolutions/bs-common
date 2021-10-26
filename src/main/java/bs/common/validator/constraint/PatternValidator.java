package bs.common.validator.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import bs.common.util.ValidatorUtil;
import bs.common.validation.ValidPattern;


/**
 * This validator implements the {@link ValidPattern} interface
 * 
 * @author Maikel Guerra Ferrer - mguerraferrer@gmail.com
 * @see ValidPattern
 *
 */
public class PatternValidator implements ConstraintValidator<ValidPattern, String> {
	
	private String regexp;
	
	@Override
	public void initialize(ValidPattern constraintAnnotation) {
		regexp = constraintAnnotation.regexp();
	}
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return ValidatorUtil.validatePattern(value, regexp);
	}

}
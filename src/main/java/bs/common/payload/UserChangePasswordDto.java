package bs.common.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.NoArgsConstructor;
import bs.common.util.ValidatorUtil;
import bs.common.validation.ValidEmail;
import bs.common.validation.ValidFieldsValueDifferent;
import bs.common.validation.ValidFieldsValueMatch;
import bs.common.validation.ValidPassword;

@Data
@NoArgsConstructor
@ValidFieldsValueDifferent(field = "oldPassword", fieldDifferent = "password", message = ValidatorUtil.INVALID_PASSWORD_DIFFERENT)
@ValidFieldsValueMatch(field = "password", fieldMatch = "repassword", message = ValidatorUtil.INVALID_PASSWORD_MATCHING)
public class UserChangePasswordDto {
	
	@NotBlank(message = ValidatorUtil.REQUIRED)
    @ValidEmail(message = ValidatorUtil.INVALID_EMAIL)
    private String email;
	
	@NotEmpty(message = ValidatorUtil.REQUIRED)
	private String oldPassword;
	
	@NotEmpty(message = ValidatorUtil.REQUIRED)
    @ValidPassword(message = ValidatorUtil.INVALID_PASSWORD)
	private String password;
	
	@NotEmpty(message = ValidatorUtil.REQUIRED)
    private String repassword;
		
}
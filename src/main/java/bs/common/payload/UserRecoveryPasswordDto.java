package bs.common.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.NoArgsConstructor;
import bs.common.util.ValidatorUtil;
import bs.common.validation.ValidEmail;
import bs.common.validation.ValidFieldsValueMatch;
import bs.common.validation.ValidPassword;

@Data
@NoArgsConstructor
@ValidFieldsValueMatch(field = "password", fieldMatch = "repassword", message = ValidatorUtil.INVALID_PASSWORD_MATCHING)
public class UserRecoveryPasswordDto {

	@NotBlank(message = ValidatorUtil.REQUIRED)
	private String code;
	
	@NotEmpty(message = ValidatorUtil.REQUIRED)
    @ValidEmail(message = ValidatorUtil.INVALID_EMAIL)
	private String email;
	
	@NotEmpty(message = ValidatorUtil.REQUIRED)
    @ValidPassword(message = ValidatorUtil.INVALID_PASSWORD)
	private String password;
	
	@NotEmpty(message = ValidatorUtil.REQUIRED)
    private String repassword;
	
}
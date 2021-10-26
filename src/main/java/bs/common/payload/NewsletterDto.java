package bs.common.payload;

import javax.validation.constraints.NotEmpty;

import lombok.Data;
import bs.common.util.ValidatorUtil;
import bs.common.validation.ValidEmail;

@Data
public class NewsletterDto {
	
	@NotEmpty(message = ValidatorUtil.REQUIRED)
    @ValidEmail(message = ValidatorUtil.INVALID_EMAIL)
	private String newsletterEmail;
	
}
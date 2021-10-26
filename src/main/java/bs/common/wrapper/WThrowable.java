package bs.common.wrapper;

import java.io.Serializable;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WThrowable implements Serializable {
	private static final long serialVersionUID = -189365452227508599L;
	
	private String redirectAfterError;
	private String exceptionMessage;
    private RedirectAttributes redirectAttributes;
    
    public WThrowable(final String redirectAfterError) {
    	this.redirectAfterError = redirectAfterError;
    }
    
    public WThrowable(final String redirectAfterError, final String exceptionMessage) {
    	this.redirectAfterError = redirectAfterError;
    	this.exceptionMessage = exceptionMessage;
    }
    
}
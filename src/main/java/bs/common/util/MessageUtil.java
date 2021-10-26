package bs.common.util;

import java.io.Serializable;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MessageUtil implements Serializable {
	private static final long serialVersionUID = 7465546545462763375L;

	@Autowired
	private MessageSource messageSource;

	private static MessageSourceAccessor accessor;

	@PostConstruct
	private void init() {
		accessor = new MessageSourceAccessor(messageSource, LocaleContextHolder.getLocale());
	}
	
	public static void setAccessor(MessageSourceAccessor msAccessor) {
		accessor = msAccessor;
	}
	
	public static String getMessage(final String key) {
		return getMessage(key, null);
	}
	
	public static String getMessage(final String key, @Nullable Object[] params) {
		if(ValidatorUtil.isNotEmpty(key)) {
			return accessor.getMessage(key, params, LocaleContextHolder.getLocale());
		}
		return null;
	}
	
	public static String getMessage(final CustomMessage customMessage) {
		if(ValidatorUtil.isNotEmpty(customMessage)) {
			return accessor.getMessage(customMessage.getKey(), customMessage.getParams(), LocaleContextHolder.getLocale());
		}
		return null;
	}
	
	public String getPropertieValue(String activeProfile, String key) {
		try {
		
			var propertiesFile = "application.properties";
			if(activeProfile != null) {
				propertiesFile = "application-" + activeProfile + ".properties";
			}
			
			val properties = new Properties();
			properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(propertiesFile));
			
			val value = properties.getProperty(key);
			if(value != null) {
				return value;
			}
			
		} catch (Exception e) {
			log.error("MessageUtil#getPropertieValue error {}", e);
		}
		return null;
	}
	
	@Data
	@NoArgsConstructor
	@Builder
	@AllArgsConstructor
	public static class CustomMessage {
		private String key;
		private Object[] params;
	}
	
}
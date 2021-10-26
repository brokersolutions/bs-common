package bs.common.util;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;
import lombok.val;

@Data
@Builder
@AllArgsConstructor
public class EmailUtil implements Serializable {
	private static final long serialVersionUID = -3053384548071235730L;

	private String recipient;
	@Singular("recipients")
	private List<String> recipients;
	private String subject;
	private Map<String, String> ctxMap;
	private String ctx;
	
	public static EmailUtil populate(final String recipient, final String subject, final Map<String, String> ctxMap) {
		val bean = EmailUtil
					.builder()
			    	.recipient(recipient)
			    	.subject(subject)
			    	.ctxMap(ctxMap)
			    	.ctx(ctx(ctxMap))
			    	.clearRecipients()
			    	.recipients(Arrays.asList(recipient))
					.build();
		return bean;
	}
	
	public static String ctx(Map<String, String> ctxMap) {
		if(ctxMap == null || ctxMap.isEmpty()) {
			return null;
		}
		
		String ctx = null;
		for (val entry : ctxMap.entrySet()) {
			val key = entry.getKey();
			val value = entry.getValue();
			val kv = value != null ? key.concat("=").concat(value) : key.concat("=");
			
			if(ctx == null) {
				ctx = kv;
			} else {
				ctx = ctx.concat("&").concat(kv);
			}
		}
		return ctx;
	}

}
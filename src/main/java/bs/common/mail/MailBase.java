package bs.common.mail;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import bs.common.util.ListUtil;
import bs.common.util.PredicateUtil;
import bs.common.util.ValidatorUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;
import lombok.val;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MailBase implements Serializable {
	private static final long serialVersionUID = 3878729541833095624L;
	
	private String from;
	@Singular("recipients")
	private List<String> recipients;
	@Singular("copyTo")
	private List<String> copyTo;
	private String subject;
	private String body;
	
	public static MailBase populate(final String from, 
									final String recipient, 
									final String subject, 
									final String body) {
		return populate(from, recipient, null, subject, body);
	}
	
	public static MailBase populate(final String from, 
									final String recipient, 
									final String copyTo, 
									final String subject, 
									final String body) {
		
		val recipients = sanitizeRecipient(recipient);
		val copyToList = sanitizeCopyTo(copyTo);
		
		return populate(from, recipients, copyToList, subject, body);
	}
	
	public static MailBase populate(final String from, 
									final List<String> recipients, 
									final List<String> copyTo, 
									final String subject,
									final String body) {
		
		val verify = verify(from, recipients, subject, body);
		if (verify) {
			val recipientList = sanitizeRecipients(recipients);
			val copyToList = sanitizeCopyTo(recipientList, copyTo);
			
			return MailBase
					.builder()
					.from(from)
					.clearRecipients()
					.recipients(recipientList)
					.copyTo(copyToList)
					.subject(subject)
					.body(body)
					.build();
		}
		return null;
	}
	
	public static Boolean isValid(MailBase ew) {
		return verify(ew.getFrom(), ew.getRecipients(), ew.getSubject(), ew.getBody());
	}
	
	public static String getAllRecipients(MailBase ew) {
		if (ValidatorUtil.nonNull(ew) && ValidatorUtil.nonNull(ew.getRecipients()) && ValidatorUtil.nonNull(ew.getCopyTo())) {
			val recipients = ListUtil.concat(ew.getRecipients(), ew.getCopyTo());
			return recipients.stream()
							 .filter(PredicateUtil.distinctByKey(String::new))
							 .collect(Collectors.joining(", "));
		}
		return null;
	}
	
	public static String getContext(Map<String, Object> ctxMap) {
		if(ctxMap == null || ctxMap.isEmpty()) {
			return null;
		}
		
		return ctxMap.entrySet().stream()
						 		.map(i -> i.getKey() + "=" + i.getValue())
						 		.collect(Collectors.joining("&"));
	}
	
	private static List<String> sanitizeRecipient(String recipient) {
		if (ValidatorUtil.isNotEmpty(recipient)) {
			val recipientList = List.of(recipient);
			return recipientList.stream()
					 	     	.filter(Objects::nonNull)
					 	     	.filter(PredicateUtil.distinctByKey(String::new))
					 	     	.toList();
		}
		return Collections.emptyList();
	}
	
	private static List<String> sanitizeRecipients(List<String> recipients) {
		if (ValidatorUtil.isNotEmpty(recipients)) {
			return recipients.stream()
							 .filter(Objects::nonNull)
							 .filter(PredicateUtil.distinctByKey(String::new))
							 .toList();
		}
		return Collections.emptyList();
	}
	
	private static List<String> sanitizeCopyTo(String copyTo) {
		if (ValidatorUtil.isNotEmpty(copyTo)) {
			val copyToList = List.of(copyTo);
			return copyToList.stream()
					 	     .filter(Objects::nonNull)
					 	     .filter(PredicateUtil.distinctByKey(String::new))
					 	     .toList();
		}
		return Collections.emptyList();
	}
	
	private static List<String> sanitizeCopyTo(List<String> recipients, List<String> copyTo) {
		if (ValidatorUtil.isNotEmpty(copyTo)) {
			val allRecipients = ListUtil.concat(recipients, copyTo);
			return allRecipients.stream()
							 	.filter(Objects::nonNull)
							 	.filter(PredicateUtil.distinctByKey(String::new))
							 	.toList();
		}
		return Collections.emptyList();
	}
	
	private static Boolean verify(final String from, final List<String> recipients, final String subject, final String body) {
		val args = List.of(from, recipients, subject, body);
		return ValidatorUtil.nonNull(args);
	}

}
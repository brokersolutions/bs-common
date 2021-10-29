package bs.common.mail;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import bs.common.util.ListUtil;
import bs.common.util.ValidatorUtil;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MailBaseServiceImpl implements MailBaseService {
	
	@Autowired
    private JavaMailSender mailSender;
	
	private final SimpleMailMessage init(final MailBase mailBase) {
		try {
			
			val isValid = MailBase.isValid(mailBase);
	    	if(isValid) {
	    		log.info("MailBaseServiceImpl#init - SimpleMailMessage initialization to send an email from [{}] to the recipients [{}]", 
	    				mailBase.getFrom(), MailBase.getAllRecipients(mailBase));
	    		// Email info
	    		val from = mailBase.getFrom();
	    		val to = mailBase.getRecipients();
	    		val copyTo = mailBase.getCopyTo();
	    		val subject = mailBase.getSubject();
	    		
	    		val email = new SimpleMailMessage();
	    		email.setFrom(from);
	    		email.setTo(ListUtil.toArray(to));
	    		email.setSubject(subject);
	    		email.setText(mailBase.getBody());
	    		
	            if (ValidatorUtil.isNotEmpty(copyTo)) {
	            	email.setCc(ListUtil.toArray(copyTo));
	            }
	            
	            return email;
	    	} else {
	    		log.warn("MailBaseServiceImpl#init - MailBase is not valid");
	    	}
			
		} catch (Exception e) {
			log.error("MailBaseServiceImpl#init error {}", e.getMessage());
		}
        return null;
    }

	@Override
	public Boolean sendMail(MailBase mailBase) {
		boolean mailSent = true;
		try {
			
			val email = init(mailBase);
			if (email != null) {
				val message = mailSender.createMimeMessage();
				val messageHelper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());
				messageHelper.setFrom(email.getFrom());
				messageHelper.setTo(email.getTo());
				messageHelper.setSubject(email.getSubject());
				messageHelper.setText(email.getText(), true);
				
				if (ValidatorUtil.isNotEmpty(email.getCc())) {
					messageHelper.setCc(email.getCc());
				}
				
				log.info("MailBaseServiceImpl#sendEmail - Sending email from [{}] to recipients [{}]", 
						mailBase.getFrom(), MailBase.getAllRecipients(mailBase));
	        	mailSender.send(message);
	        	log.info("MailBaseServiceImpl#sendEmail - The email has been sent successfully");
			} else {
				log.warn("MailBaseServiceImpl#sendEmail - SimpleMailMessage is null");
				mailSent = false;
			}
			
		} catch (MailException e) {
			log.error("MailBaseServiceImpl#sendEmail [MailException error] - {}", e.getMessage());
			mailSent = false;
		} catch (Exception e) {
			log.error("MailBaseServiceImpl#sendEmail [Exception error] - {}", e.getMessage());
			mailSent = false;
		}
		return mailSent;
	}
	
}
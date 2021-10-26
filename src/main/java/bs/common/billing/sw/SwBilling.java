package bs.common.billing.sw;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import Exceptions.AuthException;
import Services.Authentication.SWAuthenticationService;
import bs.common.billing.sw.SwCfdi33Response.Status;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SwBilling {
	
	private static final String API_TEST_URL = "http://services.test.sw.com.mx";
	private static final String API_PROD_URL = "https://services.sw.com.mx";
	private static final String API_STAMP_ENDPOINT = "/v3/cfdi33/issue/json/v4";
	
	public static SwCfdi33Response stamp(String username, String passwd, Voucher voucher, Boolean isProd) {
		try {
			
			val apiUrl = isProd ? API_PROD_URL : API_TEST_URL;
			val auth = new SWAuthenticationService(username, passwd, apiUrl);
			
			val authResponse = auth.Token();
			if (authResponse.Status.equals(Status.SUCCESS.getStatus())) {
				val stampUrl = apiUrl + API_STAMP_ENDPOINT;
				
				val headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				headers.setBearerAuth(auth.getToken());
				
				val entity = new HttpEntity<Object>(voucher, headers);
				
				val restTemplate = new RestTemplate();
				val response = restTemplate.postForObject(stampUrl, entity, SwCfdi33Response.class);
				if (response != null) {
					if (response.getStatus().equalsIgnoreCase(Status.SUCCESS.getStatus())) {
						log.info("SwBilling#stamp status=[{}]", response.getStatus());
						log.info("SwBilling#stamp uuid=[{}]", response.getData().getUuid());
					} else {
						log.warn("SwBilling#stamp warn message=[{}]", response.getMessage());
						log.warn("SwBilling#stamp warn messageDetail=[{}]", response.getMessageDetail());
					}
					return response;
				} 
			}
			
		} catch (AuthException e) {
			log.info("SwBilling#stamp error - AuthException {}", e.getMessage());
		} catch (Exception e) {
			log.info("SwBilling#stamp error - Exception {}", e.getMessage());
		}
		return null;
	}
	
}
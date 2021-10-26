package bs.common.test.util;

import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.Locale;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.SpringSecurityMessageSource;

import bs.common.util.AgeUtil;
import bs.common.util.MessageUtil;
import lombok.val;

/**
 * {@link AgeUtil} unit test
 * @author Maikel Guerra Ferrer - mguerraferrer@gmail.com
 *
 */
//@TestInstance(value = Lifecycle.PER_CLASS)
//@ExtendWith(MockitoExtension.class)

//@RunWith(SpringRunner.class)
//@ContextConfiguration(classes = MessageUtil.class)
//@TestPropertySource(locations = "/test-messages-mx.properties")
//@PropertySource("classpath:messages_es_MX.properties")
class AgeUtilTest {
	//@InjectMocks
	//private MessageUtil message;
	
	private static final LocalDate TODAY = LocalDate.now();
	
	private void initLocaleMx() {
		Locale beforeHolder = LocaleContextHolder.getLocale();
		LocaleContextHolder.setLocale(new Locale("es", "MX"));
		val accessor = SpringSecurityMessageSource.getAccessor();
		MessageUtil.setAccessor(accessor);
	}
	
	//@Test
	void ageYearsTest() {
		initLocaleMx();
		
		var dob = TODAY.minusYears(1);
		var age = AgeUtil.age(dob);
		assertTrue(age.contains("1 año") || age.contains("1 year"));
	}
	
}
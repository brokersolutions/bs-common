package bs.common.test.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import bs.common.util.PatternUtil;
import bs.common.util.ValidatorUtil;

/**
 * {@link PatternUtil} unit test
 * 
 * @author Maikel Guerra Ferrer - mguerraferrer@gmail.com
 *
 */
@TestInstance(value = Lifecycle.PER_CLASS)
class PatternUtilTest {
	
	@ParameterizedTest
	@CsvSource({ "true", "false", "TRUE", "FALSE", "TruE", "FaLsE" })
	void booleanPatternValidTest(String value) {
		assertTrue(ValidatorUtil.validatePattern(value, PatternUtil.BOOLEAN_PATTERN));
	}
	
	@ParameterizedTest
	@CsvSource({ "trues", "falses", "TRUES", "FALSES" })
	void booleanPatternFailedTest(String value) {
		assertFalse(ValidatorUtil.validatePattern(value, PatternUtil.BOOLEAN_PATTERN));
	}
	
	@ParameterizedTest
	@CsvSource({ "1995-10-20 02:59", "2020-12-31 14:03", "2021-05-10 05:15" })
	void dateTime24hPatternValidTest(String value) {
		assertTrue(ValidatorUtil.validatePattern(value, PatternUtil.DATE_TIME_24H_PATTERN));
	}

	@ParameterizedTest
	@CsvSource({ "1890-10-20 23:59", "3001-12-31 24:20", "2021-5-1 05-15" })
	void dateTime24hPatternFailedTest(String value) {
		assertFalse(ValidatorUtil.validatePattern(value, PatternUtil.DATE_TIME_24H_PATTERN));
	}
	
	@ParameterizedTest
	@CsvSource({ "1995-10-20 02:59:55", "2020-12-31 14:03:35", "2021-05-10 05:15:05" })
	void dateTime24hFullPatternValidTest(String value) {
		assertTrue(ValidatorUtil.validatePattern(value, PatternUtil.DATE_TIME_24H_FULL_PATTERN));
	}

	@ParameterizedTest
	@CsvSource({ "1890-10-20 23:59:55", "2120-12-31  24.20.35", "2021-5-1 05-15-35" })
	void dateTime24hFullPatternFailedTest(String value) {
		assertFalse(ValidatorUtil.validatePattern(value, PatternUtil.DATE_TIME_24H_FULL_PATTERN));
	}
	
	@ParameterizedTest
	@CsvSource({ "1995-10-20T02:59", "2020-12-31'T'14:03", "2021-05-10T05:15" })
	void dateTime24hTPatternValidTest(String value) {
		assertTrue(ValidatorUtil.validatePattern(value, PatternUtil.DATE_TIME_24H_T_PATTERN));
	}

	@ParameterizedTest
	@CsvSource({ "1890-10-20T23:59", "2120-12-31't'24.20", "2021-5-1T05-15" })
	void dateTime24hTPatternFailedTest(String value) {
		assertFalse(ValidatorUtil.validatePattern(value, PatternUtil.DATE_TIME_24H_T_PATTERN));
	}
	
	@ParameterizedTest
	@CsvSource({ "1995-10-20T02:59:55", "2020-12-31'T'14:03:35", "2021-05-10T05:15:05" })
	void dateTime24hTFullPatternValidTest(String value) {
		assertTrue(ValidatorUtil.validatePattern(value, PatternUtil.DATE_TIME_24H_T_FULL_PATTERN));
	}

	@ParameterizedTest
	@CsvSource({ "1890-10-20T23:59:55", "2120-12-31't'24.20.35", "2021-5-1T05-15-35" })
	void dateTime24hTFullPatternFailedTest(String value) {
		assertFalse(ValidatorUtil.validatePattern(value, PatternUtil.DATE_TIME_24H_T_FULL_PATTERN));
	}
	
	@ParameterizedTest
	@CsvSource({ "1995.10.20 23.59.55", "2020.12.31 14.20.35", "2021.05.10 05.15.35" })
	void dateTime24hFullDotPatternValidTest(String value) {
		assertTrue(ValidatorUtil.validatePattern(value, PatternUtil.DATE_TIME_24H_DOT_PATTERN));
	}

	@ParameterizedTest
	@CsvSource({ "1890.10.20T23.59.55", "2120.12.31  24.20.35", "2021.5.1-05.15.35", "2021.10.12_5.15.35" })
	void dateTime24hFullDotPatternFailedTest(String value) {
		assertFalse(ValidatorUtil.validatePattern(value, PatternUtil.DATE_TIME_24H_DOT_PATTERN));
	}
	
	@ParameterizedTest
	@CsvSource({ "1995.10.20T23.59.55", "2020.12.31'T'14.20.35", "2021.05.10T05.15.35" })
	void dateTime24hFullDotTPatternValidTest(String value) {
		assertTrue(ValidatorUtil.validatePattern(value, PatternUtil.DATE_TIME_24H_DOT_T_PATTERN));
	}

	@ParameterizedTest
	@CsvSource({ "1890.10.20T23.59.55", "2120.12.31T24.20.35", "2021.5.1T05.15.35", "2021.10.12T5.15.35" })
	void dateTime24hFullDotTPatternFailedTest(String value) {
		assertFalse(ValidatorUtil.validatePattern(value, PatternUtil.DATE_TIME_24H_DOT_T_PATTERN));
	}
	
	@ParameterizedTest
	@CsvSource({ "11:25 am", "05:10 pm", "10:50 AM", "08:12 aM", "12:15 Pm" })
	void time12hPatternValidTest(String value) {
		assertTrue(ValidatorUtil.validatePattern(value, PatternUtil.TIME_12H_PATTERN));
	}

	@ParameterizedTest
	@CsvSource({ "08:40am", "12:50pm", "01:45  am" })
	void time12hPatternFailedTest(String value) {
		assertFalse(ValidatorUtil.validatePattern(value, PatternUtil.TIME_12H_PATTERN));
	}
	
	@ParameterizedTest
	@CsvSource({ "11:25:59 am", "05:10:00 pm", "10:50:15 AM", "08:12:00 aM", "12:15:15 Pm" })
	void time12hFullPatternValidTest(String value) {
		assertTrue(ValidatorUtil.validatePattern(value, PatternUtil.TIME_12H_FULL_PATTERN));
	}

	@ParameterizedTest
	@CsvSource({ "08:40:00am", "12:50:00pm", "01:45:00  am", "10:00 pm" })
	void time12hFullPatternFailedTest(String value) {
		assertFalse(ValidatorUtil.validatePattern(value, PatternUtil.TIME_12H_FULL_PATTERN));
	}

	@ParameterizedTest
	@CsvSource({ "13:25", "05:10", "22:55" })
	void time24hPatternValidTest(String value) {
		assertTrue(ValidatorUtil.validatePattern(value, PatternUtil.TIME_24H_PATTERN));
	}

	@ParameterizedTest
	@CsvSource({ "1:25", "24:10", "22:00:00", "19:5", "7:9" })
	void time24hPatternFailedTest(String value) {
		assertFalse(ValidatorUtil.validatePattern(value, PatternUtil.TIME_24H_PATTERN));
	}

	@ParameterizedTest
	@CsvSource({ "13:25:59", "05:10:00", "22:50:15" })
	void time24hFullPatternValidTest(String value) {
		assertTrue(ValidatorUtil.validatePattern(value, PatternUtil.TIME_24H_FULL_PATTERN));
	}

	@ParameterizedTest
	@CsvSource({ "13:25", "24:10:00", "22:65:15", "19:15:5", "1:24:15", "15:5:55" })
	void time24hFullPatternFailedTest(String value) {
		assertFalse(ValidatorUtil.validatePattern(value, PatternUtil.TIME_24H_FULL_PATTERN));
	}

}
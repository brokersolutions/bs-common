package bs.common.test.util;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import lombok.val;
import bs.common.util.DateTimeUtil;
import bs.common.util.DateTimeUtil.Formatter;

/**
 * {@link DateTimeUtil} unit test
 * 
 * @author Maikel Guerra Ferrer - mguerraferrer@gmail.com
 *
 */
@TestInstance(value = Lifecycle.PER_CLASS)
class DateTimeUtilTest {

	@Test
	void localDateTimeTest1() {
		val date = new Date();
		val ldt = DateTimeUtil.localDateTime(date);
		assertNotNull(ldt);
	}

	@ParameterizedTest
	@CsvSource({ "03:00 PM", "03:00 pm", "3:55 pm" })
	void localTimeTest1(String dateString) {
		val time = DateTimeUtil.localTime(dateString, Formatter.T12H);
		assertTrue(time.toString().startsWith("15"));
	}

	@ParameterizedTest
	@CsvSource({ "10:30 AM", "10:30 am" })
	void localTimeTest2(String dateString) {
		val time = DateTimeUtil.localTime(dateString, Formatter.T12H);
		assertEquals("10:30", time.toString());
	}

	@ParameterizedTest
	@CsvSource({ "2:00:00 pm", "02:30:55 PM", "2:20:25 pM" })
	void localTimeTest3(String dateString) {
		val time = DateTimeUtil.localTime(dateString, Formatter.T12H_FULL);
		assertTrue(time.toString().startsWith("14"));
	}

	@ParameterizedTest
	@CsvSource({ "20:30", "20:30" })
	void localTimeTest4(String dateString) {
		val time = DateTimeUtil.localTime(dateString, Formatter.T24H);
		assertEquals("20:30", time.toString());
	}

	@ParameterizedTest
	@CsvSource({ "5:30", "05:25" })
	void localTimeTest5(String dateString) {
		val time = DateTimeUtil.localTime(dateString, Formatter.T24H);
		assertTrue(time.toString().startsWith("05"));
	}

}
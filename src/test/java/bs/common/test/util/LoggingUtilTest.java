package bs.common.test.util;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.StopWatch;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import lombok.val;
import bs.common.util.LoggingUtil;
import bs.common.util.ThreadUtil;

/**
 * {@link LoggingUtil} unit test
 * @author Maikel Guerra Ferrer - mguerraferrer@gmail.com
 *
 */
@TestInstance(value = Lifecycle.PER_CLASS)
class LoggingUtilTest {

	@Test
	void loggingTimeTest1() {
		val watch = new StopWatch();
		watch.start();
		
		ThreadUtil.sleep(3000);
		
		watch.stop();
		val elapsedTime = watch.getTime();
		
		assertTrue(elapsedTime >= 3000);
	}
	
	@Test
	void loggingTimeTest2() {
		val watch = new StopWatch();
		watch.start();
		
		ThreadUtil.sleep(2000);
		
		watch.stop();
		val elapsedTime = watch.getTime(TimeUnit.SECONDS);
		
		assertEquals(2, elapsedTime);
	}
	
	@Test
	void loggingFormatTime() {
		val watch = new StopWatch();
		watch.start();
		
		ThreadUtil.sleep(1000);
		
		watch.stop();
		val elapsedTime = watch.formatTime();
		
		assertTrue(elapsedTime.contains("00:00:01"));
	}
	
}
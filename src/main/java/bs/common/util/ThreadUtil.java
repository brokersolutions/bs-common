package bs.common.util;

import java.io.Serializable;

import lombok.extern.slf4j.Slf4j;

/**
 * Utility class for Thread
 * 
 * @author Maikel Guerra Ferrer - mguerraferrer@gmail.com
 *
 */
@Slf4j
public class ThreadUtil implements Serializable {
	private static final long serialVersionUID = -7122187366487485119L;

	private ThreadUtil() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Causes the currently executing thread to sleep (temporarily ceaseexecution)
	 * for the specified number of milliseconds
	 * 
	 * @param sleep Number of milliseconds
	 */
	public static void sleep(long sleep) {
		try {

			Thread.sleep(sleep);

		} catch (InterruptedException e) {
			log.warn("ThreadUtil#sleep Interrupted {}", e);
			// Restore interrupted state...
			Thread.currentThread().interrupt();
		}
	}

}
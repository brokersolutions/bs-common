package bs.common.util;

import java.io.Serializable;

/**
 * @author Maikel Guerra Ferrer - mguerraferrer@gmail.com
 *
 */
public class AjaxUtil implements Serializable {
	private static final long serialVersionUID = -994677449429428373L;
	
	private AjaxUtil() {
		throw new IllegalStateException("Utility class");
	}

	public static final String AJAX_HEADER_NAME = "X-Requested-With";
	public static final String AJAX_HEADER_VALUE = "XMLHttpRequest";
}
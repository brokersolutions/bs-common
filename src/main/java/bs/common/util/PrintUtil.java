package bs.common.util;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import lombok.val;
import lombok.extern.slf4j.Slf4j;
import bs.common.util.DateTimeUtil.Formatter;

@Slf4j
public class PrintUtil implements Serializable {
	private static final long serialVersionUID = -6804009512273545887L;

	private PrintUtil() {
		throw new IllegalStateException("Utility class");
	}

	public static String consoleDatetime() {
		var consolePrint = StringUtils.EMPTY;
		try {

			val formattedDate = DateTimeUtil.formatDate(new Date(), Formatter.DATE_SIMPLE_DOT_FORMAT);
			val formattedTime = DateTimeUtil.formatDate(new Date(), Formatter.T24H_FULL);
			consolePrint = formattedDate.concat(" ").concat(formattedTime).concat(" ");

		} catch (Exception e) {
			log.error("PrintUtil#consoleDatetime error {}", e);
		}
		return consolePrint;
	}
}
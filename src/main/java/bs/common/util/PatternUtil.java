package bs.common.util;

import java.io.Serializable;

/**
 * Pattern utility class
 * 
 * @author Maikel Guerra Ferrer - mguerraferrer@gmail.com
 *
 */
public class PatternUtil implements Serializable {
	private static final long serialVersionUID = 8573425970490347196L;

	private PatternUtil() {
		throw new IllegalStateException("Utility class");
	}

	public static final String EMAIL_PATTERN = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";
	public static final String ALPHABETIC_PATTERN = "^[a-zA-ZáéíñóúüÁÉÍÑÓÚÜ]*$";
	public static final String ALPHABETIC_WITH_SPACE_PATTERN = "^[a-zA-ZáéíñóúüÁÉÍÑÓÚÜ\\s]*$";
	public static final String NUMBER_PATTERN = "\\d+";
	public static final String NUMBER_POSITIVE_PATTERN = "^[0-9]\\d*$";
	public static final String NUMBER_WITH_SPACE_PATTERN = "^[0-9\\s]*$";
	public static final String RFC_PATTERN = "[A-Z&Ñ]{3,4}[0-9]{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])[A-Z0-9]{2}[0-9A]?";
	public static final String RFC12_PATTERN = "[A-Z&Ñ]{3}[0-9]{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])[A-Z0-9]{2}[0-9A]?";
	public static final String RFC13_PATTERN = "[A-Z&Ñ]{4}[0-9]{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])[A-Z0-9]{2}[0-9A]?";
	public static final String CURP_PATTERN = "[A-Z][AEIOUX][A-Z]{2}[0-9]{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])[MH]([ABCMTZ]S|[BCJMOT]C|[CNPST]L|[GNQ]T|[GQS]R|C[MH]|[MY]N|[DH]G|NE|VZ|DF|SP)[BCDFGHJ-NP-TV-Z]{3}[0-9A-Z][0-9]";
	public static final String STATE_MX_PATTERN = "agc|bac|bcs|cam|coa|col|chp|chh|cdmx|dur|gua|gue|hid|jal|mex|mic|mor|nay|nul|oax|pue|que|roo|slp|sin|son|tab|tam|tla|ver|yuc|zac";
	public static final String ZIP_CODE_MX_PATTERN = "^\\d{5}$";
	public static final String PHONE_MX_PATTERN = "^\\d{10}$";
	public static final String BOOLEAN_PATTERN = "^(?i)(true|false)$";

	/**
	 * DateTime pattern for <b>yyyy-MM-dd HH:mm</b> format
	 */
	public static final String DATE_TIME_24H_PATTERN = "^((19|20)\\d\\d)-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])(\\s)(2[0-3]|[01][0-9]):([0-5][0-9]$)";
	
	/**
	 * DateTime pattern for <b>yyyy-MM-dd HH:mm:ss</b> format
	 */
	public static final String DATE_TIME_24H_FULL_PATTERN = "^((19|20)\\d\\d)-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])(\\s)(2[0-3]|[01][0-9]):([0-5][0-9]):([0-5][0-9])$";
	
	/**
	 * DateTime pattern for <b>yyyy-MM-ddTHH:mm</b> format
	 */
	public static final String DATE_TIME_24H_T_PATTERN = "^((19|20)\\d\\d)-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])('T'|T)(2[0-3]|[01][0-9]):([0-5][0-9])$";
	
	/**
	 * DateTime pattern for <b>yyyy-MM-ddTHH:mm:ss</b> format
	 */
	public static final String DATE_TIME_24H_T_FULL_PATTERN = "^((19|20)\\d\\d)-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])('T'|T)(2[0-3]|[01][0-9]):([0-5][0-9]):([0-5][0-9])$";
	
	/**
	 * DateTime pattern for <b>yyyy.MM.dd HH.mm.ss</b> format
	 */
	public static final String DATE_TIME_24H_DOT_PATTERN = "^((19|20)\\d\\d)[.](0[1-9]|1[012])[.](0[1-9]|[12][0-9]|3[01])(\\s)(2[0-3]|[01][0-9])[.]([0-5][0-9])[.]([0-5][0-9])$";
	
	/**
	 * DateTime pattern for <b>yyyy.MM.ddTHH.mm.ss</b> format
	 */
	public static final String DATE_TIME_24H_DOT_T_PATTERN = "^((19|20)\\d\\d)[.](0[1-9]|1[012])[.](0[1-9]|[12][0-9]|3[01])('T'|T)(2[0-3]|[01][0-9])[.]([0-5][0-9])[.]([0-5][0-9])$";
	
	/**
	 * Time pattern for <b>12-hour (hh:mm a)</b> format case-insensitive
	 */
	public static final String TIME_12H_PATTERN = "^(0[1-9]|1[012]):[0-5][0-9](\\s)(?i)(am|pm)$";
	
	/**
	 * Time pattern for <b>12-hour (hh:mm:ss a)</b> format case-insensitive
	 */
	public static final String TIME_12H_FULL_PATTERN = "^(0[1-9]|1[012]):[0-5][0-9]:[0-5][0-9](\\s)(?i)(am|pm)$";
	
	/**
	 * Time pattern for <b>24-hour (HH:mm)</b> format
	 */
	public static final String TIME_24H_PATTERN = "^(0[1-9]|1[0-9]|2[0-3]):[0-5][0-9]$";
	
	/**
	 * Time pattern for <b>24-hour (HH:mm:ss)</b> format 
	 */
	public static final String TIME_24H_FULL_PATTERN = "^(0[1-9]|1[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$";

}
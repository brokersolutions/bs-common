package bs.common.util;

import lombok.val;
import lombok.extern.slf4j.Slf4j;

import bs.common.util.MessageUtil.CustomMessage;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Date & Time utility class
 * 
 * @author Maikel Guerra Ferrer mguerraferrer@gmail.com
 *
 */
@Slf4j
public class DateTimeUtil implements Serializable {
	private static final long serialVersionUID = -941116400306552283L;

	private DateTimeUtil() {
		throw new IllegalStateException("Utility class");
	}
	
	public static final String DATE_RANGE_SEPARATOR = " - ";
	public static final String LAST_TIME_OF_DATE = "23:59:59";
	public static final String TIME_AM = "AM";
	public static final String TIME_PM = "PM";

	public static final String DURATION_DAYS = "days";
	public static final String DURATION_HOURS = "hours";
	public static final String DURATION_MINUTES = "minutes";
	public static final String DURATION_SECONDS = "seconds";
	public static final String DURATION_MILLIS = "millis";
	public static final String DURATION_NANOS = "nanos";

	public static Date TODAY = new Date();
	public static Integer CURRENT_DAY = currentDay();
	public static Integer CURRENT_MONTH = currentMonth();
	public static Integer CURRENT_YEAR = currentYear();
	public static String CURRENT_YEAR_2D = currentYear2D();
	public static String CURRENT_TIMESTAMP = currentTimestamp(Formatter.DATE_TIME_DB_FULL_FORMAT);
	public static Timestamp SYSTEM_TIMESTAMP = new Timestamp(System.currentTimeMillis());
	
	private static final String LOG_FORMAT_DATE_ERROR = "UDateTime#formatDate error {}";
	private static final String LOG_DATE_ERROR = "UDateTime#date error {}";
	private static final String LOG_LOCAL_DATE_ERROR = "UDateTime#localDate error {}";
	private static final String LOG_LOCAL_TIME_ERROR = "UDateTime#localTime error {}";
	private static final String LOG_LOCAL_DATE_TIME_ERROR = "UDateTime#localDateTime error {}";
	private static final String LOG_MONTH_ERROR = "UDateTime#month error {}";
	private static final String LOG_DAY_OF_WEEK_ERROR = "UDateTime#dayOfWeek error {}";
	private static final String LOG_DURATION_ERROR = "UDateTime#duration error {}";
	private static final String LOG_DURATION_IN_DATES_ERROR = "UDateTime#durationInDates error {}";
	private static final String LOG_DURATION_IN_HOURS_ERROR = "UDateTime#durationInHours error {}";
	private static final String LOG_DURATION_IN_MINUTES_ERROR = "UDateTime#durationInMinutes error {}";
	private static final String LOG_DURATION_IN_SECONDS_ERROR = "UDateTime#durationInSeconds error {}";
	private static final String LOG_DURATION_IN_MILLISECONDS_ERROR = "UDateTime#durationInMilliseconds error {}";
	private static final String LOG_ELAPSED_TIME_ERROR = "UDateTime#elapsedTime error {}";

	public enum Formatter {
		// ##### DB DATE
		DATE_DB_FORMAT("yyyy-MM-dd"), 
		DATE_TIME_DB_SIMPLE_FORMAT("yyyy-MM-dd HH:mm"),
		DATE_TIME_DB_FULL_FORMAT("yyyy-MM-dd HH:mm:ss"), 
		DATE_TIME_T_FORMAT("yyyy-MM-dd'T'HH:mm:ss"),
		// ##### CUSTOM DATE
		DATE_SIMPLE_FORMAT("dd/MM/yyyy"), 
		DATE_SIMPLE_DOT_FORMAT("dd.MM.yyyy"),
		DATE_TIME_SIMPLE_FORMAT("dd/MM/yyyy HH:mm"), 
		DATE_TIME_FULL_FORMAT("dd/MM/yyyy HH:mm:ss"),
		DATE_TIME_MERIDIAN_FORMAT("dd/MM/yyyy hh:mm:ss a"),
		// ##### TIME
		T12H("hh:mm a"), 
		T12H_FULL("hh:mm:ss a"), 
		T24H("HH:mm"), 
		T24H_FULL("HH:mm:ss");

		private String format;

		Formatter(final String value) {
			format = value;
		}

		public String getFormat() {
			return format;
		}
	}

	/**
	 * Returns current day
	 * 
	 * @return The day-of-month, from 1 to 31
	 */
	private static Integer currentDay() {
		return LocalDate.now().getDayOfMonth();
	}

	/**
	 * Returns current month
	 * 
	 * @return The month-of-year, from 1 to 12
	 */
	private static Integer currentMonth() {
		return LocalDate.now().getMonthValue();
	}

	/**
	 * Returns current year
	 * 
	 * @return The actual year
	 */
	private static Integer currentYear() {
		return LocalDate.now().getYear();
	}

	/**
	 * Returns current year with 2 digits
	 * 
	 * @return The actual year with 2 digits
	 */
	private static String currentYear2D() {
		return Year.now().format(DateTimeFormatter.ofPattern("yy"));
	}

	/**
	 * Returns a current timestamp in String format. <br>
	 * <b>Allows only the following formats:</b> <br>
	 * Formatter.DATE_TIME_FULL_FORMAT and Formatter.DATE_TIME_FULL_FORMAT
	 * 
	 * @param formatter {@link Formatter}
	 * @return Current timestamp in String format
	 * @see Formatter
	 */
	public static String currentTimestamp(final Formatter formatter) {
		try {

			String timestamp = null;
			if (formatter.equals(Formatter.DATE_TIME_FULL_FORMAT)
					|| formatter.equals(Formatter.DATE_TIME_DB_FULL_FORMAT)) {
				val dtf = DateTimeFormatter.ofPattern(formatter.format);
				timestamp = dtf.format(LocalDateTime.now());
			}
			return timestamp;

		} catch (Exception e) {
			log.error("UDateTime#currentTimeStamp error {}", e);
		}
		return null;
	}

	/**
	 * Convert Date (java.util.date) to Calendar (java.util.calendar)
	 * 
	 * @param date java.util.Date
	 * @return A calendar from date
	 */
	public static Calendar dateToCalendar(final Date date) {
		try {

			if (date != null) {
				val calendar = Calendar.getInstance();
				calendar.setTime(date);

				return calendar;
			}

		} catch (Exception e) {
			log.error("UDateTime#dateToCalendar error {}", e);
		}
		return null;
	}

	/**
	 * Convert Calendar (java.util.Calendar) to Date (java.util.Date)
	 * 
	 * @param calendar Calendar (java.util.Calendar)
	 * @return A date from calendar
	 */
	public static Date calendarToDate(final Calendar calendar) {
		try {

			if (calendar != null) {
				return calendar.getTime();
			}

		} catch (Exception e) {
			log.error("UDateTime#calendarToDate error {}", e);
		}
		return null;
	}

	/**
	 * Format a current {@code java.util.Date} with specific formatter
	 * 
	 * @param pattern String
	 * @return A string date formatted from the Date
	 */
	public static String formatDate(final String pattern) {
		return formatDate(new Date(), pattern);
	}
	
	/**
	 * Formats a {@code java.util.Date} with a specific pattern
	 * 
	 * @param date java.util.date
	 * @param pattern String
	 * @return A string date formatted from the Date
	 */
	public static String formatDate(final Date date, final String pattern) {
		if (date != null && ValidatorUtil.isNotEmpty(pattern)) {
			val ld = localDate(date);
			return formatDate(ld, pattern);
		}
		return null;
	}
	
	/**
	 * Formats a {@code java.time.LocalDate} with a specific pattern
	 * 
	 * @param ld java.time.LocalDate
	 * @param pattern String
	 * @return A string date formatted from the LocalDate
	 */
	public static String formatDate(final LocalDate ld, final String pattern) {
		try {
			
			if (ld != null && ValidatorUtil.isNotEmpty(pattern)) {
				return ld.format(DateTimeFormatter.ofPattern(pattern));
			}
			
		} catch (Exception e) {
			log.error(LOG_FORMAT_DATE_ERROR, e);
		}
		return null;
	}
	
	/**
	 * Formats a {@code java.time.LocalDateTime} with a specific pattern
	 * 
	 * @param ldt java.time.LocalDateTime
	 * @param pattern String
	 * @return A string date formatted from the LocalDateTime
	 */
	public static String formatDate(final LocalDateTime ldt, final String pattern) {
		try {
			
			if (ldt != null && ValidatorUtil.isNotEmpty(pattern)) {
				return ldt.format(DateTimeFormatter.ofPattern(pattern));
			}
			
		} catch (Exception e) {
			log.error(LOG_FORMAT_DATE_ERROR, e);
		}
		return null;
	}
	
	/**
	 * Format a current {@code java.util.Date} with specific formatter
	 * 
	 * @param formatter {@link Formatter}
	 * @return A string date formatted from the Date
	 * @see Formatter
	 */
	public static String formatDate(final Formatter formatter) {
		return formatDate(new Date(), formatter);
	}
	
	/**
	 * Formats a {@code java.util.Date} with a specific format
	 * 
	 * @param date      java.util.Date
	 * @param formatter {@link Formatter}
	 * @return A string date formatted from the Date
	 * @see Formatter
	 */
	public static String formatDate(final Date date, final Formatter formatter) {
		try {

			if (date != null && ValidatorUtil.isNotEmpty(formatter)) {
				val sdf = new SimpleDateFormat(formatter.format);
				return sdf.format(date).toLowerCase();
			}

		} catch (Exception e) {
			log.error(LOG_FORMAT_DATE_ERROR, e);
		}
		return null;
	}

	/**
	 * Formats {@code java.time.LocalDate} with a specific format
	 * 
	 * @param localDate java.time.LocalDate
	 * @param formatter {@link Formatter}
	 * @return A string date formatted from the LocalDate
	 * @see Formatter
	 */
	public static String formatDate(final LocalDate localDate, final Formatter formatter) {
		try {

			if (localDate != null) {
				return localDate.format(DateTimeFormatter.ofPattern(formatter.format));
			}

		} catch (Exception e) {
			log.error(LOG_FORMAT_DATE_ERROR, e);
		}
		return null;
	}

	/**
	 * Formats a {@code java.time.LocalDateTime} with a specific format
	 * 
	 * @param localDateTime java.time.LocalDateTime
	 * @param formatter     {@link Formatter}
	 * @return A string date formatted from the LocalDateTime
	 * @see Formatter
	 */
	public static String formatDate(final LocalDateTime localDateTime, final Formatter formatter) {
		try {

			if (localDateTime != null) {
				return localDateTime.format(DateTimeFormatter.ofPattern(formatter.format));
			}

		} catch (Exception e) {
			log.error(LOG_FORMAT_DATE_ERROR, e);
		}
		return null;
	}

	public static String formatDate(final java.sql.Date sqlDate, Formatter formatter) {
		if (ValidatorUtil.isNotEmpty(sqlDate) && ValidatorUtil.isNotEmpty(formatter)) {
			try {

				val sdf = new SimpleDateFormat(formatter.format);
				return sdf.format(sqlDate);

			} catch (Exception e) {
				log.error(LOG_FORMAT_DATE_ERROR, e);
			}
		}
		return null;
	}

	/**
	 * Parses a date with a specific format
	 * 
	 * @param dateStr   String date
	 * @param formatter {@link Formatter}
	 * @return A Date parsed from the string
	 * @see Formatter
	 */
	public static Date parseDate(final String dateStr, final Formatter formatter) {
		try {

			if (ValidatorUtil.isNotEmpty(dateStr) && ValidatorUtil.isNotEmpty(formatter)) {
				val sdf = new SimpleDateFormat(formatter.format);
				return sdf.parse(dateStr);
			}

		} catch (ParseException e) {
			log.error("UDateTime#parseDate error {}", e);
		}
		return null;
	}

	public static Date parseDateTime(final String dateStr, Formatter formatter, boolean useLastTime) {
		try {

			if (ValidatorUtil.isNotEmpty(dateStr) && ValidatorUtil.isNotEmpty(formatter)
					&& ValidatorUtil.isNotEmpty(useLastTime)) {
				val date = parseDate(dateStr, formatter);
				val time = useLastTime ? parseDate(LAST_TIME_OF_DATE, formatter) : new Date();
				val ldt = localDateTime(date, time);
				return date(ldt);
			}

		} catch (Exception e) {
			log.error("UDateTime#parseDateTime error {}", e);
		}
		return null;
	}

	/**
	 * Returns the day of month
	 * 
	 * @param date java.util.Date
	 * @return The day of month (from 1 to 31)
	 */
	public static Integer day(final Date date) {
		try {

			if (date != null) {
				val ld = localDate(date);
				if (ld != null) {
					return ld.getDayOfMonth();
				}
			}

		} catch (Exception e) {
			log.error("UDateTime#day error {}", e);
		}
		return null;
	}

	/**
	 * Returns the day of month of the given date
	 * 
	 * @param date java.util.Date
	 * @return The month of te year (from 1 to 12)
	 */
	public static Integer month(final Date date) {
		try {

			if (date != null) {
				val ldt = localDate(date);
				if (ldt != null) {
					return ldt.getMonthValue();
				}
			}

		} catch (Exception e) {
			log.error(LOG_MONTH_ERROR, e);
		}
		return null;
	}

	/**
	 * Returns the text (from properties) that represents the name of the month or
	 * the abbreviated month name
	 * 
	 * @param date         java.util.Date
	 * @param abbreviation Boolean
	 * @return The name of the month or the abbreviated month name
	 */
	public static String month(final Date date, final Boolean abbreviation) {
		try {

			if (date != null && abbreviation != null) {
				val month = month(date);
				return month(month, abbreviation);
			}

		} catch (Exception e) {
			log.error(LOG_MONTH_ERROR, e);
		}
		return null;
	}

	public static String month(final Integer month, final Boolean showAbbreviation) {
		return switch (month) {
		case 1 -> Boolean.FALSE.equals(showAbbreviation) ? "date.text.month.january" : "date.text.month.january.short";
		case 2 -> Boolean.FALSE.equals(showAbbreviation) ? "date.text.month.february" : "date.text.month.february.short";
		case 3 -> Boolean.FALSE.equals(showAbbreviation) ? "date.text.month.march" : "date.text.month.march.short";
		case 4 -> Boolean.FALSE.equals(showAbbreviation) ? "date.text.month.april" : "date.text.month.april.short";
		case 5 -> Boolean.FALSE.equals(showAbbreviation) ? "date.text.month.may" : "date.text.month.may.short";
		case 6 -> Boolean.FALSE.equals(showAbbreviation) ? "date.text.month.june" : "date.text.month.june.short";
		case 7 -> Boolean.FALSE.equals(showAbbreviation) ? "date.text.month.july" : "date.text.month.july.short";
		case 8 -> Boolean.FALSE.equals(showAbbreviation) ? "date.text.month.august" : "date.text.month.august.short";
		case 9 -> Boolean.FALSE.equals(showAbbreviation) ? "date.text.month.september" : "date.text.month.september.short";
		case 10 -> Boolean.FALSE.equals(showAbbreviation) ? "date.text.month.october" : "date.text.month.october.short";
		case 11 -> Boolean.FALSE.equals(showAbbreviation) ? "date.text.month.november" : "date.text.month.november.short";
		case 12 -> Boolean.FALSE.equals(showAbbreviation) ? "date.text.month.december" : "date.text.month.december.short";
		default -> null;
		};
	}

	/**
	 * Returns java.time.Month that representing of month name
	 * 
	 * @param monthName String
	 * @return A month of the year, such as 'July'
	 */
	public static Month month(final String monthName) {
		try {

			if (ValidatorUtil.isNotEmpty(monthName)) {
				val monthNameUpperCase = monthName.toUpperCase();
				return switch (monthNameUpperCase) {
				case "ENERO", "JANUARY", "ENE", "JAN" -> Month.JANUARY;
				case "FEBRERO", "FEBRUARY", "FEB" -> Month.FEBRUARY;
				case "MARZO", "MARCH", "MAR" -> Month.MARCH;
				case "ABRIL", "APRIL", "ABR", "APR" -> Month.APRIL;
				case "MAYO", "MAY" -> Month.MAY;
				case "JUNIO", "JUNE", "JUN" -> Month.JUNE;
				case "JULIO", "JULY", "JUL" -> Month.JULY;
				case "AGOSTO", "AUGUST", "AUG" -> Month.AUGUST;
				case "SEPTIEMBRE", "SEPTEMBER", "SEP" -> Month.SEPTEMBER;
				case "OCTUBRE", "OCTOBER", "OCT" -> Month.OCTOBER;
				case "NOVIEMBRE", "NOVEMBER", "NOV" -> Month.NOVEMBER;
				case "DICIEMBRE", "DECEMBER", "DIC", "DEC" -> Month.DECEMBER;
				default -> null;
				};
			}

		} catch (Exception e) {
			log.error(LOG_MONTH_ERROR, e);
		}
		return null;
	}

	/**
	 * Returns the month number from 1 to 12
	 * 
	 * @param monthName String
	 * @return A month of the year
	 */
	public static Integer monthNumber(final String monthName) {
		try {

			if (ValidatorUtil.isNotEmpty(monthName)) {
				val monthNameUpperCase = monthName.toUpperCase();
				val month = month(monthNameUpperCase);
				if (month != null) {
					return month.getValue();
				}
			}

		} catch (Exception e) {
			log.error("UDateTime#monthNumber error {}", e);
		}
		return null;
	}

	/**
	 * Returns de year of the given date
	 * 
	 * @param date java.util.Date
	 * @return The year
	 */
	public static Integer year(final Date date) {
		try {

			if (date != null) {
				val ld = localDate(date);
				if (ld != null) {
					return ld.getYear();
				}
			}

		} catch (Exception e) {
			log.error("UDateTime#year error {}", e);
		}
		return null;
	}

	/**
	 * Returns de year (two digits) of the given date
	 * 
	 * @param date java.util.Date
	 * @return The year (two digits)
	 */
	public static String year2D(final Date date) {
		try {

			if (date != null) {
				val ld = localDate(date);
				if (ld != null) {
					return ld.format(DateTimeFormatter.ofPattern("yy"));
				}
			}

		} catch (Exception e) {
			log.error("UDateTime#year2D error {}", e);
		}
		return null;
	}

	public static CustomMessage fullDate() {
		return fullDate(new Date());
	}

	public static CustomMessage fullDate(final LocalDateTime ldt) {
		val date = date(ldt);
		return fullDate(date);
	}

	public static CustomMessage fullDate(final Date date) {
		try {

			String day = null;
			String month = null;
			
			val localDate = localDate(date);
			if (localDate != null) {
				val dayOfWeek = localDate.getDayOfWeek().name();
				switch (dayOfWeek) {
					case "MONDAY":
						day = "date.text.day.monday";
						break;
					case "TUESDAY":
						day = "date.text.day.tuesday";
						break;
					case "WEDNESDAY":
						day = "date.text.day.wednesday";
						break;
					case "THURSDAY":
						day = "date.text.day.thursday";
						break;
					case "FRIDAY":
						day = "date.text.day.friday";
						break;
					case "SATURDAY":
						day = "date.text.day.saturday";
						break;
					case "SUNDAY":
						day = "date.text.day.sunday";
						break;
					default: break;
				}
				
				val monthOfYear = localDate.getMonth().name();
				switch (monthOfYear) {
					case "JANUARY":
						month = "date.text.month.january";
						break;
					case "FEBRUARY":
						month = "date.text.month.february";
						break;
					case "MARCH":
						month = "date.text.month.march";
						break;
					case "APRIL":
						month = "date.text.month.april";
						break;
					case "MAY":
						month = "date.text.month.may";
						break;
					case "JUNE":
						month = "date.text.month.june";
						break;
					case "JULY":
						month = "date.text.month.july";
						break;
					case "AUGUST":
						month = "date.text.month.august";
						break;
					case "SEPTEMBER":
						month = "date.text.month.september";
						break;
					case "OCTOBER":
						month = "date.text.month.october";
						break;
					case "NOVEMBER":
						month = "date.text.month.november";
						break;
					case "DECEMBER":
						month = "date.text.month.december";
						break;
					default: break;	
				}
				
				val dayOfMonth = localDate.getDayOfMonth();
				val year = localDate.getYear();

				val params = new String[] { MessageUtil.getMessage(day), String.valueOf(dayOfMonth), MessageUtil.getMessage(month),
						String.valueOf(year) };
				return CustomMessage.builder().key("date.text.date.full").params(params).build();
			}

		} catch (Exception e) {
			log.error("UDateTime#fullDate error {}", e);
		}
		return null;
	}

	public static CustomMessage shortDate() {
		val localDate = localDate(new Date());
		if (localDate != null) {
			val day = localDate.getDayOfMonth();
			val dayOfMonth = day >= 10 ? String.valueOf(day) : "0" + day;
			
			val month = localDate.getMonthValue();
			val monthStr = month >= 10 ? String.valueOf(month) : "0" + month;
			
			val year = localDate.getYear();
			
			val params = new String[] { dayOfMonth, monthStr, String.valueOf(year) };
			return CustomMessage.builder().key("date.text.date.short").params(params).build();
		}
		return null;
	}

	public static DayOfWeek dayOfWeek(final String day) {
		try {

			if (ValidatorUtil.isNotEmpty(day)) {
				val dayUpperCase = day.toUpperCase();
				switch (dayUpperCase) {
				case "LUNES":
					return DayOfWeek.MONDAY;
				case "MONDAY":
					return DayOfWeek.MONDAY;
				case "MARTES":
					return DayOfWeek.TUESDAY;
				case "TUESDAY":
					return DayOfWeek.TUESDAY;
				case "MIERCOLES":
					return DayOfWeek.WEDNESDAY;
				case "MIÉRCOLES":
					return DayOfWeek.WEDNESDAY;
				case "WEDNESDAY":
					return DayOfWeek.WEDNESDAY;
				case "JUEVES":
					return DayOfWeek.THURSDAY;
				case "THURSDAY":
					return DayOfWeek.THURSDAY;
				case "VIERNES":
					return DayOfWeek.FRIDAY;
				case "FRIDAY":
					return DayOfWeek.FRIDAY;
				case "SABADO":
					return DayOfWeek.SATURDAY;
				case "SÁBADO":
					return DayOfWeek.SATURDAY;
				case "SATURDAY":
					return DayOfWeek.SATURDAY;
				case "DOMINGO":
					return DayOfWeek.SUNDAY;
				case "SUNDAY":
					return DayOfWeek.SUNDAY;
				default:
					return null;
				}
			}

		} catch (Exception e) {
			log.error(LOG_DAY_OF_WEEK_ERROR, e);
		}
		return null;
	}

	public static String dayOfWeek(final Date date) {
		try {

			if (date != null) {
				val localDate = localDate(date);
				if (localDate != null) {
					return localDate.getDayOfWeek().name();
				}
			}

		} catch (Exception e) {
			log.error(LOG_DAY_OF_WEEK_ERROR, e);
		}
		return null;
	}

	public static DayOfWeek getDayOfWeek(final Date date) {
		try {

			if (date != null) {
				val localDate = localDate(date);
				if (localDate != null) {
					return localDate.getDayOfWeek();
				}
			}

		} catch (Exception e) {
			log.error("UDateTime#getDayOfWeek error {}", e);
		}
		return null;
	}

	public static Date dayOfWeek(final Date date, final String type, final String day) {
		try {

			if (date != null && ValidatorUtil.isNotEmpty(type) && ValidatorUtil.isNotEmpty(day)) {
				val localDate = localDate(date);
				return dayOfWeek(localDate, type, day);
			}

		} catch (Exception e) {
			log.error(LOG_DAY_OF_WEEK_ERROR, e);
		}
		return null;
	}

	public static Date dayOfWeek(final LocalDate localDate, final String type, final String day) {
		try {

			if (localDate != null && ValidatorUtil.isNotEmpty(type) && ValidatorUtil.isNotEmpty(day)) {
				if (type.equalsIgnoreCase("First")) {
					return date(localDate.with(TemporalAdjusters.firstInMonth(dayOfWeek(day))));
				} else if (type.equalsIgnoreCase("Second")) {
					return date(localDate.with(TemporalAdjusters.firstInMonth(dayOfWeek(day))).plusDays(7));
				} else if (type.equalsIgnoreCase("Third")) {
					return date(localDate.with(TemporalAdjusters.firstInMonth(dayOfWeek(day))).plusDays(14));
				} else if (type.equalsIgnoreCase("Last")) {
					return date(localDate.with(TemporalAdjusters.lastInMonth(dayOfWeek(day))));
				}
			}

		} catch (Exception e) {
			log.error(LOG_DAY_OF_WEEK_ERROR, e);
		}
		return null;
	}

	public static LocalDate firstDayOfMonth() {
		try {

			val month = YearMonth.from(localDateTime(new Date()));
			return month.atDay(1);

		} catch (Exception e) {
			log.error("UDateTime#firstDayOfMonth error {}", e);
		}
		return null;
	}

	public static LocalDate lastDayOfMonth() {
		try {

			val month = YearMonth.from(localDateTime(new Date()));
			return month.atEndOfMonth();

		} catch (Exception e) {
			log.error("UDateTime#lastDayOfMonth error {}", e);
		}
		return null;
	}

	public static LocalDate lastDayOfMonth(final Integer year, final Integer month) {
		try {

			if (year != null && month != null) {
				val ld = LocalDate.of(year, month, 1);
				return ld.with(TemporalAdjusters.lastDayOfMonth());
			}

		} catch (Exception e) {
			log.error("UDateTime#lastDayOfMonth error {}", e);
		}
		return null;
	}

	public static Integer getLastDayOfMonth(final Integer year, final Integer month) {
		try {

			if (year != null && month != null) {
				val ld = LocalDate.of(year, month, 1);
				return ld.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();
			}

		} catch (Exception e) {
			log.error("UDateTime#getLastDayOfMonth error {}", e);
		}
		return null;
	}

	public static String firstLastDayOfMonthRange() {
		return firstLastDayOfMonthRange(Formatter.DATE_SIMPLE_FORMAT.format, DATE_RANGE_SEPARATOR);
	}

	public static String firstLastDayOfMonthRange(final String format, final String separator) {
		try {

			if (ValidatorUtil.isNotEmpty(format) && ValidatorUtil.isNotEmpty(separator)) {
				val sdf = new SimpleDateFormat(format);

				val firstDayOfMonth = firstDayOfMonth();
				val firstDay = date(firstDayOfMonth);
				val firstDayFormatted = sdf.format(firstDay);

				val lastDayOfMonth = lastDayOfMonth();
				val lastDay = date(lastDayOfMonth);
				val lastDayFormatted = sdf.format(lastDay);

				return firstDayFormatted + separator + lastDayFormatted;
			}

		} catch (Exception e) {
			log.error("UDateTime#firstLastDayOfMonthRange error {}", e);
		}
		return ValueUtil.EMPTY;
	}

	public static String firstActualDayOfMonthRange() {
		return firstActualDayOfMonthRange(Formatter.DATE_SIMPLE_FORMAT.format, DATE_RANGE_SEPARATOR);
	}

	public static String firstActualDayOfMonthRange(final String format, final String separator) {
		try {

			if (ValidatorUtil.isNotEmpty(format) && ValidatorUtil.isNotEmpty(separator)) {
				val sdf = new SimpleDateFormat(format);

				val firstDayOfMonth = firstDayOfMonth();
				val firstDay = date(firstDayOfMonth);
				val firstDayFormatted = sdf.format(firstDay);

				val todayOfMonth = localDate(new Date());
				val today = date(todayOfMonth);
				val lastDayFormatted = sdf.format(today);

				return firstDayFormatted + separator + lastDayFormatted;
			}

		} catch (Exception e) {
			log.error("UDateTime#firstActualDayOfMonthRange error {}", e);
		}
		return ValueUtil.EMPTY;
	}

	// ###########################################
	// ##### LocalDate (java.time.LocalDate) #####
	// ###########################################

	/**
	 * Returns a LocalDate (java.time.LocalDate)
	 * 
	 * @param stringDate String date
	 * @param pattern    Formatter ({@link Formatter})
	 * @return LocalDate
	 */
	public static LocalDate localDate(final String stringDate, final Formatter pattern) {
		try {

			if (ValidatorUtil.isNotEmpty(stringDate) && pattern != null) {
				val dateTimeFormatter = DateTimeFormatter.ofPattern(pattern.format);
				return LocalDate.parse(stringDate, dateTimeFormatter);
			}

		} catch (Exception e) {
			log.error(LOG_LOCAL_DATE_ERROR, e);
		}
		return null;
	}

	/**
	 * Returns a LocalDate (java.time.LocalDate)
	 * 
	 * @param year  Year of date (Integer)
	 * @param month Month of date (Integer)
	 * @param day   Day of date (Integer)
	 * @return LocalDate
	 */
	public static LocalDate localDate(final Integer year, final Integer month, final Integer day) {
		try {

			if (year != null && month != null && day != null) {
				return LocalDate.of(year, month, day);
			}

		} catch (Exception e) {
			log.error(LOG_LOCAL_DATE_ERROR, e);
		}
		return null;
	}

	/**
	 * Returns a LocalDate (java.time.LocalDate) from Date (java.util.Date)
	 * 
	 * @param date java.util.Date
	 * @return LocalDate
	 */
	public static LocalDate localDate(final Date date) {
		try {

			if (date != null) {
				val fDate = formatDate(date, Formatter.DATE_SIMPLE_FORMAT);
				val dtf = DateTimeFormatter.ofPattern(Formatter.DATE_SIMPLE_FORMAT.format);
				return LocalDate.parse(fDate, dtf);
			}

		} catch (Exception e) {
			log.error(LOG_LOCAL_DATE_ERROR, e);
		}
		return null;
	}

	/**
	 * Returns a LocalDate (java.time.LocalDate) from LocalDateTime
	 * (java.time.LocalDateTime)
	 * 
	 * @param ldt LocalDateTime (java.time.LocalDateTime)
	 * @return LocalDate
	 */
	public static LocalDate localDate(final LocalDateTime ldt) {
		if (ldt != null) {
			return ldt.toLocalDate();
		}
		return null;
	}

	public static LocalTime localTime(final Date date) {
		try {

			val calendar = dateToCalendar(date);
			if (calendar != null) {
				return LocalTime.of(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
						calendar.get(Calendar.SECOND));
			}

		} catch (Exception e) {
			log.error(LOG_LOCAL_TIME_ERROR, e);
		}
		return null;
	}

	public static LocalTime localTime(final String dateStr) {
		try {

			val format = DateTimeFormatter.ofPattern(Formatter.T24H.format);
			return LocalTime.parse(dateStr, format);

		} catch (Exception e) {
			log.error(LOG_LOCAL_TIME_ERROR, e);
		}
		return null;
	}

	public static String sanitizeTime(final String dateStr) {
		try {
			
			if(ValidatorUtil.isNotEmpty(dateStr)) {
				val timeStr = dateStr.toUpperCase();
				val hour = timeStr.split(Pattern.quote(":"))[0]; 
				if(hour.length() < 2) {
					return "0" + timeStr;
				}
				return timeStr;
			}
			
		} catch (Exception e) {
			log.error("UDateTime error {}", e);
		}
		return ValueUtil.EMPTY;
	}
	
	public static LocalTime localTime(final String dateStr, final Formatter formatter) {
		try {
			
			if(ValidatorUtil.isNotEmpty(dateStr) && ValidatorUtil.isNotEmpty(formatter)) {
				val format = DateTimeFormatter.ofPattern(formatter.format);
				return LocalTime.parse(sanitizeTime(dateStr), format);
			}

		} catch (Exception e) {
			log.error(LOG_LOCAL_TIME_ERROR, e);
		}
		return null;
	}

	public static LocalDateTime localDateTime() {
		return LocalDateTime.now();
	}

	public static LocalDateTime localDateTime(final Date date) {
		try {

			if (date != null) {
				val localDate = localDate(date);
				val localTime = localTime(date);
				return LocalDateTime.of(localDate, localTime);
			}

		} catch (Exception e) {
			log.error(LOG_LOCAL_DATE_TIME_ERROR, e);
		}
		return null;
	}

	public static LocalDateTime localDateTime(final Date date, final Date time) {
		try {

			val localDate = localDate(date);
			val localTime = localTime(time);
			if (localDate != null && ValidatorUtil.isNotEmpty(localTime)) {
				return LocalDateTime.of(localDate, localTime);
			}

		} catch (Exception e) {
			log.error(LOG_LOCAL_DATE_TIME_ERROR, e);
		}
		return null;
	}

	public static LocalDateTime localDateTime(final String date, final Formatter formatter) {
		try {

			if (date != null && ValidatorUtil.isNotEmpty(formatter)) {
				val format = DateTimeFormatter.ofPattern(formatter.format);
				return LocalDateTime.parse(date, format);
			}

		} catch (Exception e) {
			log.error(LOG_LOCAL_DATE_TIME_ERROR, e);
		}
		return null;
	}

	public static LocalDateTime localDateTime(final LocalDate localDate, final LocalTime localTime) {
		try {

			if (localDate != null && localTime != null) {
				return LocalDateTime.of(localDate, localTime);
			}

		} catch (Exception e) {
			log.error(LOG_LOCAL_DATE_TIME_ERROR, e);
		}
		return null;
	}

	public static LocalDateTime localDateTime(final LocalDate localDate) {
		try {

			if (localDate != null) {
				return localDate.atTime(LocalTime.now());
			}

		} catch (Exception e) {
			log.error(LOG_LOCAL_DATE_TIME_ERROR, e);
		}
		return null;
	}
	
	public static LocalDateTime localDateTime(final LocalTime localTime) {
		try {

			if (localTime != null) {
				return localTime.atDate(LocalDate.now());
			}

		} catch (Exception e) {
			log.error(LOG_LOCAL_DATE_TIME_ERROR, e);
		}
		return null;
	}

	public static Date date(final Integer year, final Integer month, final Integer day) {
		try {

			if (year != null && month != null && day != null) {
				val localDate = LocalDate.of(year, month, day);
				return date(localDate);
			}

		} catch (Exception e) {
			log.error(LOG_DATE_ERROR, e);
		}
		return null;
	}

	public static Date date(final LocalDateTime localDateTime) {
		try {

			if (localDateTime != null) {
				val instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
				return Date.from(instant);
			}

		} catch (Exception e) {
			log.error(LOG_DATE_ERROR, e);
		}
		return null;
	}
	
	public static Date date(final LocalDate localDate) {
		try {

			if (localDate != null) {
				val instant = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
				return Date.from(instant);
			}

		} catch (Exception e) {
			log.error(LOG_DATE_ERROR, e);
		}
		return null;
	}
	
	public static Date date(final LocalTime localTime) {
		try {

			if (localTime != null) {
				val ldt = localDateTime(localTime);
				if (ldt != null) {
					val instant = ldt.atZone(ZoneId.systemDefault()).toInstant();
					return Date.from(instant);
				}
			}

		} catch (Exception e) {
			log.error(LOG_DATE_ERROR, e);
		}
		return null;
	}

	// ##### LocalDate
	public static boolean isBeforeLocalDate(final Date dateOne, final Date dateTwo) {
		val dateOneLocalDate = localDate(dateOne);
		val dateTwoLocalDate = localDate(dateTwo);
		return isBeforeLocalDate(dateOneLocalDate, dateTwoLocalDate);
	}

	public static boolean isBeforeLocalDate(final LocalDate ldOne, final LocalDate ldTwo) {
		try {

			if (ValidatorUtil.isNotEmpty(ldOne) && ValidatorUtil.isNotEmpty(ldTwo)) {
				return ldOne.isBefore(ldTwo);
			}

		} catch (Exception e) {
			log.error("UDateTime#isBeforeLocalDate error {}", e);
		}
		return false;
	}

	public static boolean isAfterLocalDate(final Date dateOne, final Date dateTwo) {
		val dateOneLocalDate = localDate(dateOne);
		val dateTwoLocaldate = localDate(dateTwo);
		return isAfterLocalDate(dateOneLocalDate, dateTwoLocaldate);
	}

	public static boolean isAfterLocalDate(final LocalDate ldOne, final LocalDate ldTwo) {
		try {

			if (ValidatorUtil.isNotEmpty(ldOne) && ValidatorUtil.isNotEmpty(ldTwo)) {
				return ldOne.isAfter(ldTwo);
			}

		} catch (Exception e) {
			log.error("UDateTime#isAfterLocalDate error {}", e);
		}
		return false;
	}

	public static boolean isEqualLocalDate(final Date dateOne, final Date dateTwo) {
		val dateOneLocalDate = localDate(dateOne);
		val dateTwoLocaldate = localDate(dateTwo);
		return isEqualLocalDate(dateOneLocalDate, dateTwoLocaldate);
	}

	public static boolean isEqualLocalDate(final LocalDate dateOneLocalDate, final LocalDate dateTwoLocaldate) {
		try {

			if (ValidatorUtil.isNotEmpty(dateOneLocalDate) && ValidatorUtil.isNotEmpty(dateTwoLocaldate)) {
				return dateOneLocalDate.isEqual(dateTwoLocaldate);
			}

		} catch (Exception e) {
			log.error("UDateTime#isEqualLocalDate error {}", e);
		}
		return false;
	}

	public static boolean isBeforeOrEqualLocalDate(final Date dateOne, final Date dateTwo) {
		val dateOneLocalDate = localDate(dateOne);
		val dateTwoLocaldate = localDate(dateTwo);
		return isBeforeOrEqualLocalDate(dateOneLocalDate, dateTwoLocaldate);
	}

	public static boolean isBeforeOrEqualLocalDate(final LocalDate ldOne, final LocalDate ldTwo) {
		try {

			if (ValidatorUtil.isNotEmpty(ldOne) && ValidatorUtil.isNotEmpty(ldTwo)) {
				return ldOne.isBefore(ldTwo) || ldOne.isEqual(ldTwo);
			}

		} catch (Exception e) {
			log.error("UDateTime#isBeforeOrEqualLocalDate error {}", e);
		}
		return false;
	}

	public static boolean isAfterOrEqualLocalDate(final Date dateOne, final Date dateTwo) {
		val dateOneLocalDate = localDate(dateOne);
		val dateTwoLocaldate = localDate(dateTwo);
		return isAfterOrEqualLocalDate(dateOneLocalDate, dateTwoLocaldate);
	}

	public static boolean isAfterOrEqualLocalDate(final LocalDate ldOne, final LocalDate ldTwo) {
		try {

			if (ValidatorUtil.isNotEmpty(ldOne) && ValidatorUtil.isNotEmpty(ldTwo)) {
				return ldOne.isAfter(ldTwo) || ldOne.isEqual(ldTwo);
			}

		} catch (Exception e) {
			log.error("UDateTime#isAfterOrEqualLocalDate error {}", e);
		}
		return false;
	}

	public static boolean isInRangeLocalDate(String startDate, String endDate) {
		val now = formatDate(new Date(), Formatter.DATE_SIMPLE_FORMAT);
		return isInRangeLocalDate(now, startDate, endDate);
	}

	public static boolean isInRangeLocalDate(String startDate, String endDate, Formatter fomatter) {
		val now = formatDate(new Date(), fomatter);
		return isInRangeLocalDate(now, startDate, endDate);
	}

	public static boolean isInRangeLocalDate(final String dateToCheck, final String startDate, final String endDate) {
		val toCheck = localDate(dateToCheck, Formatter.DATE_SIMPLE_FORMAT);
		val startInterval = localDate(startDate, Formatter.DATE_SIMPLE_FORMAT);
		val endInterval = localDate(endDate, Formatter.DATE_SIMPLE_FORMAT);
		return isInRangeLocalDate(toCheck, startInterval, endInterval);
	}

	public static boolean isInRangeLocalDate(final String dateToCheck, final String startDate, final String endDate,
			Formatter formatter) {
		val toCheck = localDate(dateToCheck, formatter);
		val startInterval = localDate(startDate, formatter);
		val endInterval = localDate(endDate, formatter);
		return isInRangeLocalDate(toCheck, startInterval, endInterval);
	}

	public static boolean isInRangeLocalDate(final Date dateToCheck, final Date startDate, final Date endDate) {
		val toCheck = localDate(dateToCheck);
		val startInterval = localDate(startDate);
		val endInterval = localDate(endDate);
		return isInRangeLocalDate(toCheck, startInterval, endInterval);
	}

	public static boolean isInRangeLocalDate(final LocalDate toCheck, final LocalDate startInterval,
			final LocalDate endInterval) {
		try {

			if (ValidatorUtil.isNotEmpty(toCheck) && ValidatorUtil.isNotEmpty(startInterval)
					&& ValidatorUtil.isNotEmpty(endInterval)) {
				return isAfterOrEqualLocalDate(toCheck, startInterval)
						&& isBeforeOrEqualLocalDate(toCheck, endInterval);
			}

		} catch (Exception e) {
			log.error("UDateTime#isInRangeLocalDate error {}", e);
		}
		return false;
	}

	// ##### LocalTime
	public static boolean isBeforeLocalTime(final Date dateOne, final Date dateTwo) {
		val timeOne = localTime(dateOne);
		val timeTwo = localTime(dateTwo);
		return isBeforeLocalTime(timeOne, timeTwo);
	}

	public static boolean isBeforeLocalTime(final LocalTime timeOne, final LocalTime timeTwo) {
		try {

			if (ValidatorUtil.isNotEmpty(timeOne) && ValidatorUtil.isNotEmpty(timeTwo)) {
				return timeOne.isBefore(timeTwo);
			}

		} catch (Exception e) {
			log.error("UDateTime#isBeforeLocalTime error {}", e);
		}
		return false;
	}

	public static boolean isAfterLocalTime(final Date dateOne, final Date dateTwo) {
		val timeOne = localTime(dateOne);
		val timeTwo = localTime(dateTwo);
		return isAfterLocalTime(timeOne, timeTwo);
	}

	public static boolean isAfterLocalTime(final LocalTime timeOne, final LocalTime timeTwo) {
		try {

			if (ValidatorUtil.isNotEmpty(timeOne) && ValidatorUtil.isNotEmpty(timeTwo)) {
				return timeOne.isAfter(timeTwo);
			}

		} catch (Exception e) {
			log.error("UDateTime#isAfterLocalTime error {}", e);
		}
		return false;
	}

	public static boolean isEqualLocalTime(final Date dateOne, final Date dateTwo) {
		val timeOne = localTime(dateOne);
		val timeTwo = localTime(dateTwo);
		return isEqualLocalTime(timeOne, timeTwo);
	}

	public static boolean isEqualLocalTime(final LocalTime timeOne, final LocalTime timeTwo) {
		try {

			if (ValidatorUtil.isNotEmpty(timeOne) && ValidatorUtil.isNotEmpty(timeTwo)) {
				return timeOne.equals(timeTwo);
			}

		} catch (Exception e) {
			log.error("UDateTime#isEqualLocalTime error {}", e);
		}
		return false;
	}

	public static boolean isBeforeOrEqualLocalTime(final Date dateOne, final Date dateTwo) {
		val timeOne = localTime(dateOne);
		val timeTwo = localTime(dateTwo);
		return isBeforeOrEqualLocalTime(timeOne, timeTwo);
	}

	public static boolean isBeforeOrEqualLocalTime(final LocalTime timeOne, final LocalTime timeTwo) {
		try {

			if (ValidatorUtil.isNotEmpty(timeOne) && ValidatorUtil.isNotEmpty(timeTwo)) {
				return (timeOne.isBefore(timeTwo) || timeOne.equals(timeTwo));
			}

		} catch (Exception e) {
			log.error("UDateTime#isBeforeOrEqualLocalTime error {}", e);
		}
		return false;
	}

	public static boolean isAfterOrEqualLocalTime(final Date dateOne, final Date dateTwo) {
		try {

			val timeOne = localTime(dateOne);
			val timeTwo = localTime(dateTwo);
			return isAfterOrEqualLocalTime(timeOne, timeTwo);

		} catch (Exception e) {
			log.error("UDateTime#isAfterOrEqualLocalTime error {}", e);
		}
		return false;
	}

	public static boolean isAfterOrEqualLocalTime(final LocalTime timeOne, final LocalTime timeTwo) {
		try {

			if (ValidatorUtil.isEmpty(timeOne) && ValidatorUtil.isEmpty(timeTwo)) {
				return (timeOne.isAfter(timeTwo) || timeOne.equals(timeTwo));
			}

		} catch (Exception e) {
			log.error("UDateTime#isAfterOrEqualLocalTime error {}", e);
		}
		return false;
	}

	public static boolean isInRangeLocalTime(String startTime, String endTime) {
		val now = formatDate(new Date(), Formatter.T24H);
		return isInRangeLocalTime(now, startTime, endTime);
	}

	public static boolean isInRangeLocalTime(String startTime, String endTime, Formatter fomatter) {
		val now = formatDate(new Date(), fomatter);
		return isInRangeLocalTime(now, startTime, endTime);
	}

	public static boolean isInRangeLocalTime(final String timeToCheck, final String startTime, final String endTime) {
		val toCheck = localTime(timeToCheck);
		val startInterval = localTime(startTime);
		val endInterval = localTime(endTime);
		return isInRangeLocalTime(toCheck, startInterval, endInterval);
	}

	public static boolean isInRangeLocalTime(final String timeToCheck, final String startTime, final String endTime,
			Formatter formatter) {
		val toCheck = localTime(timeToCheck, formatter);
		val startInterval = localTime(startTime, formatter);
		val endInterval = localTime(endTime, formatter);
		return isInRangeLocalTime(toCheck, startInterval, endInterval);
	}

	public static boolean isInRangeLocalTime(final Date startDate, final Date endDate) {
		return isInRangeLocalTime(new Date(), startDate, endDate);
	}

	public static boolean isInRangeLocalTime(final Date dateToCheck, final Date startDate, final Date endDate) {
		val toCheck = localTime(dateToCheck);
		val startInterval = localTime(startDate);
		val endInterval = localTime(endDate);
		return isInRangeLocalTime(toCheck, startInterval, endInterval);
	}

	public static boolean isInRangeLocalTime(final LocalTime toCheck, final LocalTime startInterval,
			final LocalTime endInterval) {
		try {

			if (ValidatorUtil.isNotEmpty(toCheck) && ValidatorUtil.isNotEmpty(startInterval)
					&& ValidatorUtil.isNotEmpty(endInterval)) {
				return toCheck.compareTo(startInterval) >= 0 && toCheck.compareTo(endInterval) <= 0;
			}

		} catch (Exception e) {
			log.error("UDateTime#isInRangeLocalTime error {}", e);
		}
		return false;
	}

	// ##### LocalDateTime
	public static boolean isBeforeLocalDateTime(final Date dateOne, final Date dateTwo) {
		val startLdt = localDateTime(dateOne);
		val endLdt = localDateTime(dateTwo);
		return isBeforeLocalDateTime(startLdt, endLdt);
	}

	public static boolean isBeforeLocalDateTime(final LocalDateTime startLdt, LocalDateTime endLdt) {
		try {

			if (ValidatorUtil.isNotEmpty(startLdt) && ValidatorUtil.isNotEmpty(endLdt)) {
				return startLdt.isBefore(endLdt);
			}

		} catch (Exception e) {
			log.error("UDateTime#isBeforeLocalDateTime error {}", e);
		}
		return false;
	}

	public static boolean isAfterLocalDateTime(final Date dateOne, final Date dateTwo) {
		val startLdt = localDateTime(dateOne);
		val endLdt = localDateTime(dateTwo);
		return isAfterLocalDateTime(startLdt, endLdt);
	}

	public static boolean isAfterLocalDateTime(final LocalDateTime startLdt, LocalDateTime endLdt) {
		try {

			if (ValidatorUtil.isNotEmpty(startLdt) && ValidatorUtil.isNotEmpty(endLdt)) {
				return startLdt.isAfter(endLdt);
			}

		} catch (Exception e) {
			log.error("UDateTime#isAfterLocalDateTime error {}", e);
		}
		return false;
	}

	public static boolean isEqualLocalDateTime(final Date dateOne, final Date dateTwo) {
		val startLdt = localDateTime(dateOne);
		val endLdt = localDateTime(dateTwo);
		return isEqualLocalDateTime(startLdt, endLdt);
	}

	public static boolean isEqualLocalDateTime(final LocalDateTime startLdt, final LocalDateTime endLdt) {
		try {

			if (ValidatorUtil.isNotEmpty(startLdt) && ValidatorUtil.isNotEmpty(endLdt)) {
				return startLdt.equals(endLdt);
			}

		} catch (Exception e) {
			log.error("UDateTime#isEqualLocalDateTime error {}", e);
		}
		return false;
	}

	public static boolean isBeforeOrEqualLocalDateTime(final Date dateOne, final Date dateTwo) {
		val startLdt = localDateTime(dateOne);
		val endLdt = localDateTime(dateTwo);
		return isBeforeOrEqualLocalDateTime(startLdt, endLdt);
	}

	public static boolean isBeforeOrEqualLocalDateTime(final LocalDateTime startLdt, LocalDateTime endLdt) {
		try {

			if (ValidatorUtil.isNotEmpty(startLdt) && ValidatorUtil.isNotEmpty(endLdt)) {
				return (startLdt.isBefore(endLdt) || startLdt.equals(endLdt));
			}

		} catch (Exception e) {
			log.error("UDateTime#isBeforeOrEqualLocalDateTime error {}", e);
		}
		return false;
	}

	public static boolean isAfterOrEqualLocalDateTime(final Date dateOne, final Date dateTwo) {
		val startLdt = localDateTime(dateOne);
		val endLdt = localDateTime(dateTwo);
		return isAfterOrEqualLocalDateTime(startLdt, endLdt);
	}

	public static boolean isAfterOrEqualLocalDateTime(final LocalDateTime startLdt, LocalDateTime endLdt) {
		try {

			if (ValidatorUtil.isNotEmpty(startLdt) && ValidatorUtil.isNotEmpty(endLdt)) {
				return (startLdt.isAfter(endLdt) || startLdt.equals(endLdt));
			}

		} catch (Exception e) {
			log.error("UDateTime#isAfterOrEqualLDT error {}", e);
		}
		return false;
	}

	public static boolean isInRangeLocalDateTime(String startDateTime, String endDateTime) {
		val now = formatDate(new Date(), Formatter.DATE_TIME_FULL_FORMAT);
		return isInRangeLocalDate(now, startDateTime, endDateTime);
	}

	public static boolean isInRangeLocalDateTime(String startDateTime, String endDateTime, Formatter fomatter) {
		val now = formatDate(new Date(), fomatter);
		return isInRangeLocalDate(now, startDateTime, endDateTime);
	}

	public static boolean isInRangeLocalDateTime(final String dateTimeToCheck, final String startDateTime,
			final String endDateTime) {
		val toCheck = localDateTime(dateTimeToCheck, Formatter.DATE_TIME_FULL_FORMAT);
		val startInterval = localDateTime(startDateTime, Formatter.DATE_TIME_FULL_FORMAT);
		val endInterval = localDateTime(endDateTime, Formatter.DATE_TIME_FULL_FORMAT);
		return isInRangeLocalDateTime(toCheck, startInterval, endInterval);
	}

	public static boolean isInRangeLocalDateTime(final String dateTimeToCheck, final String startDateTime,
			final String endDateTime, Formatter formatter) {
		val toCheck = localDateTime(dateTimeToCheck, formatter);
		val startInterval = localDateTime(startDateTime, formatter);
		val endInterval = localDateTime(endDateTime, formatter);
		return isInRangeLocalDateTime(toCheck, startInterval, endInterval);
	}

	public static boolean isInRangeLocalDateTime(final Date dateTimeToCheck, final Date startDateTime,
			final Date endDateTime) {
		val toCheck = localDateTime(dateTimeToCheck);
		val startInterval = localDateTime(startDateTime);
		val endInterval = localDateTime(endDateTime);
		return isInRangeLocalDateTime(toCheck, startInterval, endInterval);
	}

	public static boolean isInRangeLocalDateTime(final LocalDateTime toCheck, final LocalDateTime startInterval,
			final LocalDateTime endInterval) {
		try {

			if (ValidatorUtil.isNotEmpty(toCheck) && ValidatorUtil.isNotEmpty(startInterval)
					&& ValidatorUtil.isNotEmpty(endInterval)) {
				return isAfterOrEqualLocalDateTime(toCheck, startInterval)
						&& isBeforeOrEqualLocalDateTime(toCheck, endInterval);
			}

		} catch (Exception e) {
			log.error("UDateTime#isInRangeLocalDateTime error {}", e);
		}
		return false;
	}

	// ####################
	// ##### Duration #####
	// ####################
	/**
	 * Returns the duration between two dates (LocalDateTime)
	 * 
	 * @param startLdt Start inclusive
	 * @param endLdt   End exclusive
	 * @return the {@code java.time.Duration} between two dates
	 */
	public static Duration duration(final LocalDateTime startLdt, final LocalDateTime endLdt) {
		try {

			if (startLdt != null && endLdt != null) {
				return Duration.between(startLdt, endLdt);
			}

		} catch (Exception e) {
			log.error(LOG_DURATION_ERROR, e);
		}
		return null;
	}
	
	/**
	 * Returns the duration between two dates (LocalDate)
	 * 
	 * @param ldOne Start inclusive
	 * @param ldTwo End exclusive
	 * @return the {@code java.time.Duration} between two date
	 */
	public static Duration duration(final LocalDate ldOne, final LocalDate ldTwo) {
		try {

			if (ldOne != null && ldTwo != null) {
				return Duration.between(ldOne, ldTwo);
			}

		} catch (Exception e) {
			log.error(LOG_DURATION_ERROR, e);
		}
		return null;
	}
	
	/**
	 * Returns the duration between two dates (LocalTime)
	 * 
	 * @param ltOne Start inclusive
	 * @param ltTwo End exclusive
	 * @return the {@code java.time.Duration} between two date
	 */
	public static Duration duration(final LocalTime ltOne, final LocalTime ltTwo) {
		try {

			if (ltOne != null && ltTwo != null) {
				return Duration.between(ltOne, ltTwo);
			}

		} catch (Exception e) {
			log.error(LOG_DURATION_ERROR, e);
		}
		return null;
	}
	
	/**
	 * Returns the duration between two dates
	 * 
	 * @param dateOne Start inclusive
	 * @param dateTwo End exclusive
	 * @return the {@code java.time.Duration} between two dates
	 */
	public static Duration duration(final Date dateOne, final Date dateTwo) {
		try {

			if (dateOne != null && dateTwo != null) {
				return duration(localDateTime(dateOne), localDateTime(dateTwo));
			}

		} catch (Exception e) {
			log.error(LOG_DURATION_ERROR, e);
		}
		return null;
	}

	/**
	 * Returns the duration (int) in days between two dates (LocalDateTime)
	 * 
	 * @param startLdt Start inclusive
	 * @param endLdt   End exclusive
	 * @return the duration (int) in days between two dates
	 */
	public static Integer durationInDates(final LocalDateTime startLdt, final LocalDateTime endLdt) {
		try {

			if (startLdt != null && endLdt != null) {
				val duration = Duration.between(startLdt, endLdt);
				Long durationInDays = duration.toDays();
				return durationInDays.intValue();
			}

		} catch (Exception e) {
			log.error(LOG_DURATION_IN_DATES_ERROR, e);
		}
		return null;
	}

	/**
	 * Returns the duration (int) in days between two dates (LocalDate)
	 * 
	 * @param ldOne Start inclusive
	 * @param ldTwo End exclusive
	 * @return the duration (int) in days between two dates
	 */
	public static Integer durationInDates(final LocalDate ldOne, final LocalDate ldTwo) {
		try {

			if (ldOne != null && ldTwo != null) {
				val duration = Duration.between(ldOne, ldTwo);
				Long durationInDays = duration.toDays();
				return durationInDays.intValue();
			}

		} catch (Exception e) {
			log.error(LOG_DURATION_IN_DATES_ERROR, e);
		}
		return null;
	}

	/**
	 * Returns the duration (int) in days between two dates
	 * 
	 * @param dateOne Start inclusive
	 * @param dateTwo End exclusive
	 * @return the duration (int) in days between two dates
	 */
	public static Integer durationInDates(final Date dateOne, final Date dateTwo) {
		try {

			if (dateOne != null && dateTwo != null) {
				val duration = duration(dateOne, dateTwo);
				if (duration != null) {
					Long durationInDays = duration.toDays();
					return durationInDays.intValue();
				}
			}

		} catch (Exception e) {
			log.error(LOG_DURATION_IN_DATES_ERROR, e);
		}
		return null;
	}

	/**
	 * Returns the duration (int) in days between two dates without weekends
	 * 
	 * @param dateOne Start inclusive
	 * @param dateTwo End exclusive
	 * @return the duration (int) in days between two dates without weekends
	 */
	public static Integer durationInDatesWithoutWeekend(final Date dateOne, final Date dateTwo) {
		try {

			var start = localDate(dateOne);
			val stop = localDate(dateTwo);

			if (start != null && stop != null) {
				var count = 0;

				Set<DayOfWeek> weekend = EnumSet.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
				while (start.isBefore(stop)) {
					if (!weekend.contains(start.getDayOfWeek())) {
						// ##### It is not weekend
						count++;
					}

					// ##### Increment a day
					start = start.plusDays(1);
				}
				return count;
			}

		} catch (Exception e) {
			log.error("UDateTime#durationInDatesWithoutWeekend error {}", e);
		}
		return null;
	}

	/**
	 * Returns the duration (int) in hours between two dates (LocalDateTime)
	 * 
	 * @param startLdt Start inclusive
	 * @param endLdt   End exclusive
	 * @return the duration (int) in hours between two dates
	 */
	public static Integer durationInHours(final LocalDateTime startLdt, final LocalDateTime endLdt) {
		try {

			if (startLdt != null && endLdt != null) {
				val duration = duration(startLdt, endLdt);
				if (duration != null) {
					Long durationInMinutes = duration.toHours();
					return durationInMinutes.intValue();
				}
			}

		} catch (Exception e) {
			log.error(LOG_DURATION_IN_HOURS_ERROR, e);
		}
		return null;
	}

	/**
	 * Returns the duration (int) in hours between two dates (LocalDate)
	 * 
	 * @param startLd Start inclusive
	 * @param endLd   End exclusive
	 * @return the duration (int) in hours between two dates
	 */
	public static Integer durationInHours(final LocalDate startLd, final LocalDate endLd) {
		try {

			if (startLd != null && endLd != null) {
				val duration = duration(startLd, endLd);
				if (duration != null) {
					Long durationInMinutes = duration.toHours();
					return durationInMinutes.intValue();
				}
			}

		} catch (Exception e) {
			log.error(LOG_DURATION_IN_HOURS_ERROR, e);
		}
		return null;
	}
	
	/**
	 * Returns the duration (int) in hours between two dates (LocalTime)
	 * 
	 * @param startTime Start inclusive
	 * @param endTime   End exclusive
	 * @return the duration (int) in hours between two dates
	 */
	public static Integer durationInHours(final LocalTime startTime, final LocalTime endTime) {
		try {

			if (startTime != null && endTime != null) {
				val duration = duration(startTime, endTime);
				if (duration != null) {
					Long durationInMinutes = duration.toHours();
					return durationInMinutes.intValue();
				}
			}

		} catch (Exception e) {
			log.error(LOG_DURATION_IN_HOURS_ERROR, e);
		}
		return null;
	}

	/**
	 * Returns the duration (int) in hours between two dates
	 * 
	 * @param dateOne Start inclusive
	 * @param dateTwo End exclusive
	 * @return the duration (int) in hours between two dates
	 */
	public static Integer durationInHours(final Date dateOne, final Date dateTwo) {
		try {

			if (dateOne != null && dateTwo != null) {
				val duration = duration(dateOne, dateTwo);
				if (duration != null) {
					Long durationInHours = duration.toHours();
					return durationInHours.intValue();
				}
			}

		} catch (Exception e) {
			log.error(LOG_DURATION_IN_HOURS_ERROR, e);
		}
		return null;
	}

	/**
	 * Returns the duration (int) in minutes between two dates (LocalDateTime)
	 * 
	 * @param startLdt Start inclusive
	 * @param endLdt   End exclusive
	 * @return the duration (int) in minutes between two dates
	 */
	public static Integer durationInMinutes(final LocalDateTime startLdt, final LocalDateTime endLdt) {
		try {

			if (startLdt != null && endLdt != null) {
				val duration = duration(startLdt, endLdt);
				if (duration != null) {
					Long durationInMinutes = duration.toMinutes();
					return durationInMinutes.intValue();
				}
			}

		} catch (Exception e) {
			log.error(LOG_DURATION_IN_MINUTES_ERROR, e);
		}
		return null;
	}

	/**
	 * Returns the duration (int) in minutes between two dates (LocalDate)
	 * 
	 * @param startLd Start inclusive
	 * @param endLd   End exclusive
	 * @return the duration (int) in minutes between two dates
	 */
	public static Integer durationInMinutes(final LocalDate startLd, final LocalDate endLd) {
		try {

			if (startLd != null && endLd != null) {
				val duration = duration(startLd, endLd);
				if (duration != null) {
					Long durationInMinutes = duration.toMinutes();
					return durationInMinutes.intValue();
				}
			}

		} catch (Exception e) {
			log.error(LOG_DURATION_IN_MINUTES_ERROR, e);
		}
		return null;
	}
	
	/**
	 * Returns the duration (int) in minutes between two dates (LocalTime)
	 * 
	 * @param startTime Start inclusive
	 * @param endTime   End exclusive
	 * @return the duration (int) in minutes between two dates
	 */
	public static Integer durationInMinutes(final LocalTime startTime, final LocalTime endTime) {
		try {

			if (startTime != null && endTime != null) {
				val duration = duration(startTime, endTime);
				if (duration != null) {
					Long durationInMinutes = duration.toMinutes();
					return durationInMinutes.intValue();
				}
			}

		} catch (Exception e) {
			log.error(LOG_DURATION_IN_MINUTES_ERROR, e);
		}
		return null;
	}

	/**
	 * Returns the duration (int) in minutes between two dates
	 * 
	 * @param dateOne Start inclusive
	 * @param dateTwo End exclusive
	 * @return the duration (int) in minutes between two dates
	 */
	public static Integer durationInMinutes(final Date dateOne, final Date dateTwo) {
		try {

			if (dateOne != null && dateTwo != null) {
				val duration = duration(dateOne, dateTwo);
				if (duration != null) {
					Long durationInMinutes = duration.toMinutes();
					return durationInMinutes.intValue();
				}
			}

		} catch (Exception e) {
			log.error(LOG_DURATION_IN_MINUTES_ERROR, e);
		}
		return null;
	}

	/**
	 * Returns the duration (int) in seconds between two dates (LocalDateTime)
	 * 
	 * @param startLdt Start inclusive
	 * @param endLdt   End exclusive
	 * @return the duration (int) in seconds between two dates
	 */
	public static Integer durationInSeconds(final LocalDateTime startLdt, final LocalDateTime endLdt) {
		try {

			if (startLdt != null && endLdt != null) {
				val duration = duration(startLdt, endLdt);
				if (duration != null) {
					Long durationInMinutes = duration.toSeconds();
					return durationInMinutes.intValue();
				}
			}

		} catch (Exception e) {
			log.error(LOG_DURATION_IN_SECONDS_ERROR, e);
		}
		return null;
	}

	/**
	 * Returns the duration (int) in seconds between two dates (LocalDate)
	 * 
	 * @param startLd Start inclusive
	 * @param endLd   End exclusive
	 * @return the duration (int) in seconds between two dates
	 */
	public static Integer durationInSeconds(final LocalDate startLd, final LocalDate endLd) {
		try {

			if (startLd != null && endLd != null) {
				val duration = duration(startLd, endLd);
				if (duration != null) {
					Long durationInMinutes = duration.toSeconds();
					return durationInMinutes.intValue();
				}
			}

		} catch (Exception e) {
			log.error(LOG_DURATION_IN_SECONDS_ERROR, e);
		}
		return null;
	}
	
	/**
	 * Returns the duration (int) in seconds between two dates (LocalTime)
	 * 
	 * @param startTime Start inclusive
	 * @param endTime   End exclusive
	 * @return the duration (int) in seconds between two dates
	 */
	public static Integer durationInSeconds(final LocalTime startTime, final LocalTime endTime) {
		try {

			if (startTime != null && endTime != null) {
				val duration = duration(startTime, endTime);
				if (duration != null) {
					Long durationInMinutes = duration.toSeconds();
					return durationInMinutes.intValue();
				}
			}

		} catch (Exception e) {
			log.error(LOG_DURATION_IN_SECONDS_ERROR, e);
		}
		return null;
	}

	/**
	 * Returns the duration (int) in seconds between two dates
	 * 
	 * @param dateOne Start inclusive
	 * @param dateTwo End exclusive
	 * @return the duration (int) in seconds between two dates
	 */
	public static Integer durationInSeconds(final Date dateOne, final Date dateTwo) {
		try {

			if (dateOne != null && dateTwo != null) {
				val duration = duration(dateOne, dateTwo);
				if (duration != null) {
					Long durationInMinutes = duration.toSeconds();
					return durationInMinutes.intValue();
				}
			}

		} catch (Exception e) {
			log.error(LOG_DURATION_IN_SECONDS_ERROR, e);
		}
		return null;
	}

	/**
	 * Returns the duration (int) in milliseconds between two dates (LocalDateTime)
	 * 
	 * @param startLdt Start inclusive
	 * @param endLdt   End exclusive
	 * @return the duration (int) in milliseconds between two dates
	 */
	public static Integer durationInMilliseconds(final LocalDateTime startLdt, final LocalDateTime endLdt) {
		try {

			if (startLdt != null && endLdt != null) {
				val duration = duration(startLdt, endLdt);
				if (duration != null) {
					Long durationInMinutes = duration.toMillis();
					return durationInMinutes.intValue();
				}
			}

		} catch (Exception e) {
			log.error(LOG_DURATION_IN_MILLISECONDS_ERROR, e);
		}
		return null;
	}

	/**
	 * Returns the duration (int) in milliseconds between two dates (LocalDate)
	 * 
	 * @param startLd Start inclusive
	 * @param endLd   End exclusive
	 * @return the duration (int) in milliseconds between two dates
	 */
	public static Integer durationInMilliseconds(final LocalDate startLd, final LocalDate endLd) {
		try {

			if (startLd != null && endLd != null) {
				val duration = duration(startLd, endLd);
				if (duration != null) {
					Long durationInMinutes = duration.toMillis();
					return durationInMinutes.intValue();
				}
			}

		} catch (Exception e) {
			log.error(LOG_DURATION_IN_MILLISECONDS_ERROR, e);
		}
		return null;
	}
	
	/**
	 * Returns the duration (int) in milliseconds between two dates (LocalTime)
	 * 
	 * @param startTime Start inclusive
	 * @param endTime   End exclusive
	 * @return the duration (int) in milliseconds between two dates
	 */
	public static Integer durationInMilliseconds(final LocalTime startTime, final LocalTime endTime) {
		try {

			if (startTime != null && endTime != null) {
				val duration = duration(startTime, endTime);
				if (duration != null) {
					Long durationInMinutes = duration.toMillis();
					return durationInMinutes.intValue();
				}
			}

		} catch (Exception e) {
			log.error(LOG_DURATION_IN_MILLISECONDS_ERROR, e);
		}
		return null;
	}

	/**
	 * Returns the duration (int) in milliseconds between two dates
	 * 
	 * @param dateOne Start inclusive
	 * @param dateTwo End exclusive
	 * @return the duration (int) in milliseconds between two dates
	 */
	public static Integer durationInMilliseconds(final Date dateOne, final Date dateTwo) {
		try {

			if (dateOne != null && dateTwo != null) {
				val duration = duration(dateOne, dateTwo);
				if (duration != null) {
					Long durationInMilliseconds = duration.toMillis();
					return durationInMilliseconds.intValue();
				}
			}

		} catch (Exception e) {
			log.error(LOG_DURATION_IN_MILLISECONDS_ERROR, e);
		}
		return null;
	}

	/**
	 * Returns the duration (int) between a given date and current date
	 * 
	 * @param date Start inclusive
	 * @param type (String) Has to be either 'days', 'hours', 'minutes',
	 *             'seconds', 'millis' or 'nanos'
	 * @return the duration (int) between a given date and current date
	 */
	public static Integer elapsedTime(final Date date, final String type) {
		try {

			if (date != null && ValidatorUtil.isNotEmpty(type)) {
				val ldt = localDateTime(date);
				val duration = Duration.between(ldt, localDateTime(new Date()));

				return elapsedTime(duration, type);
			}

		} catch (Exception e) {
			log.error(LOG_ELAPSED_TIME_ERROR, e);
		}
		return null;
	}

	/**
	 * Returns the duration (int) between a given date and current date
	 * 
	 * @param date Start inclusive
	 * @param type (String) Has to be either 'days', 'hours', 'minutes',
	 *             'seconds', 'millis' or 'nanos'
	 * @return the duration (int) between a given date and current date
	 */
	public static Integer elapsedTime(final Date date, final Date time, final String type) {
		try {

			if (date != null && ValidatorUtil.isNotEmpty(time) && ValidatorUtil.isNotEmpty(type)) {
				val ldt = localDateTime(localDate(date), localTime(time));
				val duration = Duration.between(ldt, localDateTime(new Date()));

				return elapsedTime(duration, type);
			}

		} catch (Exception e) {
			log.error(LOG_ELAPSED_TIME_ERROR, e);
		}
		return null;
	}

	/**
	 * Returns the duration (int) between a given date (LocalDateTime) and current date
	 * 
	 * @param ldt  Start inclusive
	 * @param type (String) Has to be either 'days', 'hours', 'minutes',
	 *             'seconds', 'millis' or 'nanos'
	 * @return the duration (int) between a given date and current date
	 */
	public static Integer elapsedTime(final LocalDateTime ldt, final String type) {
		try {

			if (ldt != null && ValidatorUtil.isNotEmpty(type)) {
				val duration = Duration.between(ldt, LocalDateTime.now());
				return elapsedTime(duration, type);
			}

		} catch (Exception e) {
			log.error(LOG_ELAPSED_TIME_ERROR, e);
		}
		return null;
	}

	/**
	 * Returns the duration (int) between a given date (Timestamp) and current date
	 * 
	 * @param timestamp Start inclusive
	 * @param type      (String) Has to be either 'days', 'hours', 'minutes',
	 *                  'seconds', 'millis' or 'nanos'
	 * @return the duration (int) between a given date and current date
	 */
	public static Integer elapsedTime(final Timestamp timestamp, final String type) {
		try {

			if (ValidatorUtil.isNotEmpty(timestamp) && ValidatorUtil.isNotEmpty(type)) {
				val ldt = localDateTime(timestamp);
				val duration = Duration.between(ldt, LocalDateTime.now());
				return elapsedTime(duration, type);
			}

		} catch (Exception e) {
			log.error(LOG_ELAPSED_TIME_ERROR, e);
		}
		return null;
	}

	/**
	 * Returns the duration (long) between two dates using
	 * {@code java.time.temporal.ChronoUnit}
	 * 
	 * @param dateOne    Start inclusive
	 * @param dateTwo    End exclusive
	 * @param chronoUnit A {@code ChronoUnit} value
	 * @return A long value that represents the difference between two dates
	 *         determined by the chronoUnit value provided
	 */
	public static Long elapsedTime(final Date dateOne, final Date dateTwo, final ChronoUnit chronoUnit) {
		try {

			if (ValidatorUtil.isNotEmpty(dateOne) && ValidatorUtil.isNotEmpty(dateTwo)) {
				return elapsedTime(localDateTime(dateOne), localDateTime(dateTwo), chronoUnit);
			}

		} catch (Exception e) {
			log.error(LOG_ELAPSED_TIME_ERROR, e);
		}
		return null;
	}

	/**
	 * Returns the duration (long) between two dates using
	 * {@code java.time.temporal.ChronoUnit}
	 * 
	 * @param from       Start inclusive
	 * @param to         End exclusive
	 * @param chronoUnit A {@code ChronoUnit} value
	 * @return A long value that represents the difference between two dates
	 *         determined by the chronoUnit value provided
	 */
	public static Long elapsedTime(final LocalDateTime from, final LocalDateTime to, final ChronoUnit chronoUnit) {
		try {

			if (from != null && to != null) {
				return switch (chronoUnit) {
				case YEARS -> ChronoUnit.YEARS.between(from, to);
				case MONTHS -> ChronoUnit.MONTHS.between(from, to);
				case WEEKS -> ChronoUnit.WEEKS.between(from, to);
				case DAYS -> ChronoUnit.DAYS.between(from, to);
				case HOURS -> ChronoUnit.HOURS.between(from, to);
				case MINUTES -> ChronoUnit.MINUTES.between(from, to);
				case SECONDS -> ChronoUnit.SECONDS.between(from, to);
				case MILLIS -> ChronoUnit.MILLIS.between(from, to);
				case NANOS -> ChronoUnit.NANOS.between(from, to);
				default -> null;
				};
			}

		} catch (Exception e) {
			log.error(LOG_ELAPSED_TIME_ERROR, e);
		}
		return null;
	}

	private static Integer elapsedTime(final Duration duration, final String type) {
		try {

			if (duration != null && ValidatorUtil.isNotEmpty(type)) {
				var durationValue = switch (type) {
					case DURATION_DAYS -> duration.toDays();
					case DURATION_HOURS -> duration.toHours();
					case DURATION_MINUTES -> duration.toMinutes();
					case DURATION_SECONDS -> duration.toSeconds();
					case DURATION_MILLIS -> duration.toMillis();
					case DURATION_NANOS -> duration.toNanos();
					default -> null;
				};
				
				if (durationValue != null) {
					return durationValue.intValue();
				}
			}

		} catch (Exception e) {
			log.error(LOG_ELAPSED_TIME_ERROR, e);
		}
		return 0;
	}

	/**
	 * Returns the UTC date
	 * 
	 * @return the UTC date
	 */
	public static Date dateUTC() {
		try {

			val zdt = ZonedDateTime.now(ZoneOffset.UTC);
			val ldt = zdt.toLocalDateTime();
			return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());

		} catch (Exception e) {
			log.error("UDateTime#UTC error {}", e);
		}
		return null;
	}

	/**
	 * Returns true if the given year is a leap year
	 * 
	 * @param year Four-number year
	 * @return true/false
	 */
	public static Boolean isLeapYear(final Integer year) {
		if (ValidatorUtil.isNotEmpty(year)) {
			return Year.isLeap(year);
		}
		return false;
	}

	/**
	 * Add years to current date
	 * 
	 * @param years (Long) years
	 * @return The current date plus the indicated years
	 */
	public static Date plusYear(final Long years) {
		return plusYear(new Date(), years);
	}
	
	/**
	 * Add years to a date
	 * 
	 * @param date  Given date
	 * @param years (Long) years
	 * @return The given date plus the indicated years
	 */
	public static Date plusYear(final Date date, final Long years) {
		try {

			if (date != null && ValidatorUtil.isNotEmpty(years)) {
				val currentTime = localDate(date);
				if(currentTime != null) {
					val nextDate = currentTime.plusYears(years);
					return date(nextDate);
				}
			}

		} catch (Exception e) {
			log.error("UDateTime#plusYear error {}", e);
		}
		return null;
	}

	/**
	 * Subtracts years from current date
	 * 
	 * @param years (Long) years
	 * @return The current date minus the indicated years
	 */
	public static Date minusYear(final Long years) {
		return minusYear(new Date(), years);
	}
	
	/**
	 * Subtracts years from a date
	 * 
	 * @param years (Long) years
	 * @return The given date minus the indicated years
	 */
	public static Date minusYear(final Date date, final Long years) {
		try {

			if (date != null && ValidatorUtil.isNotEmpty(years)) {
				val currentTime = localDate(date);
				if(currentTime != null) {
					val beforeDate = currentTime.minusYears(years);
					return date(beforeDate);
				}
			}

		} catch (Exception e) {
			log.error("UDateTime#minusYear error {}", e);
		}
		return null;
	}

	/**
	 * Adds months to current date
	 * 
	 * @param months (Long) months
	 * @return The current date plus the indicated months
	 */
	public static Date plusMonth(final Long months) {
		return plusMonth(new Date(), months);
	}
	
	/**
	 * Add months to a date
	 * 
	 * @param date   Given date
	 * @param months (Long) months
	 * @return The given date plus the indicated months
	 */
	public static Date plusMonth(final Date date, final Long months) {
		try {

			if (date != null && ValidatorUtil.isNotEmpty(months)) {
				val currentTime = localDate(date);
				if(currentTime != null) {
					val nextDate = currentTime.plusMonths(months);
					return date(nextDate);
				}
			}

		} catch (Exception e) {
			log.error("UDateTime#plusMonth error {}", e);
		}
		return null;
	}

	/**
	 * Subtracts months from current date
	 * 
	 * @param months (Long) months
	 * @return The current date minus the indicated months
	 */
	public static Date minusMonth(final Long months) {
		return minusMonth(new Date(), months);
	}
	
	/**
	 * Subtracts months from a date
	 * 
	 * @param months (Long) months
	 * @return The given date minus the indicated months
	 */
	public static Date minusMonth(final Date date, final Long months) {
		try {

			if (date != null && ValidatorUtil.isNotEmpty(months)) {
				val currentTime = localDate(date);
				if(currentTime != null) {
					val beforeDate = currentTime.minusMonths(months);
					return date(beforeDate);
				}
			}

		} catch (Exception e) {
			log.error("UDateTime#minusMonth error {}", e);
		}
		return null;
	}

	/**
	 * Add days to current date
	 * 
	 * @param days (Long) days
	 * @return The current date plus the indicated days
	 */
	public static Date plusDays(final Long days) {
		return plusDays(new Date(), days);
	}
	
	/**
	 * Add days to a date
	 * 
	 * @param date Given date
	 * @param days (Long) days
	 * @return The given date plus the indicated days
	 */
	public static Date plusDays(final Date date, final Long days) {
		try {

			if (date != null && ValidatorUtil.isNotEmpty(days)) {
				val currentTime = localDateTime(date);
				if(currentTime != null) {
					val nextDate = currentTime.plusDays(days);
					return date(nextDate);
				}
			}

		} catch (Exception e) {
			log.error("UDateTime#plusDays error {}", e);
		}
		return null;
	}

	/**
	 * Subtracts days from current date
	 * 
	 * @param days (Long) days
	 * @return The current date minus the indicated days
	 */
	public static Date minusDays(final Long days) {
		return minusDays(new Date(), days);
	}
	
	/**
	 * Subtracts days from a date
	 * 
	 * @param days (Long) days
	 * @return The given date minus the indicated days
	 */
	public static Date minusDays(final Date date, final Long days) {
		try {

			if (date != null && ValidatorUtil.isNotEmpty(days)) {
				val currentTime = localDate(date);
				if(currentTime != null) {
					val beforeDate = currentTime.minusDays(days);
					return date(beforeDate);
				}
			}

		} catch (Exception e) {
			log.error("UDateTime#minusDays error {}", e);
		}
		return null;
	}

	/**
	 * Add hours to current date
	 * 
	 * @param hours (Long) hours
	 * @return The current date plus the indicated hours
	 */
	public static Date plusHours(final Long hours) {
		return plusHours(new Date(), hours);
	}
		
	/**
	 * Add hours to a date
	 * 
	 * @param date  Given date
	 * @param hours (Long) hours
	 * @return The given date plus the indicated hours
	 */
	public static Date plusHours(final Date date, final Long hours) {
		try {

			if (date != null && ValidatorUtil.isNotEmpty(hours)) {
				val currentTime = localDateTime(date);
				if(currentTime != null) {
					val nextTime = currentTime.plusHours(hours);
					return date(nextTime);
				}
			}

		} catch (Exception e) {
			log.error("UDateTime#plusHours error {}", e);
		}
		return null;
	}

	/**
	 * Subtracts hours from current date
	 * 
	 * @param hours (Long) hours
	 * @return The current date minus the indicated hours
	 */
	public static Date minusHours(final Long hours) {
		return minusHours(new Date(), hours);
	}
	
	/**
	 * Subtracts hours from a date
	 * 
	 * @param hours (Long) hours
	 * @return The given date minus the indicated hours
	 */
	public static Date minusHours(final Date date, final Long hours) {
		try {

			if (date != null && ValidatorUtil.isNotEmpty(hours)) {
				val currentTime = localDateTime(date);
				if(currentTime != null) {
					val beforeDate = currentTime.minusHours(hours);
					return date(beforeDate);
				}
			}

		} catch (Exception e) {
			log.error("UDateTime#minusHours error {}", e);
		}
		return null;
	}

	/**
	 * Add minutes to current date
	 * 
	 * @param minutes (Long) minutes
	 * @return The current date plus the indicated minutes
	 */
	public static Date plusMinutes(final Long minutes) {
		return plusMinutes(new Date(), minutes);
	}
	
	/**
	 * Add minutes to a date
	 * 
	 * @param date    Given date
	 * @param minutes (Long) minutes
	 * @return The given date plus the indicated minutes
	 */
	public static Date plusMinutes(final Date date, final Long minutes) {
		try {

			if (date != null && ValidatorUtil.isNotEmpty(minutes)) {
				val currentTime = localDateTime(date);
				if(currentTime != null) {
					val nextTime = currentTime.plusMinutes(minutes);
					return date(nextTime);
				}
			}

		} catch (Exception e) {
			log.error("UDateTime#plusMinutes error {}", e);
		}
		return null;
	}

	/**
	 * Subtracts minutes from current date
	 * 
	 * @param minutes (Long) minutes
	 * @return The current date minus the indicated minutes
	 */
	public static Date minusMinutes(final Long minutes) {
		return minusMinutes(new Date(), minutes);
	}
	
	/**
	 * Subtracts minutes from a date
	 * 
	 * @param minutes (Long) minutes
	 * @return The given date minus the indicated minutes
	 */
	public static Date minusMinutes(final Date date, final Long minutes) {
		try {

			if (date != null && ValidatorUtil.isNotEmpty(minutes)) {
				val currentTime = localDateTime(date);
				if(currentTime != null) {
					val beforeDate = currentTime.minusMinutes(minutes);
					return date(beforeDate);
				}
			}

		} catch (Exception e) {
			log.error("UDateTime#minusMinutes error {}", e);
		}
		return null;
	}

	/**
	 * Add seconds to current date
	 * 
	 * @param seconds (Long) seconds
	 * @return The current date plus the indicated seconds
	 */
	public static Date plusSeconds(final Long seconds) {
		return plusSeconds(new Date(), seconds);
	}
	
	/**
	 * Add seconds to a date
	 * 
	 * @param date    Given date
	 * @param seconds (Long) seconds
	 * @return The given date plus the indicated seconds
	 */
	public static Date plusSeconds(final Date date, final Long seconds) {
		try {

			if (date != null && ValidatorUtil.isNotEmpty(seconds)) {
				val currentTime = localDateTime(date);
				if (currentTime != null) {
					val nextTime = currentTime.plusSeconds(seconds);
					return date(nextTime);
				}
			}

		} catch (Exception e) {
			log.error("UDateTime#plusSeconds error {}", e);
		}
		return null;
	}

	/**
	 * Subtracts seconds from current date
	 * 
	 * @param seconds (Long) seconds
	 * @return The current date minus the indicated seconds
	 */
	public static Date minusSeconds(final Long seconds) {
		return minusSeconds(new Date(), seconds);
	}
	
	/**
	 * Subtracts seconds from a date
	 * 
	 * @param seconds (Long) seconds
	 * @return The given date minus the indicated seconds
	 */
	public static Date minusSeconds(final Date date, final Long seconds) {
		try {

			if (date != null && ValidatorUtil.isNotEmpty(seconds)) {
				val currentTime = localDateTime(date);
				if(currentTime != null) {
					val beforeDate = currentTime.minusSeconds(seconds);
					return date(beforeDate);
				}
			}

		} catch (Exception e) {
			log.error("UDateTime#minusSeconds error {}", e);
		}
		return null;
	}

	// ##################
	// ##### Period #####
	// ##################
	public static Period period(final Date dateOne, final Date dateTwo) {
		try {

			if (ValidatorUtil.isNotEmpty(dateOne) && ValidatorUtil.isNotEmpty(dateTwo)) {
				return Period.between(localDate(dateOne), localDate(dateTwo));
			}

		} catch (Exception e) {
			log.error("UDateTime#period error {}", e);
		}
		return null;
	}

	public static Period period(final LocalDate ldOne, final LocalDate ldTwo) {
		try {

			if (ValidatorUtil.isNotEmpty(ldOne) && ValidatorUtil.isNotEmpty(ldTwo)) {
				return Period.between(ldOne, ldTwo);
			}

		} catch (Exception e) {
			log.error("UDateTime#period error {}", e);
		}
		return null;
	}

	public static Integer periodMonthsInt(final Date dateOne, final Date dateTwo) {
		try {

			if (ValidatorUtil.isNotEmpty(dateOne) && ValidatorUtil.isNotEmpty(dateTwo)) {
				return Period.between(localDate(dateOne), localDate(dateTwo)).getMonths();
			}

		} catch (Exception e) {
			log.error("UDateTime#periodMonthsInt error {}", e);
		}
		return null;
	}

	public static Integer periodYearsInt(final Date dateOne, final Date dateTwo) {
		try {

			if (ValidatorUtil.isNotEmpty(dateOne) && ValidatorUtil.isNotEmpty(dateTwo)) {
				return Period.between(localDate(dateOne), localDate(dateTwo)).getYears();
			}

		} catch (Exception e) {
			log.error("UDateTime#periodYearsInt error {}", e);
		}
		return null;
	}

	// ######################
	// ##### ChronoUnit #####
	// ######################
	public static Long periodYearsLong(final Date dateOne, final Date dateTwo) {
		try {

			if (ValidatorUtil.isNotEmpty(dateOne) && ValidatorUtil.isNotEmpty(dateTwo)) {
				return ChronoUnit.YEARS.between(localDate(dateOne), localDate(dateTwo));
			}

		} catch (Exception e) {
			log.error("UDateTime#periodYearsLong error {}", e);
		}
		return null;
	}

	/**
	 * Sorts a list of date
	 * 
	 * @param dateList
	 * @return
	 */
	public static List<Date> sortDate(List<Date> dateList) {
		try {

			if (ValidatorUtil.isNotEmpty(dateList)) {
				dateList.sort((ld1, ld2) -> ld1.compareTo(ld2));
				return dateList;
			}

		} catch (Exception e) {
			log.error("UDateTime#sortDate error {}", e);
		}
		return Collections.emptyList();
	}

	/**
	 * Returns true if the date represents a weekend
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isWeekend(final Date date) {
		try {

			val localDate = localDate(date);
			if (localDate != null) {
				Set<DayOfWeek> weekend = EnumSet.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
				return weekend.contains(localDate.getDayOfWeek());
			}

		} catch (Exception e) {
			log.error("UDateTime#isWeekend error {}", e);
		}
		return false;
	}

	public static Integer hour(final Date date) {
		try {

			if (date != null) {
				val time = formatDate(date, Formatter.T12H);
				if (time != null) {
					val splitted = time.split(":");
					return IntegerUtil.value(splitted[0]);
				}
			}

		} catch (Exception e) {
			log.error("UDateTime#hour error {}", e);
		}
		return null;
	}

	public static String[] daysOfWeek() {
		val monday = "date.text.day.monday";
		val tuesday = "date.text.day.tuesday";
		val wednesday = "date.text.day.wednesday";
		val thursday = "date.text.day.thursday";
		val friday = "date.text.day.friday";
		val saturday = "date.text.day.saturday";
		val sunday = "date.text.day.sunday";
		return new String[] { monday, tuesday, wednesday, thursday, friday, saturday, sunday };
	}

	public static LocalDate nextWeek(final Date date) {
		try {

			val localDate = localDate(date);
			if (localDate != null) {
				val dayOfWeek = localDate.getDayOfWeek().getValue();

				LocalDate nextWeek = null;
				switch (dayOfWeek) {
				case 1:
					nextWeek = localDate.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
					break;
				case 2:
					nextWeek = localDate.with(TemporalAdjusters.next(DayOfWeek.TUESDAY));
					break;
				case 3:
					nextWeek = localDate.with(TemporalAdjusters.next(DayOfWeek.WEDNESDAY));
					break;
				case 4:
					nextWeek = localDate.with(TemporalAdjusters.next(DayOfWeek.THURSDAY));
					break;
				case 5:
					nextWeek = localDate.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
					break;
				case 6:
					nextWeek = localDate.with(TemporalAdjusters.next(DayOfWeek.SATURDAY));
					break;
				case 7:
					nextWeek = localDate.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
					break;
				default:
					break;
				}
				return nextWeek;
			}

		} catch (Exception e) {
			log.error("UDateTime#nextWeek error {}", e);
		}
		return null;
	}

	public static LocalDate previousDay(final Date date, final DayOfWeek dayOfWeek) {
		try {

			if (date != null && ValidatorUtil.isNotEmpty(dayOfWeek)) {
				val localDate = localDate(date);
				if (localDate != null) {
					return localDate.with(TemporalAdjusters.previous(dayOfWeek));
				}
			}

		} catch (Exception e) {
			log.error("UDateTime#previousDay error {}", e);
		}
		return null;
	}

	public static LocalDate nextDay(final Date date, final DayOfWeek dayOfWeek) {
		try {

			if (date != null && ValidatorUtil.isNotEmpty(dayOfWeek)) {
				val localDate = localDate(date);
				if (localDate != null) {
					return localDate.with(TemporalAdjusters.next(dayOfWeek));
				}
			}

		} catch (Exception e) {
			log.error("UDateTime#nextDay error {}", e);
		}
		return null;
	}

	public static Integer compareDayOfWeek(final Date dateOne, final Date dateTwo) {
		try {

			if (ValidatorUtil.isNotEmpty(dateOne) && ValidatorUtil.isNotEmpty(dateTwo)) {
				val dowOne = getDayOfWeek(dateOne);
				val dowTwo = getDayOfWeek(dateTwo);
				return compareDayOfWeek(dowOne, dowTwo);
			}

		} catch (Exception e) {
			log.error("UDateTime#compareDayOfWeek error {}", e);
		}
		return null;
	}

	public static Integer compareDayOfWeek(final DayOfWeek dowOne, final DayOfWeek dowTwo) {
		try {

			return dowOne.compareTo(dowTwo);

		} catch (Exception e) {
			log.error("UDateTime#compareDayOfWeek error {}", e);
		}
		return null;
	}

	public static boolean isValidWeekDay(final DayOfWeek dow) {
		try {

			if (ValidatorUtil.isNotEmpty(dow)) {
				val currentDay = DayOfWeek.from(LocalDate.now());
				return currentDay.compareTo(dow) == 0;
			}

		} catch (Exception e) {
			log.error("UDateTime#isValidDay error {}", e);
		}
		return false;
	}

	// ######################################################################################
	// ##### Date (java.sql.Date), Time (java.sql.Time), Timestamp
	// (java.sql.Timestamp) #####
	// ######################################################################################
	public static String formatDate(final java.sql.Timestamp timestamp, Formatter formatter) {
		if (ValidatorUtil.isNotEmpty(timestamp) && ValidatorUtil.isNotEmpty(formatter)) {
			try {

				val sdf = new SimpleDateFormat(formatter.format);
				return sdf.format(timestamp);

			} catch (Exception e) {
				log.error("UDateTime#formatDate(java.sql.Timestamp) error {}", e);
			}
		}
		return ValueUtil.EMPTY;
	}

	public static java.sql.Date sqlDate(final Date date) {
		try {

			if (date != null) {
				return new java.sql.Date(date.getTime());
			}

		} catch (Exception e) {
			log.error("UDateTime#sqlDate(Date) error {}", e);
		}
		return null;
	}

	public static Date date(final java.sql.Date sqlDate) {
		try {

			if (ValidatorUtil.isNotEmpty(sqlDate)) {
				return new Date(sqlDate.getTime());
			}

		} catch (Exception e) {
			log.error("UDateTime#date(java.sql.Date) error {}", e);
		}
		return null;
	}

	public static Date date(final java.sql.Time sqlTime) {
		try {

			if (ValidatorUtil.isNotEmpty(sqlTime)) {
				return new Date(sqlTime.getTime());
			}

		} catch (Exception e) {
			log.error("UDateTime#date(java.sql.Time) error {}", e);
		}
		return null;
	}

	public static java.sql.Time sqlTime(final Date date) {
		try {

			if (date != null) {
				return new java.sql.Time(date.getTime());
			}

		} catch (Exception e) {
			log.error("UDateTime#sqlTime(Date) error {}", e);
		}
		return null;
	}

	public static java.sql.Timestamp timestamp(final Date javaDate) {
		try {

			if (javaDate != null) {
				return new java.sql.Timestamp(javaDate.getTime());
			}

		} catch (Exception e) {
			log.error("UDateTime#timestamp(Date) error {}", e);
		}
		return null;
	}

	public static java.sql.Timestamp timestamp(final LocalDate javaLd) {
		try {

			if (ValidatorUtil.isNotEmpty(javaLd)) {
				return java.sql.Timestamp.valueOf(javaLd.atStartOfDay());
			}

		} catch (Exception e) {
			log.error("UDateTime#timestamp(LocalDate) error {}", e);
		}
		return null;
	}

	public static java.sql.Timestamp timestamp(final LocalDateTime javaLdt) {
		try {

			if (ValidatorUtil.isNotEmpty(javaLdt)) {
				return java.sql.Timestamp.valueOf(javaLdt);
			}

		} catch (Exception e) {
			log.error("UDateTime#timestamp(LocalDateTime) error {}", e);
		}
		return null;
	}

	public static Date date(final java.sql.Timestamp timestamp) {
		try {

			if (ValidatorUtil.isNotEmpty(timestamp)) {
				return new Date(timestamp.getTime());
			}

		} catch (Exception e) {
			log.error("UDateTime#date(Timestamp) error {}", e);
		}
		return null;
	}

	public static LocalDate localDate(final java.sql.Timestamp timestamp) {
		if (ValidatorUtil.isNotEmpty(timestamp)) {
			try {

				return timestamp.toLocalDateTime().toLocalDate();

			} catch (Exception e) {
				log.error("UDateTime#localDate(Timestamp) error {}", e);
			}
		}
		return null;
	}

	public static LocalDateTime localDateTime(final java.sql.Timestamp timestamp) {
		if (ValidatorUtil.isNotEmpty(timestamp)) {
			try {

				return timestamp.toLocalDateTime();

			} catch (Exception e) {
				log.error("UDateTime#localDateTime(Timestamp) error {}", e);
			}
		}
		return null;
	}

	// ###################
	// ##### OVERLAP #####
	// ###################
	public static Boolean hasOverlap(Date sd1, Date ed1, Date sd2, Date ed2) {
		val sLdt = localDateTime(sd1);
		val eLdt = localDateTime(ed1);
		val osLdt = localDateTime(sd2);
		val oeLdt = localDateTime(ed2);

		return hasOverlap(sLdt, eLdt, osLdt, oeLdt);
	}

	public static Boolean hasOverlap(LocalDate sld1, LocalDate eld1, LocalDate sld2, LocalDate eld2) {
		val sLdt = localDateTime(sld1);
		val eLdt = localDateTime(eld1);
		val osLdt = localDateTime(sld2);
		val oeLdt = localDateTime(eld2);

		return hasOverlap(sLdt, eLdt, osLdt, oeLdt);
	}
	
	public static Boolean hasOverlap(LocalTime slt1, LocalTime elt1, LocalTime slt2, LocalTime elt2) {
		val sLdt = localDateTime(slt1);
		val eLdt = localDateTime(elt1);
		val osLdt = localDateTime(slt2);
		val oeLdt = localDateTime(elt2);

		return hasOverlap(sLdt, eLdt, osLdt, oeLdt);
	}

	public static Boolean hasOverlap(LocalDateTime sldt1, LocalDateTime eldt1, LocalDateTime sldt2, LocalDateTime eldt2) {
		return (DateTimeUtil.isAfterOrEqualLocalDateTime(sldt1, sldt2) && DateTimeUtil.isBeforeOrEqualLocalDateTime(sldt1, eldt2))
				|| (DateTimeUtil.isAfterOrEqualLocalDateTime(eldt2, eldt1) && DateTimeUtil.isBeforeOrEqualLocalDateTime(sldt2, eldt1));
	}

}
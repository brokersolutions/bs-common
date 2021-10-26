package bs.common.util;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

import lombok.val;
import lombok.extern.slf4j.Slf4j;
import bs.common.util.DateTimeUtil.Formatter;
import bs.common.util.MessageUtil.CustomMessage;

/**
 * Utility class to operate with age
 * 
 * @author Maikel Guerra Ferrer - mguerraferrer@gmail.com
 *
 */
@Slf4j
public class AgeUtil implements Serializable {
	private static final long serialVersionUID = -2064616199025565646L;

	private AgeUtil() {
		throw new IllegalStateException("Utility class");
	}

	private static final String AGE_DETAILED_MESSAGE = "age.text.detailed";
	private static final String AGE_TEXT_YEARS = "age.text.y";
	private static final String AGE_TEXT_MONTHS = "age.text.m";
	private static final String AGE_TEXT_DAYS = "age.text.d";

	private static final String LOG_ERROR = "UAge#age error {}";

	/**
	 * Returns age (String) from a birth date <br>
	 * <br>
	 * <b>Possible results</b> <br>
	 * - 1 year/N years <br>
	 * - 1 month/N months <br>
	 * - Newborn/1 day/N days
	 * 
	 * @param birthDate {@code LocalDate}
	 * @return the age (String) between birth date and today
	 */
	public static String age(LocalDate birthDate) {
		if (birthDate != null) {
			val period = period(birthDate);

			val years = ageYears(period);
			val months = ageMonths(period);
			val days = ageDays(period, years, months);

			if (ValidatorUtil.isNotEmpty(years)) {
				return years;
			} else if (ValidatorUtil.isNotEmpty(months)) {
				return months;
			} else if (ValidatorUtil.isNotEmpty(days)) {
				return days;
			}
		}
		return ValueUtil.DASH;
	}

	/**
	 * Returns age (String) from a birth date <br>
	 * <br>
	 * <b>Possible results</b> <br>
	 * - 1 year/N years <br>
	 * - 1 month/N months <br>
	 * - Newborn/1 day/N days
	 * 
	 * @param date String
	 * @return the age (String) between birth date and today
	 */
	public static Integer age(final String date) {
		try {

			if (ValidatorUtil.isNotEmpty(date)) {
				val birthDate = DateTimeUtil.parseDate(date, Formatter.DATE_SIMPLE_FORMAT);
				return age(birthDate);
			}

		} catch (Exception e) {
			log.error(LOG_ERROR, e.getMessage());
		}
		return null;
	}

	/**
	 * 
	 * Returns detailed age (String) from a birth date <br>
	 * <br>
	 * <b>Possible results</b> <br>
	 * - N year(s), N month(s) and N day(s)<br>
	 * - N year(s) and N month(s)<br>
	 * - N year(s) and N day(s)<br>
	 * - N month(s) and N day(s)<br>
	 * - N year(s)<br>
	 * - N month(s)<br>
	 * - N day(s)
	 * 
	 * @param birthDate {@code LocalDate}
	 * @return the detailed age (String) between birth date and today
	 */
	public static String ageDetailed(LocalDate birthDate) {
		if (birthDate != null) {
			val period = period(birthDate);

			val years = ageYears(period);
			val months = ageMonths(period);
			val days = ageDays(period, years, months);

			return ageDetailed(years, months, days);
		}
		return ValueUtil.DASH;
	}

	/**
	 * Returns the age (only years) from a birth date
	 * 
	 * @param birthDate {@code Date}
	 * @return the age (only years) between birth date and today
	 */
	public static Integer age(final Date birthDate) {
		try {

			if (birthDate != null) {
				return DateTimeUtil.periodYearsInt(birthDate, new Date());
			}

		} catch (Exception e) {
			log.error(LOG_ERROR, e.getMessage());
		}
		return null;
	}

	/**
	 * Returns a {@code CustomMessage} object
	 * @param date The date (String) to analyze
	 * @param detailed Determines whether the result will be a detailed age (see
	 *                 {@link AgeUtil#ageDetailed(LocalDate)} or not
	 * @return {@code CustomMessage} or null
	 * @see CustomMessage
	 */
	public static CustomMessage age(final String date, final boolean detailed) {
		try {

			if (ValidatorUtil.isNotEmpty(date)) {
				val bdate = DateTimeUtil.parseDate(date, Formatter.DATE_SIMPLE_FORMAT);
				val birthDate = DateTimeUtil.localDate(bdate);
				return age(birthDate, detailed);
			}

		} catch (Exception e) {
			log.error(LOG_ERROR, e.getMessage());
		}
		return null;
	}

	public static CustomMessage age(final Date date, final boolean detailed) {
		try {

			if (date != null) {
				val birthDate = DateTimeUtil.localDate(date);
				return age(birthDate, detailed);
			}

		} catch (Exception e) {
			log.error(LOG_ERROR, e.getMessage());
		}
		return null;
	}

	private static CustomMessage age(final LocalDate birthDate, final boolean detailed) {
		try {

			val currentDate = LocalDate.now();
			val period = DateTimeUtil.period(birthDate, currentDate);

			val years = periodValue(period, "years");
			val months = periodValue(period, "months");
			val days = periodValue(period, "days");

			if (detailed) {
				return ageDetailed(years, months, days);
			} else {
				if (ValidatorUtil.isNotEmpty(years)) {
					return CustomMessage.builder().key(AGE_TEXT_YEARS).params(new Object[] { years }).build();
				} else if (ValidatorUtil.isNotEmpty(months)) {
					return CustomMessage.builder().key(AGE_TEXT_MONTHS).params(new Object[] { months }).build();
				} else if (days != null) {
					return CustomMessage.builder().key(AGE_TEXT_DAYS).params(new Object[] { days }).build();
				}
			}

		} catch (Exception e) {
			log.error(LOG_ERROR, e.getMessage());
		}
		return null;
	}

	private static Period period(LocalDate birthDate) {
		return Period.between(birthDate, LocalDate.now());
	}

	private static String ageYears(Period period) {
		if (ValidatorUtil.isNotEmpty(period) && period.getYears() >= 1) {
			return MessageUtil.getMessage(AGE_TEXT_YEARS, new Object[] { period.getYears() });
		}
		return null;
	}

	private static String ageMonths(Period period) {
		if (ValidatorUtil.isNotEmpty(period) && period.getMonths() >= 1) {
			return MessageUtil.getMessage(AGE_TEXT_MONTHS, new Object[] { period.getYears() });
		}
		return null;
	}

	private static String ageDays(Period period, String years, String months) {
		if (ValidatorUtil.isNotEmpty(period)) {
			return MessageUtil.getMessage(AGE_TEXT_DAYS, new Object[] { period.getDays() });
		}
		return null;
	}

	private static CustomMessage ageDetailed(Integer years, Integer months, Integer days) {
		if (ValidatorUtil.isNotEmpty(years) && ValidatorUtil.isNotEmpty(months) && ValidatorUtil.isNotEmpty(days)) {
			return CustomMessage.builder().key("age.text.ymd").params(new Object[] { years, months, days }).build();
		}

		if (ValidatorUtil.isNotEmpty(years) && ValidatorUtil.isNotEmpty(months)) {
			return CustomMessage.builder().key("age.text.ym").params(new Object[] { years, months }).build();
		}

		if (ValidatorUtil.isNotEmpty(years) && ValidatorUtil.isNotEmpty(days)) {
			return CustomMessage.builder().key("age.text.yd").params(new Object[] { years, days }).build();
		}

		if (ValidatorUtil.isNotEmpty(months) && ValidatorUtil.isNotEmpty(days)) {
			return CustomMessage.builder().key("age.text.md").params(new Object[] { months, days }).build();
		}

		if (ValidatorUtil.isNotEmpty(years)) {
			return CustomMessage.builder().key(AGE_TEXT_YEARS).params(new Object[] { years }).build();
		}

		if (ValidatorUtil.isNotEmpty(months)) {
			return CustomMessage.builder().key(AGE_TEXT_MONTHS).params(new Object[] { months }).build();
		}

		if (ValidatorUtil.isNotEmpty(days)) {
			return CustomMessage.builder().key(AGE_TEXT_DAYS).params(new Object[] { days }).build();
		}
		return null;
	}

	private static String ageDetailed(String years, String months, String days) {
		if (ValidatorUtil.isNotEmpty(years) && ValidatorUtil.isNotEmpty(months) && ValidatorUtil.isNotEmpty(days)) {
			return MessageUtil.getMessage("age.text.ymd.detailed", new Object[] { years, months, days });
		}

		if (ValidatorUtil.isNotEmpty(years) && ValidatorUtil.isNotEmpty(months)) {
			return MessageUtil.getMessage(AGE_DETAILED_MESSAGE, new Object[] { years, months });
		}

		if (ValidatorUtil.isNotEmpty(years) && ValidatorUtil.isNotEmpty(days)) {
			return MessageUtil.getMessage(AGE_DETAILED_MESSAGE, new Object[] { years, days });
		}

		if (ValidatorUtil.isNotEmpty(months) && ValidatorUtil.isNotEmpty(days)) {
			return MessageUtil.getMessage(AGE_DETAILED_MESSAGE, new Object[] { months, days });
		}

		if (ValidatorUtil.isNotEmpty(years)) {
			return years;
		}

		if (ValidatorUtil.isNotEmpty(months)) {
			return months;
		}

		if (ValidatorUtil.isNotEmpty(days)) {
			return days;
		}

		return ValueUtil.DASH;
	}

	private static Integer periodValue(Period period, String value) {
		switch (value) {
		case "years" -> {
			if (period.getYears() >= 1) {
				return period.getYears();
			}
			return null;
		}
		case "months" -> {
			if (period.getMonths() >= 1) {
				return period.getMonths();
			}
			return null;
		}
		case "days" -> {
			if (period.getDays() == 0 && period.getYears() <= 1 && period.getMonths() <= 1) {
				return 0;
			} else if (period.getDays() >= 1) {
				return period.getDays();
			}
		}
		default -> {
			return 0;
		}
		}
		return null;
	}
}
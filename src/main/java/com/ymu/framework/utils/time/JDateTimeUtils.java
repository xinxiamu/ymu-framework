package com.ymu.framework.utils.time;

import java.util.Date;

import jodd.datetime.JDateTime;

/**
 * Jodd 时间处理库的使用工具。
 * 
 * @author mutou
 * @version jdk 1.7
 */
public class JDateTimeUtils {

	private static final String INVALID_DATE_MSG = "无效时间";

	private JDateTimeUtils() {
	};

	/**
	 * 按照特定格式获取当前系统时间。
	 * 
	 * @param format
	 *            时间格式。null或着空则返回默认。
	 * @return 默认返回时间格式如：2015-06-03 11:40:46.303
	 */
	public static String getCurrentSystemDateTime(String format) {
		JDateTime jdt = new JDateTime();
		String jdtStr = (format == null || format.isEmpty()) ? jdt.toString()
				: jdt.toString(format);
		return jdtStr;
	}

	/**
	 * 时间转成毫秒。
	 * 
	 * @param date
	 * @return
	 */
	public static long dateTimeInMillis(Object date) {
		JDateTime jDateTime = conversionToJDateTime(date);
		return jDateTime.getTimeInMillis();
	}

	/**
	 * 日期转换成字符串。
	 * 
	 * @param date
	 *            long,或着Date类型。
	 * @return
	 */
	public static String dateToStr(Object date, String format) {
		if (date == null) {
			throw new NullPointerException();
		}
		JDateTime jdt = conversionToJDateTime(date);
		if (format == null || format.isEmpty()) {
			return jdt.toString();
		}
		return jdt.toString(format);
	}

	/**
	 * 时间对象转换成字符串。
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Date strToDate(String dateStr) {
		if (dateStr == null || dateStr.isEmpty()) {
			throw new NullPointerException();
		}
		if (!isValid(dateStr)) {
			return null;
		}
		JDateTime jdt = new JDateTime(dateStr);
		Date date = jdt.convertToDate();
		return date;
	}

	/**
	 * 转换字符串日期格式。
	 * 
	 * @param dateStr
	 *            字符串日期。
	 * @param format
	 *            新的格式。
	 * @return 新格式的字符串日期。
	 */
	public static String dateStrFormat(String dateStr, String format) {
		if (dateStr == null || dateStr.isEmpty()) {
			throw new NullPointerException();
		}
		if (!isValid(dateStr, format)) {
			return INVALID_DATE_MSG;
		}

		JDateTime jdt = new JDateTime(dateStr);
		String newDateStr = null;
		if (format == null || format.isEmpty()) {
			newDateStr = jdt.toString();
		} else {
			newDateStr = jdt.toString(format);
		}
		return newDateStr;
	}

	/**
	 * 获取时间的时或分或秒……
	 * 
	 * @param dateTimeStr
	 * @param flg
	 *            类型标志。来自{@link JDateTimeType}
	 * @return 返回-1表示时间无效
	 */
	public static int getDateTimeType(String dateTimeStr, String flg) {
		if (dateTimeStr == null || flg == null) {
			throw new NullPointerException();
		}
		if (!isValid(dateTimeStr)) {
			return -1;
		}
		JDateTime jdt = new JDateTime(dateTimeStr);
		int result = 0;
		if (flg == JDateTimeType.YEAR.getValue()) {
			result = jdt.getYear();
		} else if (flg == JDateTimeType.MONTH.getValue()) {
			result = jdt.getMonth();
		} else if (flg == JDateTimeType.DAY.getValue()) {
			result = jdt.getDay();
		} else if (flg == JDateTimeType.HOUR.getValue()) {
			result = jdt.getHour();
		} else if (flg == JDateTimeType.MINUTE.getValue()) {
			result = jdt.getMinute();
		} else if (flg == JDateTimeType.SECONDS.getValue()) {
			result = jdt.getSecond();
		} else if (flg == JDateTimeType.MILLISECONDS.getValue()) {
			result = jdt.getMillisecond();
		}
		return result;
	}

	public static int getDateTimeType(Date date, String flg) {
		if (date == null || flg == null) {
			throw new NullPointerException();
		}
		if (!isValid(date)) {
			return -1;
		}
		JDateTime jdt = new JDateTime(date);
		int result = 0;
		if (flg == JDateTimeType.YEAR.getValue()) {
			result = jdt.getYear();
		} else if (flg == JDateTimeType.MONTH.getValue()) {
			result = jdt.getMonth();
		} else if (flg == JDateTimeType.DAY.getValue()) {
			result = jdt.getDay();
		} else if (flg == JDateTimeType.HOUR.getValue()) {
			result = jdt.getHour();
		} else if (flg == JDateTimeType.MINUTE.getValue()) {
			result = jdt.getMinute();
		} else if (flg == JDateTimeType.SECONDS.getValue()) {
			result = jdt.getSecond();
		} else if (flg == JDateTimeType.MILLISECONDS.getValue()) {
			result = jdt.getMillisecond();
		}
		return result;
	}

	/**
	 * 返回特定时间处于第几个星期。
	 * 
	 * @param date
	 * @return 返回-1表示时间无效
	 */
	public static int getWeekOfMonth(Date date) {
		if (date == null) {
			throw new NullPointerException();
		}
		if (!isValid(date)) {
			return -1;
		}
		JDateTime jdt = new JDateTime(date);
		int weekOfMonth = jdt.getWeekOfMonth();
		return weekOfMonth;
	}

	public static int getWeekOfMonth(String dateStr) {
		if (dateStr == null) {
			throw new NullPointerException();
		}
		if (!isValid(dateStr)) {
			return -1;
		}
		JDateTime jdt = new JDateTime(dateStr);
		int weekOfMonth = jdt.getWeekOfMonth();
		return weekOfMonth;
	}

	/**
	 * 获取特定时间是星期几。
	 * 
	 * @param dateStr
	 *            日期字符串。
	 * @return
	 */
	public static String getDayOfWeek(String dateStr) {
		if (dateStr == null) {
			throw new NullPointerException();
		}
		if (!isValid(dateStr)) {
			return INVALID_DATE_MSG;
		}
		JDateTime jdt = new JDateTime(dateStr);
		int dayOfWeek = jdt.getDayOfWeek();
		return dayOfWeekToZh(dayOfWeek);
	}

	public static String getDayOfWeek(Date date) {
		if (date == null) {
			throw new NullPointerException();
		}
		if (!isValid(date)) {
			return INVALID_DATE_MSG;
		}
		JDateTime jdt = new JDateTime(date);
		int dayOfWeek = jdt.getDayOfWeek();
		return dayOfWeekToZh(dayOfWeek);
	}

	/**
	 * 转换星期几。
	 * 
	 * @param dayOfWeek
	 * @return
	 */
	private static String dayOfWeekToZh(int dayOfWeek) {
		String dayOfWeekStr = null;
		switch (dayOfWeek) {
		case JDateTime.MONDAY:
			dayOfWeekStr = Week.MONDAY.getChineseName();
			break;
		case JDateTime.TUESDAY:
			dayOfWeekStr = Week.TUESDAY.getChineseName();
			break;
		case JDateTime.WEDNESDAY:
			dayOfWeekStr = Week.WEDNESDAY.getChineseName();
			break;
		case JDateTime.THURSDAY:
			dayOfWeekStr = Week.THURSDAY.getChineseName();
			break;
		case JDateTime.FRIDAY:
			dayOfWeekStr = Week.FRIDAY.getChineseName();
			break;
		case JDateTime.SATURDAY:
			dayOfWeekStr = Week.SATURDAY.getChineseName();
			break;
		case JDateTime.SUNDAY:
			dayOfWeekStr = Week.SUNDAY.getChineseName();
			break;

		default:
			break;
		}
		return dayOfWeekStr;
	}

	/**
	 * 一年中的第几天。
	 * 
	 * @param date
	 * @return -1无效时间。
	 */
	public static int getDayOfYear(Date date) {
		if (date == null) {
			throw new NullPointerException();
		}
		if (!isValid(date)) {
			return -1;
		}
		JDateTime jdt = new JDateTime(date);
		return jdt.getDayOfYear();
	}

	public static int getDayOfYear(String dateStr) {
		if (dateStr == null) {
			throw new NullPointerException();
		}
		if (!isValid(dateStr)) {
			return -1;
		}
		JDateTime jdt = new JDateTime(dateStr);
		return jdt.getDayOfYear();
	}

	/**
	 * 判断给定时间是否是闰年。
	 * 
	 * @param date
	 *            只能是Date、String、Long类型
	 * @return 是返回true,否则返回false
	 */
	public static boolean isLeapYear(Object date) {
		JDateTime jdt = null;
		if (date instanceof Date) {
			jdt = new JDateTime((Date) date);
		} else if (date instanceof String) {
			jdt = new JDateTime((String) date);
		} else if (date instanceof Long) {
			jdt = new JDateTime((Long) date);
		}
		if (!isValid(jdt.toString())) {
			return false;
		}
		return jdt.isLeapYear();
	}

	/**
	 * 判断时间表达是否有效。系统格式：2015-06-03
	 * 
	 * @param date
	 *            Date、String、long类型时间格式。
	 * @return 有效返回true，否则false
	 */
	public static boolean isValid(Object date) {
		JDateTime jdt = conversionToJDateTime(date);
		if (jdt == null) {
			return false;
		}
		return jdt.isValid(jdt.toString());
	}

	/**
	 * 根据模板判断时间表达的有效性。
	 * 
	 * @param date
	 * @param template
	 *            时间表示模式。
	 * @return
	 */
	public static boolean isValid(Object date, String template) {
		JDateTime jdt = conversionToJDateTime(date);
		if (jdt == null) {
			return false;
		}
		return jdt.isValid(jdt.toString(), template);
	}

	/*------------------------- 时间加减偏移  ---------------------------*/

	/**
	 * 年月日偏移。时间往后用正整数，往前用负整数。
	 * 
	 * @param object
	 * @param yearOffset
	 * @param monthOffset
	 * @param dayOffset
	 * @return 新的时间
	 */
	public static String addDate(Object object, int yearOffset,
			int monthOffset, int dayOffset) {
		if (!isValid(object)) {
			return INVALID_DATE_MSG;
		}
		JDateTime jdt = conversionToJDateTime(object);
		jdt.add(yearOffset, monthOffset, dayOffset);
		return jdt.toString();
	}

	/**
	 * 时分秒偏移。时间往后用正整数，往前用负整数。
	 * 
	 * @param object
	 * @param hourOffset
	 * @param minuteOffset
	 * @param secondsOffset
	 * @return
	 */
	public static String addTime(Object object, int hourOffset,
			int minuteOffset, int secondsOffset) {
		if (!isValid(object)) {
			return INVALID_DATE_MSG;
		}
		JDateTime jdt = conversionToJDateTime(object);
		jdt.addTime(hourOffset, minuteOffset, secondsOffset);
		return jdt.toString();
	}

	/*------------------------- 时间比较  ---------------------------*/

	/**
	 * 比较两个时间。
	 * 
	 * @param date1
	 * @param date2
	 * @param ignoreTime
	 *            true忽略时分秒部分，否则不忽略
	 * @return 1 date1比date2大，0相等，-1 date1比date2小
	 */
	public static int compareTo(Object date1, Object date2, boolean ignoreTime) {
		JDateTime jDateTime1 = conversionToJDateTime(date1);
		JDateTime jDateTime2 = conversionToJDateTime(date2);
		if (ignoreTime) {
			return jDateTime1.compareDateTo(jDateTime2);
		}
		return jDateTime1.compareTo(jDateTime2);
	}

	/**
	 * 时间1是否在时间2之后。
	 * 
	 * @param date1
	 * @param date2
	 * @return 是返回true，否则返回false。
	 */
	public static boolean isDate1AfterDate2(Object date1, Object date2) {
		JDateTime jdt1 = conversionToJDateTime(date1);
		JDateTime jdt2 = conversionToJDateTime(date2);
		return jdt1.isAfter(jdt2);
	}

	/**
	 * 时间1是否在时间2之后。忽略time部分。
	 * 
	 * @param date1
	 * @param date2
	 * @return 是返回true，否则返回false。
	 */
	public static boolean isDate1AfterDate2IgnoredTime(Object date1,
			Object date2) {
		JDateTime jdt1 = conversionToJDateTime(date1);
		JDateTime jdt2 = conversionToJDateTime(date2);
		return jdt1.isAfterDate(jdt2);
	}

	/**
	 * 时间1是否在时间2之前。
	 * 
	 * @param date1
	 * @param date2
	 * @return 是返回ture，否则返回false。
	 */
	public static boolean isDate1BeforeDate2(Object date1, Object date2) {
		JDateTime jdt1 = conversionToJDateTime(date1);
		JDateTime jdt2 = conversionToJDateTime(date2);
		return jdt1.isBefore(jdt2);
	}

	/**
	 * 时间1是否在时间2之前。忽略time部分。
	 * 
	 * @param date1
	 * @param date2
	 * @return 是返回ture，否则返回false。
	 */
	public static boolean isDate1BeforeDate2IgnoredTime(Object date1,
			Object date2) {
		JDateTime jdt1 = conversionToJDateTime(date1);
		JDateTime jdt2 = conversionToJDateTime(date2);
		return jdt1.isBeforeDate(jdt2);
	}

	/**
	 * 两个时间是否是同一天。
	 * 
	 * @param date1
	 * @param date2
	 * @return 相同返回ture，否则返回false。
	 */
	public static boolean isTheSameDay(Object date1, Object date2) {
		JDateTime jdt1 = conversionToJDateTime(date1);
		JDateTime jdt2 = conversionToJDateTime(date2);
		int flg = jdt1.compareDateTo(jdt2);
		if (flg == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 两个时间是否是同一时刻。
	 * 
	 * @param date1
	 * @param date2
	 * @return 相同返回ture，否则返回false。
	 */
	public static boolean isTheSameTime(Object date1, Object date2) {
		JDateTime jdt1 = conversionToJDateTime(date1);
		JDateTime jdt2 = conversionToJDateTime(date2);
		int flg = jdt1.compareTo(jdt2);
		if (flg == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 计算两个日期相差的天数。返回绝对值。
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int absDate1ToDate2(Object date1, Object date2) {
		JDateTime jdt1 = conversionToJDateTime(date1);
		JDateTime jdt2 = conversionToJDateTime(date2);
		int num = jdt1.daysBetween(jdt2);
		return num;
	}

	/**
	 * 友好的时间显示
	 * 
	 * @param date
	 * @return
	 */
	public static String showDateTimeFriendly(Object date) {
		String showStr = null;
		long showTime = dateTimeInMillis(date);
		long nowTime = dateTimeInMillis(new Date());
		long diff = 0L;
		if (nowTime > showTime) {
			diff = (nowTime - showTime) / 1000; // 转为秒
			int diff_m = (int) (diff / 60); // 分
			if (diff_m >= 0 && diff_m <= 10) {
				showStr = "刚刚";
			} else if (diff_m < 60) {
				showStr = diff_m + "分钟前";
			} else {
				int diff_h = (int) (diff / (60 * 60));
				if (diff_h >= 0 && diff_h < 24) {
					showStr = diff_h + "小时前";
				} else {
					int diff_day = (int) (diff / (60 * 60 * 24));
					if (diff_day >= 0 && diff_day < 7) {
						showStr = diff_day + "天前";
					} else {
						showStr = dateToStr(date,
								JDateTimeStyle.YYYY_MM_DD_HH_MM_CN.getValue());
					}
				}
			}
		}
		return showStr;

	}

	/*--------------------- 辅助类 -------------------------*/

	/**
	 * 转换时间为JDateTime对象。
	 * 
	 * @param date
	 *            只能是Date、String、long类型。
	 * @return 返回时间的JDateTime对象。
	 */
	private static JDateTime conversionToJDateTime(Object date) {
		if (date == null) {
			throw new NullPointerException();
		}
		JDateTime jdt = null;
		if (date instanceof String) {
			jdt = new JDateTime((String) date);
		} else if (date instanceof Date) {
			jdt = new JDateTime((Date) date);
		} else if (date instanceof Long) {
			jdt = new JDateTime((Long) date);
		} else {
			throw new RuntimeException("请输入正确的时间类型");
		}
		if (jdt != null && !jdt.isValid(jdt.toString())) {
			throw new RuntimeException(INVALID_DATE_MSG);
		}
		return jdt;
	}

/*	public static void main(String[] args) {

	}*/
}

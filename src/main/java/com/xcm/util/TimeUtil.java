package com.xcm.util;

import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
	public static Date addTimeByDay(int days, Date time) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		calendar.add(calendar.DATE, days);// 把日期往后增加N天.整数往后推,负数往前移动
		return calendar.getTime(); // 这个时间就是日期往后推N天的结果
	}

	public static Date addTimeByMonth(int month, Date time) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		calendar.add(calendar.MONTH, month);// 把日期往后增加N月.整数往后推,负数往前移动
		return calendar.getTime(); // 这个时间就是日期往后推N月的结果
	}

	public static Date addTimeByHours(int hours, Date time) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		calendar.add(calendar.HOUR, hours);// 把日期往后增加N小时.整数往后推,负数往前移动
		return calendar.getTime(); // 这个时间就是日期往后推N小时的结果
	}

	public static Date addTimeByMinute(int minutes, Date time) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		calendar.add(calendar.MINUTE, minutes);// 把日期往后增加N分钟.整数往后推,负数往前移动
		return calendar.getTime(); // 这个时间就是日期往后推N分钟的结果
	}

	public static Date addTimeByYear(int years, Date time) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		calendar.add(calendar.YEAR, years);// 把日期往后增加N年.整数往后推,负数往前移动
		return calendar.getTime(); // 这个时间就是日期往后推N年的结果
	}

	public static Date addTimeBySecond(int second, Date time) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		calendar.add(calendar.SECOND, second);// 把日期往后增加N秒.整数往后推,负数往前移动
		return calendar.getTime(); // 这个时间就是日期往后推N秒的结果
	}

	public static Date getCalendarTime(int year, int mouth, int day) {
		return getCalendarTime(year, mouth, day, 0, 0, 0);
	}

	public static Date getCalendarTime(int year, int mouth, int day, int hour,
			int minute, int second) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		// calendar月数0-11
		calendar.set(Calendar.MONTH, mouth - 1);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, second);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();

	}

	public static Date getCalendarTimeToday(int hour, int minute, int second) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, second);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();

	}

	/**
	 * 检查是否是星期六
	 * 
	 * @param time
	 * @return
	 */
	public static boolean checkTodayIsSaturday(long time) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(time));
		int day = calendar.get(Calendar.DAY_OF_WEEK);
		if (day == 7) {
			return true;
		}
		return false;

	}

	/**
	 * 检查日期是否是昨天
	 * 
	 * @param time
	 * @return
	 */
	public static boolean checkDateIsYesterDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int day = calendar.get(Calendar.DAY_OF_YEAR);
		Calendar today = Calendar.getInstance();
		int tDay = today.get(Calendar.DAY_OF_YEAR);
		return day + 1 == tDay;
	}

}

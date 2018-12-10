package com.xcm.util;



import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CommonUtil {


	public static String getDateKey() {
		return getDateKey(0, 0);

	}

	public static String getRankListDateKey() {
		return getDateKey(0, 30);
	}

	public static String getDateKey(int hour, int minute) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		String dateKey = "";
		Date time = Calendar.getInstance().getTime();
		Date compareTime = TimeUtil.getCalendarTimeToday(hour, minute, 0);
		// 查看当前时候是否已经过了当天某个时间，如果过了则算做今天，否则算作昨天
		if (time.compareTo(compareTime) > 0) {
			dateKey = simpleDateFormat.format(time.getTime());
		} else {
			dateKey = simpleDateFormat.format(TimeUtil.addTimeByDay(-1, time));
		}
		return dateKey;

	}

	public static String getDateStrByTime(long millisecond){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date time = new Date(millisecond);
		String dateKey = simpleDateFormat.format(time.getTime());
		return dateKey;
	}
	public static String getDateKeyByTime(long millisecond) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		Date time = new Date(millisecond);
		String dateKey = simpleDateFormat.format(time.getTime());
		return dateKey;
	}

	public static String getTodayHuaKuiRankListDateKey() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		Date time = Calendar.getInstance().getTime();
		String dateKey = simpleDateFormat.format(time.getTime());
		return dateKey;
	}

	public static String getYesterDayHuaKuiRankListDateKey() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		String dateKey = simpleDateFormat.format(calendar.getTime());
		return dateKey;
	}

	public static String getStarWarsDateKey() {
		return "20160522";
	}

	public static String getWeekDateKey() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		String dateKey = "";
		Date now = new Date();
		// 获取礼拜一00:30:00
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_WEEK, 2);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 30);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		// 查看当前时候是否已经过了最近的礼拜的星期日,过了则说明是下个礼拜,否则是上个礼拜
		Date compareTime = calendar.getTime();
		if (now.compareTo(compareTime) > 0) {
			dateKey = simpleDateFormat.format(compareTime);
		} else {
			dateKey = simpleDateFormat
					.format(TimeUtil.addTimeByDay(-7, compareTime));
		}
		return dateKey;

	}

	public static long getHuaKuiExpireTime() {
		return getExpireTimeByHour(49);
	}

	public static long getTodayExpireTime() {
		return getExpireTimeByHour(25);
	}

	public static long getExpireTimeByHour(int hour) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.HOUR, hour);
		long maxTime = calendar.getTime().getTime();
		long now = System.currentTimeMillis();
		return maxTime - now;
	}

	public static String getChatGroupWeekDateKey() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		// 获取礼拜一00:00:00
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_WEEK, 2);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Date time = calendar.getTime();
		String dateKey = simpleDateFormat.format(time);
		return dateKey;

	}



	public static String getExpRankListWeekDateKey() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		String dateKey = "";
		Date now = new Date();
		// 获取礼拜一05:00:00
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_WEEK, 2);
		calendar.set(Calendar.HOUR_OF_DAY, 5);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		// 查看当前时候是否已经过了最近的礼拜的星期日,过了则说明是下个礼拜,否则是上个礼拜
		Date compareTime = calendar.getTime();
		if (now.compareTo(compareTime) > 0) {
			dateKey = simpleDateFormat.format(compareTime);
		} else {
			dateKey = simpleDateFormat
					.format(TimeUtil.addTimeByDay(-7, compareTime));
		}
		return dateKey;
	}

	public static boolean isJobParamNormal(int arg){
		if (arg >= 0 && arg <= 4){
			return true;
		}
		return false;
	}
}

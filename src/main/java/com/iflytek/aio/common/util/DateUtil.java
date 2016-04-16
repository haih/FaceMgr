/**	
 * Copyright 2014 IFlyTek. All rights reserved.<br>
 */
package com.iflytek.aio.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * @desc: 日期处理的操作类
 * @author: cheney
 * @createTime: 2014-11-3
 * @version: 1.0
 */
public class DateUtil {
	private static final int[] dayArray = new int[] { 31, 28, 31, 30, 31, 30,
			31, 31, 30, 31, 30, 31 };
	private static SimpleDateFormat sdf = new SimpleDateFormat();

	/** 精确到秒的日期格式 */
	public static final String DATE_PATTERN_SECOND = "yyyy-MM-dd HH:mm:ss";

	/** 精确到日的日期格式 */
	public static final String DATE_PATTERN_DAY = "yyyy-MM-dd";

	/** 精确到秒的日期格式 */
	public static final String COUNT_DATE_PATTERN_SECOND = "MM/dd/yyyyy HH:mm:ss";

	public static final String DATE_PATTERN_YYYY_MM = "yyyy-MM";

	public static final String DATE_PATTERN_MILL_SECOND = "yyyyMMddHHmmssSSS";
	//
	public static final String YYYYMMDD = "yyyyMMdd";

	/**
	 * @param date
	 * @return String
	 */
	public static synchronized String getDateSecondFormat(java.util.Date date) {
		return getDateFormat(date, DATE_PATTERN_SECOND);
	}

	/**
	 * @param date
	 * @return String
	 */
	public static synchronized String getCountFormat(java.util.Date date) {
		return getDateFormat(date, COUNT_DATE_PATTERN_SECOND);
	}

	/**
	 * 将秒转换为 xx:xx:xx 格式
	 * 
	 * @param totalSecond
	 *            总秒数
	 * @return xx:xx:xx 格式的时间
	 * @author 张磊
	 */
	public static String formatSecond(long totalSecond) {
		long mTemp, sTemp;
		long h = (long) Math.floor(totalSecond / (60 * 60));

		mTemp = totalSecond - h * 60 * 60;
		long m = (long) Math.floor(mTemp / 60);

		sTemp = mTemp - m * 60;
		long s = sTemp;

		String hour = String.format("%02d", h);
		String minute = String.format("%02d", m);
		String second = String.format("%02d", s);

		return hour + ":" + minute + ":" + second;
	}

	/**
	 * @param date
	 * @param pattern
	 * @return String
	 */
	public static synchronized String getDateFormat(java.util.Date date,
			String pattern) {
		synchronized (sdf) {
			String str = null;
			sdf.applyPattern(pattern);
			str = sdf.format(date);
			return str;
		}
	}

	/**
	 * 根据日期获取年龄
	 * 
	 * @param date
	 * @return
	 */
	public static int getYearOfBirthday(Date date) {
		Calendar c = GregorianCalendar.getInstance();
		int todayYear = c.get(Calendar.YEAR);
		c.setTime(date);
		int birhYear = c.get(Calendar.YEAR);

		return todayYear - birhYear;
	}

	public static String getLastDayOfMonth() {
		// 本月的最后一天
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.DATE, 1);
		calendar.roll(Calendar.DATE, -1);
		SimpleDateFormat simpleFormate = new SimpleDateFormat("yyyy-MM-dd");
		String day = simpleFormate.format(calendar.getTime());
		return day;
	}

	public static String getFirstDayOfMonth() {
		// 本月的第一天
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.DATE, 1);
		SimpleDateFormat simpleFormate = new SimpleDateFormat("yyyy-MM-dd");
		String lastDay = simpleFormate.format(calendar.getTime());
		return lastDay;
	}

	public static String getLastDayOfMonth(int month) {
		// 获取指定月的最后一天
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DATE, 1);
		calendar.roll(Calendar.DATE, -1);
		SimpleDateFormat simpleFormate = new SimpleDateFormat("yyyy-MM-dd");
		String day = simpleFormate.format(calendar.getTime());
		return day;
	}

	public static String getLastDayOfMonthStr(int year, int month) {
		// 获取指定月的最后一天
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DATE, 1);
		calendar.roll(Calendar.DATE, -1);
		SimpleDateFormat simpleFormate = new SimpleDateFormat("yyyy-MM-dd");
		String day = simpleFormate.format(calendar.getTime());
		return day;
	}

	public static String getFirstDayOfMonth(int month) {
		// 获取指定月第一天
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DATE, 1);
		SimpleDateFormat simpleFormate = new SimpleDateFormat(" yyyy-MM-dd ");
		String lastDay = simpleFormate.format(calendar.getTime());
		return lastDay;
	}

	public static String getFirstDayOfMonth(int year, int month) {
		// 获取指定月第一天
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DATE, 1);
		SimpleDateFormat simpleFormate = new SimpleDateFormat(" yyyy-MM-dd ");
		String lastDay = simpleFormate.format(calendar.getTime());
		return lastDay;
	}

	public static String getCurrentDay() {
		// 当天
		SimpleDateFormat simpleFormate = new SimpleDateFormat(" yyyy-MM-dd ");
		String curday = simpleFormate.format(new Date());
		return curday;
	}

	public static String getLastDayOfLastMonth() {
		// 上月的最后一天
		Calendar calendar = new GregorianCalendar();
		calendar.add(Calendar.MONTH, -1); // 得到前一个月
		calendar.set(Calendar.DATE, 1);
		calendar.roll(Calendar.DATE, -1);
		SimpleDateFormat simpleFormate = new SimpleDateFormat(" yyyy-MM-dd ");
		String day = simpleFormate.format(calendar.getTime());
		return day;
	}

	public static String getFirstDayOfLastMonth() {
		// 上月的第一天
		Calendar calendar = new GregorianCalendar();
		calendar.add(Calendar.MONTH, -1); // 得到前一个月
		calendar.set(Calendar.DATE, 1);
		SimpleDateFormat simpleFormate = new SimpleDateFormat(" yyyy-MM-dd ");
		String lastDay = simpleFormate.format(calendar.getTime());
		return lastDay;
	}

	/**
	 * 获取指定日期的月份
	 * 
	 * @param date
	 * @return int
	 */
	public static int getMonth4Int(String date) {
		int month = 0;
		// date = "201110"只解析yyyyMM模式
		SimpleDateFormat simpleFormate = new SimpleDateFormat("yyyyMM");
		try {
			Date d = simpleFormate.parse(date);
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(d);
			month = calendar.get(Calendar.MONTH) + 1;
		} catch (ParseException e) {
		}

		return month;
	}

	public static synchronized int getLastDayOfMonth(int year, int month) {
		if (month < 1 || month > 12) {
			return -1;
		}
		int retn = 0;
		if (month == 2) {
			if (isLeapYear(year)) {
				retn = 29;
			} else {
				retn = dayArray[month - 1];
			}
		} else {
			retn = dayArray[month - 1];
		}
		return retn;
	}

	public static synchronized boolean isLeapYear() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		return isLeapYear(year);
	}

	/**
	 * 详细设计： 1.被400整除是闰年，否则： 2.不能被4整除则不是闰年 3.能被4整除同时不能被100整除则是闰年
	 * 3.能被4整除同时能被100整除则不是闰年
	 */
	public static synchronized boolean isLeapYear(int year) {
		if ((year % 400) == 0) {
			return true;
		} else if ((year % 4) == 0) {
			return (year % 100) == 0;
		} else {
			return false;
		}
	}

	/**
	 * @param date
	 * @return String
	 */
	public static synchronized String getDateDayFormat(java.util.Date date) {
		String pattern = "yyyy/MM/dd";
		return getDateFormat(date, pattern);
	}

	public static String fotmatDate4(Date myDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String strDate = formatter.format(myDate);
		return strDate;
	}

	public static synchronized Date getDateFromPattern(String date,
			String pattern) throws ParseException {
		synchronized (sdf) {
			Date d = null;
			sdf.applyPattern(pattern);
			d = sdf.parse(date);
			return d;
		}
	}

	public static synchronized String fotmatString(String str) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
		Date d = null;
		String s = null;
		if (str != null) {
			try {
				d = formatter.parse(str);
				s = formatter2.format(d);
			} catch (ParseException e) {

			}
		}
		return s;
	}

	public static synchronized String fotmatString(String str,
			String fromPattern, String toPattern) {
		SimpleDateFormat formatter = new SimpleDateFormat(fromPattern);
		SimpleDateFormat formatter2 = new SimpleDateFormat(toPattern);
		Date d = null;
		String s = null;
		if (str != null) {
			try {
				d = formatter.parse(str);
				s = formatter2.format(d);
			} catch (ParseException e) {

			}
		}
		return s;
	}

	/**
	 * 取得当前年月YYYYMM
	 */
	public static String getCurrYealAndMon() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		String mon = month < 10 ? "0" + month : month + "";
		return year + mon;
	}

	/**
	 * gc.add(1,1)年份加1
	 */
	public static String getDateCal(int d, int beforeMonth, String date) {
		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM");
		try {
			gc.setTime(getDateFromPattern(date, DATE_PATTERN_DAY));
		} catch (Exception e) {
		}
		gc.add(d, beforeMonth);
		gc.set(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH), gc
				.get(Calendar.DATE));
		return sf.format(gc.getTime());
	}

	/**
	 * 获取两个日期之间相差的月份
	 * 
	 * @param start
	 * @param end
	 */
	public static int getMonth(String start, String end) {
		try {
			Date startDate = getDateFromPattern(start, DATE_PATTERN_DAY);
			Date endDate = getDateFromPattern(end, DATE_PATTERN_DAY);

			if (startDate.after(endDate)) {
				Date t = startDate;
				startDate = endDate;
				endDate = t;
			}
			Calendar startCalendar = Calendar.getInstance();
			startCalendar.setTime(startDate);
			Calendar endCalendar = Calendar.getInstance();
			endCalendar.setTime(endDate);
			Calendar temp = Calendar.getInstance();
			temp.setTime(endDate);
			temp.add(Calendar.DATE, 1);

			int year = endCalendar.get(Calendar.YEAR)
					- startCalendar.get(Calendar.YEAR);
			int month = endCalendar.get(Calendar.MONTH)
					- startCalendar.get(Calendar.MONTH);

			if ((startCalendar.get(Calendar.DATE) == 1)
					&& (temp.get(Calendar.DATE) == 1)) {
				return year * 12 + month + 1;
			} else if ((startCalendar.get(Calendar.DATE) != 1)
					&& (temp.get(Calendar.DATE) == 1)) {
				return year * 12 + month;
			} else if ((startCalendar.get(Calendar.DATE) == 1)
					&& (temp.get(Calendar.DATE) != 1)) {
				return year * 12 + month;
			} else {
				return (year * 12 + month - 1) < 0 ? 0 : (year * 12 + month);
			}
		} catch (Exception e) {

		}
		return 0;
	}

	/**
	 * 获取当前时间戳(精确到秒)
	 */
	public static String getTimeStampForSec() {
		synchronized (sdf) {
			String str = null;
			sdf.applyPattern("yyyyMMddHHmmss");
			str = sdf.format(new Date());
			return str;
		}
	}

	/**
	 * 获取当前时间戳（精确到毫秒）
	 */
	public static String getTimeStampForMil() {
		synchronized (sdf) {
			String str = null;
			sdf.applyPattern("yyyyMMddHHmmssSSS");
			str = sdf.format(new Date());
			return str;
		}
	}

	/**
	 * 根据日期date转换字符串类型
	 * 
	 * @param date
	 * @param str
	 *            str类型
	 * @return
	 */
	public static synchronized String date2Str(Date date, String str) {
		return getDateFormat(date, str);
	}

	/**
	 * 
	 * @param d1
	 * @return
	 */
	public static long getTobeDays(Date d1) {
		if (null == d1) {
			return -1;
		}
		Date d2 = getDateDay(new Date());
		d1 = getDateDay(d1);

		Calendar ca = new GregorianCalendar();
		ca.setTime(d2);
		Calendar ca2 = new GregorianCalendar();
		ca2.setTime(d1);

		long t = ca2.getTimeInMillis() - ca.getTimeInMillis();
		return t / (24 * 60 * 60 * 1000) + 1;

	}

	public static Date getDateDay(Date date) {
		sdf.applyPattern("yyyy-MM-dd");
		try {
			return sdf.parse(sdf.format(date));
		} catch (ParseException e) {
		}
		return date;
	}
	
	/**
	 * @author: ycli5
	 * @createTime: 2014年12月31日 下午1:59:16
	 * @description: 格式化时间
	 * @param datestr
	 * 				时间字符串
	 * @return 
	 * Date
	 */
	public static Date parseDateStr(String datestr,String pattern){
		
		sdf.applyPattern(pattern);
		try {
			return sdf.parse(datestr);
		} catch (ParseException e) {
			return new Date();
		}
	}

	/**
	 * yyyy年MM月dd日
	 * 
	 * @param date
	 * @return
	 */
	public static String getYMDofChina(Date date) {
		StringBuffer dateStr = null;
		if (null == date) {
			return "";
		}
		dateStr = new StringBuffer();
		Calendar c = new GregorianCalendar();
		c.setTime(date);
		dateStr.append(c.get(Calendar.YEAR)).append("年").append(
				c.get(Calendar.MONTH) + 1).append("月").append(
				c.get(Calendar.DATE)).append("日");
		return dateStr.toString();
	}

	/**
	 * 把java.util.Date toString转换成yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date2Str
	 * @return
	 */
	public static String getFormateDate2String(String date2Str) {
		SimpleDateFormat frm = new SimpleDateFormat(
				"EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
		try {
			Date date = frm.parse(date2Str);
			return getDateSecondFormat(date);
		} catch (ParseException e) {
			return "";
		}
	}
	
	/**
	 * @author: ycli5
	 * @createTime: 2014年12月23日 下午3:27:14
	 * @description: 指定日志进行加减天数
	 * @param date
	 * 				日期
	 * @param day
	 * 				天数
	 * @return 
	 * Date
	 */
	public static Date addDay(Date date,int day){
		
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, day);
		
		return c.getTime();
	}
	
	/**
	 * @author: ycli5
	 * @createTime: 2014年12月23日 下午3:27:14
	 * @description: 指定日志进行加减天数
	 * @param date
	 * 				日期
	 * @param day
	 * 				天数
	 * @return 
	 * Date
	 */
	public static Date addMilliSecond(Date date,int millsecond){
		
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MILLISECOND, millsecond);

		return c.getTime();
	}
}

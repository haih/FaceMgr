/**	
 * Copyright 2014 IFlyTek. All rights reserved.<br>
 */
package com.iflytek.aio.common.util;

import java.text.DecimalFormat;

/**
 * @desc: 数字类型的处理类
 * @author: cheney
 * @createTime: 2014-11-4
 * @version: 1.0
 */
public class NumberUtils {

	/**
	 * 判断ID是否为空
	 * 
	 * @param id
	 * @return boolean
	 */
	public static boolean isNullOfId(Long id) {
		if (null == id || id <= 0) {
			return true;
		}
		return false;
	}

	/**
	 * @author: ycli5
	 * @createTime: 2014年12月30日 上午11:24:45
	 * @description: 两数加减操作
	 * @param data1
	 *            shuju 1
	 * @param data2
	 *            shuju 2
	 * @return Long
	 */
	public static Long addLongNumber(String data1, String data2,
			String Operation) {
		if (StringUtils.isBlank(data1)) {
			data1 = "0";
		}
		if (StringUtils.isBlank(data2)) {
			data2 = "0";
		}
		long total = 0l;
		if ("add".equals(Operation)) {
			total = Long.valueOf(data1) + Long.valueOf(data2);
		} else if ("sub".equals(Operation)) {
			total = Long.valueOf(data1) - Long.valueOf(data2);
		}
		return total;
	}

	/**
	 * @author: ycli5
	 * @createTime: 2014年12月23日 下午3:41:14
	 * @description: 两数相除保留两位小数点
	 * @param data1
	 *            shuju 1
	 * @param data2
	 *            shuju 2
	 */
	public static double averNumber2Point(int data1, int data2) {
		String avervisittime = "0";
		if (data2 != 0l) {
			DecimalFormat decimalFormat = new DecimalFormat("#.##");
			avervisittime = decimalFormat.format(((double) data1 / 1000)
					/ (double) data2);
		}
		return Double.valueOf(avervisittime);
	}

	/**
	 * @author: ycli5
	 * @createTime: 2015年5月8日 上午9:16:32
	 * @description:两数相除保留四位小数点
	 * @param data1
	 *            shuju 1
	 * @param data2
	 *            shuju 2
	 * @return double
	 */
	public static double averNumber4Point(int data1, int data2) {
		String avervisittime = "0";
		if (data2 != 0l) {
			DecimalFormat decimalFormat = new DecimalFormat("#.####");
			avervisittime = decimalFormat.format(((double) data1 / 1000)
					/ (double) data2);
		}
		return Double.valueOf(avervisittime);
	}

}

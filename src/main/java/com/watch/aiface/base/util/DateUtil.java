package com.watch.aiface.base.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间转换工具类
 * 
 * @author long
 */
public class DateUtil {
	public static String FORMAT_Y_M_D_H_M_S = "yyyy-MM-dd HH:mm:ss";
	public static String FORMAT_Y_M_D_H_M_S_DES = "yyyy年MM月dd日HH时mm分ss秒";
	public static String FORMAT_Y_M_D = "yyyy-MM-dd";
	public static String FORMAT_Y_M_D_DES = "yyyy年MM月dd日";

	public static String FORMAT_H_M_S = "HH:mm:ss";

	public static String FORMAT_YMDH = "yyyyMMddHH";
	public static String FORMAT_YMDHMS = "yyyyMMddHHmmss";
	public static String FORMAT_YMDHMSS = "yyyyMMddHHmmssSSS";
	public static String FORMAT_Y_M_D_H_M_SS = "yyyy-MM-dd HH:mm:ss:SSS";
	public static String FORMAT_FOLDER = "yyyy/MM/dd/";

	public static String date2Str(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	public static Date str2Date(String source, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(source);
		} catch (ParseException e) {
			return new Date();
		}
	}
	
	/**
	 * 获取系统时间的前几天后几天
	 * @param day
	 * @return
	 * @throws ParseException
	 */
    public static String getStatetime(int day) throws ParseException{
        Calendar c = Calendar.getInstance();  
        c.add(Calendar.DATE, day);  
        Date monday = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_Y_M_D_H_M_S);
        String preMonday = sdf.format(monday);
        return preMonday;
    }

	/**
	 * 获取时分秒
	 * 
	 * @return
	 */
	public static Date nowTime() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 1970);
		cal.set(Calendar.MONTH, 0);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}

}

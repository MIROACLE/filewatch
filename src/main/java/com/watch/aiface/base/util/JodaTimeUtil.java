package com.watch.aiface.base.util;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
 
import java.util.Date;
 
public class JodaTimeUtil {
	
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

    public static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";
 
    public static Date strToDate(String dateTimeStr,String formatStr){
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(formatStr);
        DateTime dateTime = dateTimeFormatter.parseDateTime(dateTimeStr);
        return dateTime.toDate();
    }
 
    public static String dateToStr(Date date,String formatStr){
        if(date == null){
            return StringUtils.EMPTY;
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(formatStr);
    }
 
    public static Date strToDate(String dateTimeStr){
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(STANDARD_FORMAT);
        DateTime dateTime = dateTimeFormatter.parseDateTime(dateTimeStr);
        return dateTime.toDate();
    }
 
    public static String dateToStr(Date date){
        if(date == null){
            return StringUtils.EMPTY;
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(STANDARD_FORMAT);
    }
 
    public static void main(String[] args) {
    	String date="20190517101506267";
    	System.out.println(JodaTimeUtil.strToDate(date,FORMAT_YMDHMSS));
        System.out.println(JodaTimeUtil.dateToStr(new Date(),FORMAT_Y_M_D_H_M_SS));
 
    }
 
}

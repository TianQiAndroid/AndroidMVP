package com.example.androidmvp.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import android.annotation.SuppressLint;

/**
 * 时间转换工具类
 * 
 * @author Administrator
 * 
 */
public class DateTimeUtils {

	@SuppressLint("SimpleDateFormat")
	private final static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 将毫秒数转换为 * days * hours * minutes * seconds 后的格式
	 * 
	 * @param mss
	 *            要转换的毫秒数
	 * @return
	 */
	public static String formatDuring(long mss) {
		long days = mss / (1000 * 60 * 60 * 24);
		long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
		long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
		long seconds = (mss % (1000 * 60)) / 1000;
		return days + "天" + hours + "时" + minutes + "分" + seconds + "秒";
	}
	
	/**
	 * 将时间转换为毫秒值，时间格式yyyy-MM-dd HH:mm:ss
	 * @param date
	 * @return
	 */
	public static long parserDate(String date){
		long retValue = 0;
		try {
			retValue = format.parse(date).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return retValue;
	}
}

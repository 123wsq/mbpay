package com.tangdi.production.mpbase.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.XMLGregorianCalendar;

import com.tangdi.production.tdbase.util.TdExpBasicFunctions;

/**
 * 日期函数
 * 
 * @author zhengqiang
 *
 */
public class DateUtil {
	public static String FULL_TIME_FORMAT_EN = "yyyyMMddHHmmSS";
	public static String FULL_TIME_FORMAT_CH = "yyyy年MM月dd日HH点mm分";

	/**
	 * 日期格式 dd/MM/yyyy 分割线:斜杠
	 */
	public static String FORMAT_DD_MM_YYYY_SLASH = "dd/MM/yyyy";
	/**
	 * 日期格式 dd/MM/yyyy 分割线:斜杠
	 */
	public static String FORMAT_MM_DD_YYYY_SLASH = "MM/dd/yyyy";

	/**
	 * 日期格式 yyyyMMdd
	 */
	public static String FORMAT_YYYYMMDD = "yyyyMMdd";

	/**
	 * 获取日期
	 * 
	 * @param format
	 *            日期格式
	 * @param op
	 *            +1 加1 -1 减1
	 * @return String
	 */
	public static String get(String format, int op) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH, op);
		return sdf.format(calendar.getTime());
	}
	
	public static String get(int i){
		Date now = new Date();
		Date now_10 = new Date(now.getTime() - 60000*i); //i分钟前
		SimpleDateFormat dateFormat = new SimpleDateFormat(FULL_TIME_FORMAT_EN);
		return dateFormat.format(now_10);
	}

	public static String datetrans(XMLGregorianCalendar calendar) {
		DateFormat d = new SimpleDateFormat(FULL_TIME_FORMAT_EN);
		return d.format(calendar.toGregorianCalendar().getTime());

	}

	public static String datetrans1(XMLGregorianCalendar calendar) {
		DateFormat d = new SimpleDateFormat(FULL_TIME_FORMAT_CH);
		return d.format(calendar.toGregorianCalendar().getTime());

	}

	public static String convertDateToString(Date date, String format) {
		DateFormat d = new SimpleDateFormat(format);
		return d.format(date);
	}

	public static Object convertStringToDate(String sdate, String format) {
		DateFormat d = new SimpleDateFormat(format);

		try {
			return d.parseObject(sdate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取当月第一天
	 * 
	 * @return
	 */
	public static String getMonthFirstDay() {
		Calendar cal = Calendar.getInstance();

		SimpleDateFormat datef = new SimpleDateFormat("yyyyMMdd");
		// 当前月的第一天
		cal.set(GregorianCalendar.DAY_OF_MONTH, 1);
		Date beginTime = cal.getTime();
		String beginTime1 = datef.format(beginTime);

		return beginTime1;
	}

	/**
	 * 获取当月最后一天
	 * 
	 * @return
	 */
	public static String getMonthEndDay() {
		Calendar cal = Calendar.getInstance();

		SimpleDateFormat datef = new SimpleDateFormat("yyyyMMdd");
		// 当前月的最后一天
		cal.set(Calendar.DATE, 1);
		cal.roll(Calendar.DATE, -1);
		Date endTime = cal.getTime();
		String endTime1 = datef.format(endTime);

		return endTime1;
	}

	/**
	 * 获取前一天日期
	 * 
	 * @return
	 */
	public static String getYesterday() {

		Date dNow = new Date(); // 当前时间
		Date dBefore = new Date();
		Calendar calendar = Calendar.getInstance(); // 得到日历
		calendar.setTime(dNow);// 把当前时间赋给日历
		calendar.add(Calendar.DAY_OF_MONTH, -1); // 设置为前一天
		dBefore = calendar.getTime(); // 得到前一天的时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); // 设置时间格式

		return sdf.format(dBefore);
	}
	
	/**
	 * 获取前天日期
	 * 
	 * @return
	 */
	public static String getTheDayBeforeYesterday() {

		Date dNow = new Date(); // 当前时间
		Date dBefore = new Date();
		Calendar calendar = Calendar.getInstance(); // 得到日历
		calendar.setTime(dNow);// 把当前时间赋给日历
		calendar.add(Calendar.DAY_OF_MONTH, -2); // 设置为前一天
		dBefore = calendar.getTime(); // 得到前一天的时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); // 设置时间格式

		return sdf.format(dBefore);
	}
	
	public static String getFourdayAgo() {

		Date dNow = new Date(); // 当前时间
		Date dBefore = new Date();
		Calendar calendar = Calendar.getInstance(); // 得到日历
		calendar.setTime(dNow);// 把当前时间赋给日历
		calendar.add(Calendar.DAY_OF_MONTH, -4); // 设置为前一天
		dBefore = calendar.getTime(); // 得到前一天的时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); // 设置时间格式

		return sdf.format(dBefore);
	}

	/**
	 * 获取指定日期的前一天。
	 * 
	 * @return
	 */
	public static String getYesterday(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); // 设置时间格式

		Date dNow = null;
		try {
			dNow = sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 当前时间
		Date dBefore = null;
		Calendar calendar = Calendar.getInstance(); // 得到日历
		calendar.setTime(dNow);// 把当前时间赋给日历
		calendar.add(Calendar.DAY_OF_MONTH, -1); // 设置为前一天
		dBefore = calendar.getTime(); // 得到前一天的时间

		return sdf.format(dBefore);
	}

	public static String getDay() {
		return TdExpBasicFunctions.GETDATE();
	}

	/**
	 * 获取工作日
	 * 
	 * @param date
	 * @return false 不是工作日 true 是工作日
	 */
	public static boolean getWorkDay() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int workday = cal.get(Calendar.DAY_OF_WEEK);
		if (workday == 7 || workday == 1) {
			return false;
		}
		return true;
	}
	
	public static boolean getFirstWorkDay(){
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int workday = cal.get(Calendar.DAY_OF_WEEK);
		if(workday==2){
			return true;
		}
		return false;
	}
	
	/**
	 * 获取节假日
	 * 
	 * @param date
	 * @return false 是节假日 true 不是节假日
	 */
	public static boolean getWeekDay() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int workday = cal.get(Calendar.DAY_OF_WEEK);
		if (workday == 7 || workday == 6) {
			return false;
		}
		return true;
	}

	/**
	 * 把 月日字符串 转换成 月-日 <br>
	 * 如 20150924=9月24日<br>
	 * 0924=9月24日<br>
	 * 09=9月
	 * 
	 * @return String
	 * @throws
	 */
	public static String convertMonthDay(String monthDayStr) {
		if (monthDayStr == null || monthDayStr.length() <= 0) {
			return "";
		}

		if (monthDayStr.length() == 8) {
			monthDayStr = monthDayStr.substring(4);
		}

		String month = monthDayStr.substring(0, 2);
		month = (month.startsWith("0") ? month.replace("0", "") : month) + "月";
		String day = "";
		if (monthDayStr.length() == 4) {
			day = monthDayStr.substring(2);
			day = (day.startsWith("0") ? day.replace("0", "") : day) + "日";
		}
		return month + day;
	}

	/**
	 * 获取当前 年
	 * 
	 * @param length
	 *            长度 截取年份位置
	 * @return
	 */
	public static String getCurrentYear(int length) {
		Calendar cal = Calendar.getInstance();
		String currentYear = String.valueOf(cal.get(Calendar.YEAR));
		if (length > 0) {
			currentYear = currentYear.substring(length);
		}
		return currentYear;
	}

	/**
	 * 获取当前 月份
	 * 
	 * @return
	 */
	public static String getCurrentMonth() {
		Calendar cal = Calendar.getInstance();
		String currentDay = String.valueOf(cal.get(Calendar.MONTH) + 1);
		return currentDay;
	}

	/**
	 * 获取当前 日
	 * 
	 * @return
	 */
	public static String getCurrentDay() {
		Calendar cal = Calendar.getInstance();
		String currentDay = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
		return currentDay;
	}
}

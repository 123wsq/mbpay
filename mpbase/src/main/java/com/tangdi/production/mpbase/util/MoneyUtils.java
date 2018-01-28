package com.tangdi.production.mpbase.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.apache.commons.lang.math.NumberUtils;

/**
 * 金额转换类
 * 
 * @author limiao
 * 
 */
public class MoneyUtils {

	/**
	 * 金额 元 格式转换对象
	 */
	public static DecimalFormat FORMAT_YUAN = new DecimalFormat("0.00");

	/**
	 * 金额 万元 格式转换对象
	 */
	public static DecimalFormat FORMAT_WANYUAN = new DecimalFormat("0.0000");

	/**
	 * 金额 分 格式转换对象
	 */
	public static DecimalFormat FORMAT_FEN = new DecimalFormat("0");

	/**
	 * 转成 分
	 * 
	 * @param obj
	 * @return
	 */
	public static Double toDoubleCent(Object obj) {
		return (NumberUtils.toDouble((String) obj) * 100);
	}

	/**
	 * 转成 分
	 * 
	 * @param obj
	 * @return
	 */
	public static String toStrCent(Object obj) {
		String cent = formatCent(toDoubleCent(obj));
		return cent;
	}

	/**
	 * 转成 元
	 * 
	 * @param obj
	 * @return
	 */
	public static Double toDoubleYuan(Object obj) {
		return (NumberUtils.toDouble(String.valueOf(obj)) / 100d);
	}

	/**
	 * 转成 元
	 * 
	 * @param obj
	 * @return
	 */
	public static String toStrYuan(Object obj) {
		String yuan = formatYuan(toDoubleYuan(obj));
		return yuan == null || "0.00".equals(yuan) ? "0" : yuan;
	}

	/**
	 * 转成 万元
	 * 
	 * @param obj
	 * @return
	 */
	private static Double toDoubleWanYuan(Object obj) {
		return (NumberUtils.toDouble(String.valueOf(obj)) / 1000000d);
	}

	/**
	 * 转成 万元 保留两位小数(四舍五入)
	 * 
	 * @param obj
	 * @return
	 */
	public static String toStrWanYuan(Object obj) {
		String wanyuan = formatWanYuan(toDoubleWanYuan(obj));
		wanyuan = wanyuan.endsWith("0") ? wanyuan.replaceAll("[0]+$", "") : wanyuan;
		wanyuan = wanyuan.endsWith(".") ? wanyuan.replace(".", "") : wanyuan;
		return wanyuan;
	}

	/**
	 * 格式化分 ，变成没有小数点的数字字符串
	 * 
	 * @param obj
	 * @return
	 */
	private static String formatCent(Object obj) {
		return FORMAT_FEN.format(obj);
	}

	/**
	 * 格式化元 ，变成两位小数点的数字字符串
	 * 
	 * @param obj
	 * @return
	 */
	private static String formatYuan(Object obj) {
		return FORMAT_YUAN.format(obj);
	}

	/**
	 * 格式化万元 ，变成六位小数点的数字字符串
	 * 
	 * @param obj
	 * @return
	 */
	private static String formatWanYuan(Object obj) {
		return FORMAT_WANYUAN.format(obj);
	}

	/**
	 * 相乘 精确2位小数 0.00
	 * 
	 * @param amt1
	 * @param amt2
	 * @return
	 */
	public static BigDecimal multiply(String amt1, String amt2) {
		BigDecimal bd = new BigDecimal(amt1);
		return bd.multiply(new BigDecimal(amt2)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
	}

	/**
	 * 相乘 精确2位小数 0.00
	 * 
	 * @param amt1
	 * @param amt2
	 * @return
	 */
	public static BigDecimal multiply(String amt1, double amt2) {
		BigDecimal bd = new BigDecimal(amt1);
		return bd.multiply(new BigDecimal(amt2)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
	}

	/**
	 * 相除 精确2位小数 0.00
	 * 
	 * @param amt1
	 * @param amt2
	 * @return
	 */
	public static BigDecimal divide(String amt1, String amt2) {
		BigDecimal bd = new BigDecimal(0);
		return bd.add(new BigDecimal(amt1)).divide(new BigDecimal(amt2), 2, BigDecimal.ROUND_UP);
	}

	/**
	 * 大小比较
	 * 
	 * @param amt1
	 * @param amt2
	 * @return
	 */
	public static boolean compare(String amt1, String amt2) {

		return true;
	}

	/**
	 * 计算费用 [提供精确的小数位四舍五入处理,保留2位小数。 ]
	 * 
	 * @param rate
	 *            点数
	 * @param txamt
	 *            分
	 * @return 分
	 */
	public static int calculate(String rate, String txamt) {
		Double rate_ = toDoubleYuan(rate);
		Double txamt_ = toDoubleYuan(txamt);

		Double tmpTxAmt = mul(rate_, txamt_);
		Double tmpTxAmt_ = mul(round(tmpTxAmt.doubleValue(), 2), 100.00);
		return tmpTxAmt_.intValue();
	}

	/**
	 * 计算费用 [提供精确的小数位四舍五入处理,保留2位小数。 ]
	 * 
	 * @param rate
	 *            点数
	 * @param txamt
	 *            分
	 * @return 分
	 */
	public static int calculate(String rate, int txamt) {
		Double rate_ = toDoubleYuan(rate);
		Double txamt_ = toDoubleYuan(txamt);

		Double tmpTxAmt = mul(rate_, txamt_);
		Double tmpTxAmt_ = mul(round(tmpTxAmt.doubleValue(), 2), 100.00);
		return tmpTxAmt_.intValue();
	}

	/**
	 * 计算费用，最低收取 1分 [提供精确的小数位四舍五入处理,保留2位小数。 ]
	 * 
	 * @param rate
	 *            点数
	 * @param txamt
	 *            分
	 * @param low
	 *            分
	 * @return 分
	 */
	public static int calculate(String rate, String txamt, double low) {
		double rate_ = toDoubleYuan(rate);
		double txamt_ = toDoubleYuan(txamt);

		Double tmpTxAmt = mul(rate_, txamt_);
		Double tmpTxAmt_ = mul(round(tmpTxAmt.doubleValue(), 2), 100.00);

		if (tmpTxAmt_.intValue() <= 0) {
			tmpTxAmt_ = low;
		}

		return tmpTxAmt_.intValue();
	}

	/**
	 * Double相乘
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static Double mul(Double v1, Double v2) {
		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		return new Double(b1.multiply(b2).doubleValue());
	}

	/**
	 * 提供精确的小数位四舍五入处理。
	 * 
	 * @param v
	 *            需要四舍五入的数字
	 * @param scale
	 *            小数点后保留几位
	 * @return 四舍五入后的结果
	 */
	public static double round(double v, int scale) {
		BigDecimal b = new BigDecimal(Double.toString(v));
		return b.divide(new BigDecimal("1"), scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
}

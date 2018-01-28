package com.tangdi.production.mpbase.service;

import java.util.List;
import java.util.Map;

/**
 * 业务报表接口
 * @author zhengqiang
 *
 */
public interface FileReportService<T> {
	
	/**
	 * 导出CSV文件
	 * @param data 数据
	 * @param uid 用户编号
	 * @param filename 文件名称
	 * @param title    标题(CSV,TXT时为空,EXCEL用)
	 * @param dataType 数据类型 1 交易报表 2数据报表 3密钥文件
	 * @param fileType 文件类型 1 CSV 2 EXCEL 3TXT
	 * @param cdata    备用数据域
	 * @return 0 成功 1失败
	 * @throws Exception
	 */
	public int report(List<T> data,String uid,
			String filename,String title,String dataType,String fileType,String[] cdata) throws Exception;
	
	/**
	 * 打包生成多个文件
	 * @param uid 用户编号
	 * @param filename 文件名称
	 * @param title    标题(CSV,TXT时为空,EXCEL用)
	 * @param dataType 数据类型 1 交易报表 2数据报表 3密钥文件
	 * @param fileType 文件类型 1 CSV 2 EXCEL 3TXT
	 * @param cdata    备用数据域
	 * @param datas     多数据域（每个数据域生成一个文件）
	 * @return 0 成功 1失败
	 * @throws Exception
	 */
	public int reportZIP(String uid,
			String filename,String title,String dataType,String fileType,String[] cdata,Map<String,List<T>> datas) throws Exception;

	/**
	 * 电子签名打包
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	public int reportEsign(String uid) throws Exception;

	

}

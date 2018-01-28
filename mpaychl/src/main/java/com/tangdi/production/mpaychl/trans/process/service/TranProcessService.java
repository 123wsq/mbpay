package com.tangdi.production.mpaychl.trans.process.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.tangdi.production.mpaychl.base.service.EncryptorAbstractService;
import com.tangdi.production.mpaychl.base.service.TermService;
import com.tangdi.production.mpaychl.base.service.TranService;
import com.tangdi.production.mpbase.constants.ExcepCode;
import com.tangdi.production.mpbase.exception.TranException;
import com.tangdi.production.tdbase.context.SpringContext;
import com.tangdi.production.tdcomm.idservice.GetSeqNoService;

/**
 * 
 * 交易[外发]渠道公共处理流程基类<br/>
 * <br/>
 * 基类 封装通用方法:<br/>
 * 配置文件对象[prop]、获取序号方法[getPosSEQ()]、错误码[EXMAP]、PIN转加密[convertPIN]
 * @author zhengqiang 2015/8/7
 *
 */

public abstract class TranProcessService extends EncryptorAbstractService implements TranService{
	private static Logger log = LoggerFactory
			.getLogger(TranProcessService.class);
	/**
	 * 配置文件
	 */
	@Value("#{mpaychlConfig}")
	protected Properties prop;
	
	/**
	 * 流水号接口
	 */
	@Autowired
	private GetSeqNoService seqNoService;

	/**
	 * 获取POS交易流水 11域
	 * @return
	 */
	protected String getPosSEQ() {
		 String seqNo = null;
		 try {
			 seqNo = seqNoService.getSeqNoNew("POS_ID", "6", "1");
			 log.info("获取POS交易流水号：[{}]",seqNo);
		 } catch (Exception e) {
			 log.error("获取POS交易流水号失败,设置默认流水号[000001]");
			 seqNo = "000001";
		 }
		 return seqNo;
	}
	
	protected String getCrdSqn(String sqn){
		String sqn_ = ("000" + sqn);		
		return sqn_.substring(sqn_.length()-sqn.length()); 
	}
	
	/**
	 * PIN转加密
	 * @param map
	 * @return 加密后PINKBLOCK
	 * @throws TranException
	 */
	protected String convertPIN(Map<String,Object> map) throws TranException{
		/**
		 * 刷卡器终端接口服务注入,处理磁道和PIN加解密
		 * 通过实例名称[termServiceName]从Spring容器中获取厂商实例。
		 */
		TermService terminalService = SpringContext.getBean(String.valueOf(map.get(TERM_SERVICE_NAMEKEY)),
				TermService.class);
		
		 log.info("PIN转加密中.");
		 //转PIN
		 return terminalService.convertPIN(map);
		
	}
	
	/**
	 * PIN转加密
	 * @param map
	 * @return 加密后PINKBLOCK
	 * @throws TranException
	 */
	protected String decryptPIN(Map<String,Object> map) throws TranException{
		/**
		 * 刷卡器终端接口服务注入,处理磁道和PIN加解密
		 * 通过实例名称[termServiceName]从Spring容器中获取厂商实例。
		 */
		TermService terminalService = SpringContext.getBean(String.valueOf(map.get(TERM_SERVICE_NAMEKEY)),
				TermService.class);
		
		 log.info("PIN转加密中.");
		 //转PIN
		 return terminalService.decryptPIN(map);
		
	}
	
	/**
	 * 通道方错误代码 转换成系统自定义异常信息
	 */
	protected static final Map<String,TranException> EXMAP = new HashMap<String,TranException>(){
			private static final long serialVersionUID = 1L;
			{
				put("01", new TranException(ExcepCode.EX200301));// 01 请联系发卡行
				put("02", new TranException(ExcepCode.EX200302));// 02 无效商户
				put("03", new TranException(ExcepCode.EX200303));// 03 无效商户
				put("04", new TranException(ExcepCode.EX200304));// 04 没收卡，请与发卡行联系
				put("05", new TranException(ExcepCode.EX200305));// 05 持卡人认证失败
				put("06", new TranException(ExcepCode.EX200306));// 06 交易金额超限
				put("07", new TranException(ExcepCode.EX200307));// 07 暂时不能退货
				put("08", new TranException(ExcepCode.EX200308));// 08 没有原交易
				put("09", new TranException(ExcepCode.EX200309));// 09 撤销只允许当日操作
				put("10", new TranException(ExcepCode.EX200310));// 10 无参数需下载
				put("11", new TranException(ExcepCode.EX200311));// 11 无效卡号
				put("12", new TranException(ExcepCode.EX200312));// 12 无效交易
				put("13", new TranException(ExcepCode.EX200313));// 13 无效金额
				put("14", new TranException(ExcepCode.EX200314));// 14 无效卡号
				put("15", new TranException(ExcepCode.EX200315));// 15 无此发卡行
				put("16", new TranException(ExcepCode.EX200316));// 16 无此账户
				put("17", new TranException(ExcepCode.EX200317));// 17 挂失卡
				put("18", new TranException(ExcepCode.EX200318));// 18 被窃卡
				put("19", new TranException(ExcepCode.EX200319));// 19 余额不足
				put("20", new TranException(ExcepCode.EX200320));// 20 过期卡
				put("21", new TranException(ExcepCode.EX200321));// 21 该卡未初始化或睡眠卡
				put("22", new TranException(ExcepCode.EX200322));// 22 操作有误，或超出交易允许天数
				put("23", new TranException(ExcepCode.EX200323));// 23 非法交易
				put("24", new TranException(ExcepCode.EX200324));// 24 有作弊嫌疑
				put("25", new TranException(ExcepCode.EX200325));// 25 没有原交易
				put("26", new TranException(ExcepCode.EX200326));// 26 无效原金额
				put("27", new TranException(ExcepCode.EX200327));// 27 没收卡
				put("28", new TranException(ExcepCode.EX200328));// 28 PIN输入超过次数
				put("29", new TranException(ExcepCode.EX200329));// 29 止付卡
				put("30", new TranException(ExcepCode.EX200330));// 30 报文格式错误
				put("31", new TranException(ExcepCode.EX200331));// 31 交易拒绝
				put("32", new TranException(ExcepCode.EX200332));// 32 卡已作废
				put("33", new TranException(ExcepCode.EX200333));// 33 重做交易或电话授权
				put("34", new TranException(ExcepCode.EX200334));// 34 有作弊嫌疑
				put("35", new TranException(ExcepCode.EX200335));// 35 PIN格式错，请重新签到
				put("36", new TranException(ExcepCode.EX200336));// 36 不匹配的交易
				put("37", new TranException(ExcepCode.EX200337));// 37 有效期错
				put("38", new TranException(ExcepCode.EX200338));// 38 PIN输入超过次数
				put("39", new TranException(ExcepCode.EX200339));// 39 手输卡号不允许做该交易
				put("40", new TranException(ExcepCode.EX200340));// 40 商户不支持此交易
				put("41", new TranException(ExcepCode.EX200341));// 41 挂失卡
				put("42", new TranException(ExcepCode.EX200342));// 42 不批准交易
				put("43", new TranException(ExcepCode.EX200343));// 43 被窃卡
				put("44", new TranException(ExcepCode.EX200344));// 44 不做任何处理
				put("45", new TranException(ExcepCode.EX200345));// 45 降级交易
				put("46", new TranException(ExcepCode.EX200346));// 46 拨号电话非法
				put("47", new TranException(ExcepCode.EX200347));// 47 退货金额超限
				put("48", new TranException(ExcepCode.EX200348));// 48 当日不许退货
				put("49", new TranException(ExcepCode.EX200349));// 49 30天内退货
				put("50", new TranException(ExcepCode.EX200350));// 50 余额不足
				put("51", new TranException(ExcepCode.EX200351));// 51 余额不足
				put("52", new TranException(ExcepCode.EX200352));// 52 格式错误
				put("53", new TranException(ExcepCode.EX200353));// 53 没收卡，请与发卡行联系
				put("54", new TranException(ExcepCode.EX200354));// 54 过期卡
				put("55", new TranException(ExcepCode.EX200355));// 55 密码错
				put("56", new TranException(ExcepCode.EX200356));// 56 超出发卡行取款次数限制
				put("57", new TranException(ExcepCode.EX200357));// 57 该卡不允许做此交易
				put("58", new TranException(ExcepCode.EX200358));// 58 商户不支持此交易
				put("59", new TranException(ExcepCode.EX200359));// 59 有作弊嫌疑
				put("60", new TranException(ExcepCode.EX200360));// 60 银行MAC校验错
				put("61", new TranException(ExcepCode.EX200361));// 61 超出金额限制
				put("62", new TranException(ExcepCode.EX200362));// 62 受限制卡
				put("63", new TranException(ExcepCode.EX200363));// 63 操作有误，或超出交易允许天数
				put("64", new TranException(ExcepCode.EX200364));// 64 无效原金额
				put("65", new TranException(ExcepCode.EX200365));// 65 超出发卡行取款次数限制
				put("66", new TranException(ExcepCode.EX200366));// 66 发卡方不允许该卡在本终端进行此交易
				put("67", new TranException(ExcepCode.EX200367));// 67 发卡行响应超时
				put("68", new TranException(ExcepCode.EX200368));// 68 发卡行响应超时
				put("69", new TranException(ExcepCode.EX200369));// 69 发卡方状态不正常，请稍后重试
				put("70", new TranException(ExcepCode.EX200370));// 70 发卡方线路异常，请稍后重试
				put("71", new TranException(ExcepCode.EX200371));// 71 银联交换中心异常
				put("72", new TranException(ExcepCode.EX200372));// 72 终端号未登记
				put("73", new TranException(ExcepCode.EX200373));// 73 请先签到
				put("74", new TranException(ExcepCode.EX200374));// 74 请联系收单机构手工退货
				put("75", new TranException(ExcepCode.EX200375));// 75 PIN输入超过次数
				put("76", new TranException(ExcepCode.EX200376));// 76 上批未结，请先结完上批
				put("77", new TranException(ExcepCode.EX200377));// 77 不支持该卡种
				put("78", new TranException(ExcepCode.EX200378));// 78 根据银联规定，完成通知交易不能撤销
				put("79", new TranException(ExcepCode.EX200379));// 79 请使用与预授权交易同一类型终端做完成交易
				put("80", new TranException(ExcepCode.EX200380));// 80 请使用刷卡方式进行交易
				put("81", new TranException(ExcepCode.EX200381));// 81 请先进行结账
				put("82", new TranException(ExcepCode.EX200382));// 82 无效卡号
				put("83", new TranException(ExcepCode.EX200383));// 83 降级交易
				put("90", new TranException(ExcepCode.EX200390));// 90 系统日切，请稍后后重试
				put("91", new TranException(ExcepCode.EX200391));// 91 交易失败
				put("92", new TranException(ExcepCode.EX200392));// 92 发卡方线路异常，请稍后重试
				put("94", new TranException(ExcepCode.EX200394));// 94 重复交易
				put("96", new TranException(ExcepCode.EX200396));// 96 处理中心系统异常，请稍后重试
				put("97", new TranException(ExcepCode.EX200397));// 97 终端号未登记
				put("98", new TranException(ExcepCode.EX200398));// 98 发卡方超时
				put("99", new TranException(ExcepCode.EX200399));// 99 PIN格式错，请重新签到
				put("A0", new TranException(ExcepCode.EX2003A0));// A0 MAC校验错，请重新签到
				put("A1", new TranException(ExcepCode.EX2003A1));// A1 其他错误
				put("A2", new TranException(ExcepCode.EX2003A2));// A2 交易成功，请向发卡行确认
				put("A3", new TranException(ExcepCode.EX2003A3));// A3 账户不正确
				put("A4", new TranException(ExcepCode.EX2003A4));// A4 交易成功，请向发卡行确认
				put("A5", new TranException(ExcepCode.EX2003A5));// A5 交易成功，请向发卡行确认
				put("A6", new TranException(ExcepCode.EX2003A6));// A6 交易成功，请向发卡行确认
				put("A7", new TranException(ExcepCode.EX2003A7));// A7 安全处理失败
				put("M1", new TranException(ExcepCode.EX2003M1));// M1 POS中心异常，请稍后重试
				put("M2", new TranException(ExcepCode.EX2003M2));// M2 软件版本错误，请稍后重试
				put("M3", new TranException(ExcepCode.EX2003M3));// M3 软件版本错误，请稍后重试
				put("M4", new TranException(ExcepCode.EX2003M4));// M4 POS中心异常，请稍后重试
				put("M5", new TranException(ExcepCode.EX2003M5));// M5 批次号错误
				put("M6", new TranException(ExcepCode.EX2003M6));// M6 商户信息错误
				put("M7", new TranException(ExcepCode.EX2003M7));// M7 请重新签到
				put("M8", new TranException(ExcepCode.EX2003M8));// M8 重传脱机数据
				put("M9", new TranException(ExcepCode.EX2003M9));// M9 机构不存在
				put("B0", new TranException(ExcepCode.EX2003B0));// B0 数据校验错误
				put("00", new TranException(ExcepCode.EX200300));// 00 交易成功
			}
		};
}

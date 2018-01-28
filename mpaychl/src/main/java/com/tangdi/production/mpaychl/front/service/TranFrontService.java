package com.tangdi.production.mpaychl.front.service;

import java.util.Map;

import com.tangdi.production.mpaychl.base.service.TranService;
import com.tangdi.production.mpbase.exception.TranException;

/**
 * <b>交易前置接口</b></br>
 * 主要功能：终端签到(刷卡器[蓝牙|音频]) 、 刷卡消费 、 银行卡余额查询
 * @author zhengqiang 2015/3/25
 *
 */
public interface TranFrontService extends TranService{

	/**
	 * 数据磁道数据
	 * @param pmap
	 * @return [0] 二磁 [1] 三磁  [2] ICDATA
	 * @throws TranException
	 */
	public String[] decryptTrack(Map<String,Object> pmap)throws TranException;
	
	/**
	 * 选取终端厂商
	 * @param pmap
	 * @return [0] 二磁 [1] 三磁  [2] ICDATA
	 * @throws TranException
	 */
	public void selectTermService(Map<String,Object> pmap)throws TranException;


}

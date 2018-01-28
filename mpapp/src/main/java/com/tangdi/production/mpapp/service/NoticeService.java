package com.tangdi.production.mpapp.service;

import java.util.List;
import java.util.Map;

import com.tangdi.production.mpbase.exception.TranException;
import com.tangdi.production.mpomng.bean.NoticeInf;


/**
 * 公告查询接口
 * @author huchunyuan
 *
 */
public interface NoticeService {
	
	/**
	 * 通过最后的公告id查询公告信息
	 * @return
	 * @throws MerchantRegisterException
	 */
	public List<Map<String, Object>> getNotice(Map<String, Object> param) throws Exception;


	public int saveType(Map<String, Object> map)throws TranException;
}

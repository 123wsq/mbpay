package com.tangdi.production.mpbase.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tangdi.production.mpbase.bean.TranErrorCodeInf;
import com.tangdi.production.mpbase.dao.TranErrorCodeDao;
import com.tangdi.production.mpbase.service.TranErrorCodeService;
import com.tangdi.production.mpbase.util.TRANCODE;

/**
 * 消息代码接口实现类
 * @author zhengqiang
 *
 */
@Service
public class TranErrorCodeServiceImpl implements TranErrorCodeService {
	private static final Logger log = LoggerFactory
			.getLogger(TranErrorCodeServiceImpl.class);
	@Autowired
	private TranErrorCodeDao dao ;

	@Override
	public Map<String, Object> queryAll() throws Exception {
		log.debug("MPBASE模块[渠道错误码]数据初始化...");
		List<TranErrorCodeInf> msgMap = null;
		try{
			Map<String, TranErrorCodeInf> map = new HashMap<String,TranErrorCodeInf>();

			msgMap = dao.selectAll();
			for(TranErrorCodeInf msg:msgMap){
				map.put(msg.getOrgno()+msg.getTid(), msg);
			}
			TRANCODE.put(map);
			log.debug("渠道错误代码:[{}]",msgMap.size());
		}catch(Exception e){
			log.error("MPBASE模块[渠道错误码]数据初始化失败!"+e.getMessage(),e);
		}
		log.debug("MPBASE模块[渠道错误码]数据初始化完成.");
		
		return null;
	}


}

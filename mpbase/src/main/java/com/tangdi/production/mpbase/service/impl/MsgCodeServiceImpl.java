package com.tangdi.production.mpbase.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tangdi.production.mpbase.bean.MsgCodeInf;
import com.tangdi.production.mpbase.dao.MsgCodeDao;
import com.tangdi.production.mpbase.service.MsgCodeService;
import com.tangdi.production.mpbase.util.MSGCODE;

/**
 * 消息代码接口实现类
 * @author zhengqiang
 *
 */
@Service
public class MsgCodeServiceImpl implements MsgCodeService {
	private static final Logger log = LoggerFactory
			.getLogger(MsgCodeServiceImpl.class);
	@Autowired
	private MsgCodeDao dao ;

	

	@Override
	public Map<String, Object> queryAll() throws Exception {
		log.debug("MPBASE模块[消息码]数据初始化...");
		List<MsgCodeInf> msgMap = null;
		try{
			Map<String, MsgCodeInf> map = new HashMap<String,MsgCodeInf>();

			msgMap = dao.selectAll();
			for(MsgCodeInf msg:msgMap){
				map.put(msg.getMsgId(), msg);
			}
			MSGCODE.put(map);
			log.debug("消息代码:[{}]",msgMap.size());
		}catch(Exception e){
			log.error("MPBASE模块[消息码]数据初始化失败!"+e.getMessage(),e);
		}
		log.debug("MPBASE模块[消息码]数据初始化完成.");
		
		return null;
	}



	@Override
	public List<MsgCodeInf> getListPage(MsgCodeInf entity) throws Exception {
		return dao.selectList(entity);
	}



	@Override
	public Integer getCount(MsgCodeInf entity) throws Exception {
		return dao.countEntity(entity);
	}



	@Override
	public MsgCodeInf getEntity(MsgCodeInf entity) throws Exception {
		log.debug("方法参数："+entity.debug());
		MsgCodeInf msgCodeInf = null;
		try{
			msgCodeInf = dao.selectEntity(entity);
		}catch(Exception e){
			throw new Exception("查询信息异常!"+e.getMessage(),e);
		}
		if(msgCodeInf != null){
		    log.debug("处理结果:",msgCodeInf.debug());
		}
		return msgCodeInf;
	}



	@Override  @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public int addEntity(MsgCodeInf entity) throws Exception {
		int rt = 0;
		log.debug("方法参数:{}",entity.debug());
		try{
			 rt = dao.insertEntity(entity);
			 log.debug("处理结果:[{}]",rt);
		}catch(Exception e){
			log.error(e.getMessage(),e);
			throw new Exception("插入数据异常!"+e.getMessage(),e);
		}
		return rt;
	}



	@Override @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public int modifyEntity(MsgCodeInf entity) throws Exception {
		int rt = 0;
		log.debug("方法参数:{}",entity.debug());
		try{
			 rt = dao.updateEntity(entity);
			 log.debug("处理结果:[{}]",rt);
		}catch(Exception e){
			log.error(e.getMessage(),e);
			throw new Exception("更新数据异常!"+e.getMessage(),e);
		}
		return rt;
	}



	@Override
	public int removeEntity(MsgCodeInf entity) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int modifyFlag(String msgId, int msgFlag) throws Exception {
		Map<String, Object> con = new HashMap<String, Object>(2);
		if(msgFlag == 1){
			msgFlag = 0;
		}else{
			msgFlag = 1;
		}
		con.put("msgFlag", msgFlag);
		con.put("msgId", msgId);
		log.debug(msgId);
		int num = 0;
		try {
			num = dao.updateModifyFlag(con);
		} catch (Exception e) {
			log.error("错误消息标记切换异常！", e);
			throw new Exception("错误消息标记切换异常！", e);
		}

		return num;
	}

}

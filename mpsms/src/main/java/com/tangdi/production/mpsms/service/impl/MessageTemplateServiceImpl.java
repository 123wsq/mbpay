package com.tangdi.production.mpsms.service.impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tangdi.production.mpsms.dao.MessageTemplateDao;
import com.tangdi.production.mpsms.service.MessageTemplateService;
import com.tangdi.production.mpsms.bean.MessageTemplateInf;


/**
 * 
 * @author zhengqiang
 * @version 1.0
 *
 */
@Service
public class MessageTemplateServiceImpl implements MessageTemplateService{
	private static final Logger log = LoggerFactory
			.getLogger(MessageTemplateServiceImpl.class);
	@Autowired
	private MessageTemplateDao dao;

	@Override
	public List<MessageTemplateInf> getListPage(MessageTemplateInf entity) throws Exception {
		return dao.selectList(entity);
	}

	@Override
	public Integer getCount(MessageTemplateInf entity) throws Exception {
		return dao.countEntity(entity);
	}

	@Override
	public MessageTemplateInf getEntity(MessageTemplateInf entity) throws Exception {
		log.debug("方法参数："+entity.debug());
		MessageTemplateInf messageTemplateInf = null;
		try{
			messageTemplateInf = dao.selectEntity(entity);
		}catch(Exception e){
			throw new Exception("查询信息异常!"+e.getMessage(),e);
		}
		if(messageTemplateInf != null){
			log.debug("处理结果:",messageTemplateInf.debug());
		}
		return messageTemplateInf;
	}

	@Override @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public int addEntity(MessageTemplateInf entity) throws Exception {
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
	public int modifyEntity(MessageTemplateInf entity) throws Exception {
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

	@Override @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public int removeEntity(MessageTemplateInf entity) throws Exception {
		int rt = 0;
		log.debug("方法参数:{}",entity.debug());
		try{
			 rt = dao.deleteEntity(entity);
			 log.debug("处理结果:[{}]",rt);
		}catch(Exception e){
			log.error(e.getMessage(),e);
			throw new Exception("删除数据异常!"+e.getMessage(),e);
		}
		return rt;
	}



	
	
}

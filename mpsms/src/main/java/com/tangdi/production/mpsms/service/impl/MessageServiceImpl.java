package com.tangdi.production.mpsms.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tangdi.production.mpsms.bean.MessageInf;
import com.tangdi.production.mpsms.dao.MessageDao;
import com.tangdi.production.mpsms.service.MessageService;
import com.tangdi.production.tdbase.util.TdExpBasicFunctions;
import com.tangdi.production.tdcomm.idservice.GetSeqNoService;

/**
 * 
 * @author huchunyuan
 * @version 1.0
 *
 */
@Service
public class MessageServiceImpl implements MessageService {
	private static final Logger log = LoggerFactory.getLogger(MessageServiceImpl.class);
	@Autowired
	private MessageDao dao;
	@Autowired
	private GetSeqNoService seqNoService;
	@Override
	public List<MessageInf> getListPage(MessageInf entity) throws Exception {
		return dao.selectList(entity);
	}

	@Override
	public Integer getCount(MessageInf entity) throws Exception {
		return dao.countEntity(entity);
	}

	@Override
	public MessageInf getEntity(MessageInf entity) throws Exception {
		log.debug("方法参数：" + entity.debug());
		MessageInf messageInf = null;
		try {
			messageInf = dao.selectEntity(entity);
		} catch (Exception e) {
			throw new Exception("查询信息异常!" + e.getMessage(), e);
		}
		log.debug("处理结果:", messageInf.debug());
		return messageInf;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int addEntity(MessageInf entity) throws Exception {
		int rt = 0;
		log.debug("方法参数:{}", entity.debug());
		try {
			
			String smsId = "0"+TdExpBasicFunctions.GETDATE().
					substring(2)+seqNoService.getSeqNoNew("SMS_ID0", "8", "0");
			entity.setSmsId(smsId);
			rt = dao.insertEntity(entity);
			log.debug("处理结果:[{}]", rt);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new Exception("插入数据异常!" + e.getMessage(), e);
		}
		return rt;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int modifyEntity(MessageInf entity) throws Exception {
		int rt = 0;
		log.debug("方法参数:{}", entity.debug());
		try {
			rt = dao.updateEntity(entity);
			log.debug("处理结果:[{}]", rt);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new Exception("更新数据异常!" + e.getMessage(), e);
		}
		return rt;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int removeEntity(MessageInf entity) throws Exception {
		int rt = 0;
		log.debug("方法参数:{}", entity.debug());
		try {
			rt = dao.deleteEntity(entity);
			log.debug("处理结果:[{}]", rt);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new Exception("删除数据异常!" + e.getMessage(), e);
		}
		return rt;
	}
}

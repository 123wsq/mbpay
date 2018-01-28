package com.tangdi.production.mpomng.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tangdi.production.mpomng.bean.NoticeInf;
import com.tangdi.production.mpomng.dao.NoticeDao;
import com.tangdi.production.mpomng.service.NoticeService;
import com.tangdi.production.tdbase.util.TdExpBasicFunctions;
import com.tangdi.production.tdcomm.idservice.GetSeqNoService;

/**
 * 
 * @author limiao
 * @version 1.0
 *
 */
@Service
public class NoticeServiceImpl implements NoticeService {
	private static final Logger log = LoggerFactory.getLogger(NoticeServiceImpl.class);
	@Autowired
	private NoticeDao dao;
	
	@Autowired
	private GetSeqNoService seqNoService;
	

	@Override
	public List<NoticeInf> getListPage(NoticeInf entity) throws Exception {
		return dao.selectList(entity);
	}

	@Override
	public Integer getCount(NoticeInf entity) throws Exception {
		return dao.countEntity(entity);
	}

	@Override
	public NoticeInf getEntity(NoticeInf entity) throws Exception {
		log.debug("方法参数：" + entity.debug());
		NoticeInf noticeInf = null;
		try {
			noticeInf = dao.selectEntity(entity);
		} catch (Exception e) {
			throw new Exception("查询信息异常!" + e.getMessage(), e);
		}
		log.debug("处理结果:", noticeInf.debug());
		return noticeInf;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int addEntity(NoticeInf entity) throws Exception {
		int rt = 0;
		log.debug("方法参数:{}", entity.debug());
		try {
			String noticeId = TdExpBasicFunctions.GETDATE() + seqNoService.getSeqNoNew("NOTICE_ID", "9", "1");
			entity.setNoticeId(noticeId);
			rt = dao.insertEntity(entity);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new Exception("插入数据异常!" + e.getMessage(), e);
		}
		return rt;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int modifyEntity(NoticeInf entity) throws Exception {
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
	public int removeEntity(NoticeInf entity) throws Exception {
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

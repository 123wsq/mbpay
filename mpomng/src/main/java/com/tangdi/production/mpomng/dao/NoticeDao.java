package com.tangdi.production.mpomng.dao;

import com.tangdi.production.mpomng.bean.NoticeInf;
import com.tangdi.production.tdbase.dao.BaseDao;

/**
 *
 * 
 *
 * @author limiao
 * @version 1.0
 */
public interface NoticeDao extends BaseDao<NoticeInf, Exception> {

	public NoticeInf selectInf();
	public NoticeInf selectType(NoticeInf noticeInf);
	public int saveType(NoticeInf noticeInf);
}

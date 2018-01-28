package com.tangdi.production.mpamng.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tangdi.production.mpomng.bean.NoticeInf;
import com.tangdi.production.tdbase.dao.BaseDao;

/**
 *
 * 
 *
 * @author sunhaining
 * @version 1.0
 */
public interface AgentNoticeDao extends BaseDao<NoticeInf, Exception> {
	/***
	 * 根据公告平台类型查询所有公告
	 * 
	 * @param platform
	 * @return
	 */
	List<NoticeInf> findNoticesByPlatform(String platform);

	/***
	 * 根据公告内容获取公告ID
	 * 
	 * @param id
	 * @return
	 */
	NoticeInf getNoticeInfById(String id);

	/***
	 * 分页查询公告信息
	 * 
	 * @param start
	 * @param end
	 * @param platform
	 * @return
	 */
	List<NoticeInf> findPageNoticesByPlatform(@Param("start") int start, @Param("end") int end, @Param("platform") String platform);
}

package com.tangdi.production.mpapp.dao;

import java.util.List;
import java.util.Map;

import com.tangdi.production.mpomng.bean.NoticeInf;


/**
 * 公告查询接口
 * @author huchunyuan
 *
 */
public interface NoticeDao {
	
	/**
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> selectEntity(Map<String,Object> param) throws Exception ;
	
	public List<Map<String,Object>> selectNotice(Map<String,Object> param) throws Exception ;
	
	public int saveType(Map<String,Object> param);
	
	public Map<String, Object> getNoticeType(Map<String,Object> param);
}

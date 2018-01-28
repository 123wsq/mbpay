package com.tangdi.production.mpbase.dao;

import com.tangdi.production.mpbase.bean.FileDownloadInf;
import com.tangdi.production.tdbase.dao.BaseDao;

/**
*
* 文件下载DAO <br/>
*
* @author zhengqiang
* @version 1.0
*/
public interface FileDownloadDao extends BaseDao<FileDownloadInf,Exception>{
	/**
	 * 删除文件
	 * 
	 * @param id文件dId
	 * @return
	 * @throws Exception
	 */
	public int deleteFile(String id) throws Exception;
}

package com.tangdi.production.mpbase.service;



import com.tangdi.production.mpbase.bean.FileDownloadInf;
import com.tangdi.production.tdbase.service.BaseService;


/**
 * 文件下载接口层（业务报表数据）
 * @author zhengqiang
 * @version 1.0
 *
 */
public interface FileDownloadService extends BaseService<FileDownloadInf,Exception>{
	
	/**
	 * 删除文件
	 * 
	 * @param id文件dId
	 * @return
	 * @throws Exception
	 */
	public int removeFile(String ids) throws Exception;

}

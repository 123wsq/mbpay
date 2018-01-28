package com.tangdi.production.mpbase.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tangdi.production.mpbase.bean.FileDownloadInf;
import com.tangdi.production.mpbase.dao.FileDownloadDao;
import com.tangdi.production.mpbase.service.FileDownloadService;


/**
 * 文件下载接口实现类
 * @author zhengqiang
 * @version 1.0
 *
 */
@Service
public class FileDownloadServiceImpl implements FileDownloadService{
	private static final Logger log = LoggerFactory
			.getLogger(FileDownloadServiceImpl.class);
	@Autowired
	private FileDownloadDao dao;

	@Override
	public List<FileDownloadInf> getListPage(FileDownloadInf entity) throws Exception {
		return dao.selectList(entity);
	}

	@Override
	public Integer getCount(FileDownloadInf entity) throws Exception {
		return dao.countEntity(entity);
	}

	@Override
	public FileDownloadInf getEntity(FileDownloadInf entity) throws Exception {
		log.debug("方法参数："+entity.debug());
		FileDownloadInf fileDownloadInf = null;
		try{
			fileDownloadInf = dao.selectEntity(entity);
		}catch(Exception e){
			throw new Exception("查询信息异常!"+e.getMessage(),e);
		}
		log.debug("处理结果:",fileDownloadInf.debug());
		return fileDownloadInf;
	}

	@Override @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public int addEntity(FileDownloadInf entity) throws Exception {
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
	public int modifyEntity(FileDownloadInf entity) throws Exception {
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
	public int removeEntity(FileDownloadInf entity) throws Exception {
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

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int removeFile(String ids) throws Exception {
		String[] idList = ids.split(",");
		int sum = 0;
		for(String id : idList){
			try {
			int con = dao.deleteFile(id);
			sum = sum + con;
			} catch (Exception e) {
				log.error("删除文件异常！", e);
				throw new Exception("删除文件异常！");
			}
		}
		return sum;
	}



	
	
}

package com.tangdi.production.mpbase.service;

import java.util.List;

import com.tangdi.production.tdbase.service.BaseService;


/**
 * 手机支付平台,标准Service接口 定义命名规范<br/>
 * 继承自BaseService
 * @author zhengqiang
 *
 * @param <T>
 * @param <E>
 */
public interface MBaseService<T,E extends Exception> extends BaseService<T, E>{
	
	/**
	 * 通过获取条件过去总页数
	 * @param entity
	 * @return 记录数
	 * @throws E
	 */
	public Integer getCount(T entity) throws E;
	
	/**
	 * 分页查询
	 * @param entity
	 * @param page
	 * @param rows
	 * @return list
	 * @throws E
	 */
	public List<T> getListPage(T entity, int page, int rows) throws E;
  

}

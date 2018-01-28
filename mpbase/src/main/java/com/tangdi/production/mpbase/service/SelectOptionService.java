package com.tangdi.production.mpbase.service;

import java.util.Map;

import com.tangdi.production.mpbase.exception.SelectOptionException;

/**
 * 下拉框查询接口
 * @author zhengqiang
 *
 */
public interface SelectOptionService {
	
	/**
	 * 获取下拉框对象
	 * @param did   码表代码
	 * @param value 默认值
	 * @param type  类型
	 * @return String
	 * @throws SelectOptionException
	 */
	public String querySelectOption(String did,String value,String type) throws SelectOptionException;

	/**
	 * 查询所有数据字典信息
	 * @return
	 * @throws SelectOptionException
	 */
	public Map<String,Object> queryDCIT()throws SelectOptionException;

}

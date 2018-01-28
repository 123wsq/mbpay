package com.tangdi.production.mpbase.dao;

import java.util.Map;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.tangdi.production.mpbase.lib.MBaseBean;
import com.tangdi.production.mpbase.lib.SQLTemplate;
import com.tangdi.production.tdbase.dao.BaseDao;


/**
 * 手机支付平台<br/>
 * 通用基类接口,封装单表的[增、删、改、查]操作。
 * @author zhengqiang
 *
 * @param <T,E>
 */
public interface MBaseDao <T extends MBaseBean,E extends Exception> extends BaseDao<T,Exception>{  
  /**
   * 查询（不需要实现查询SQL）
   * @param entity
   * @return
   * @throws Exception
   */
  @SelectProvider(type = SQLTemplate.class,method = "select")
  public Map<String,String> select(T entity) throws E;  
  /**
   * 统计（不需要实现查询SQL）
   * @param entity
   * @return
   * @throws Exception
   */
  @SelectProvider(type = SQLTemplate.class,method = "count")   
  public int count(T entity) throws E;
    
  /** 
   * Insert语句从CUDTemplate类中生成 （不需要实现查询SQL）
   * @return int
   * @param entity 
   */  
  @InsertProvider(type = SQLTemplate.class,method = "insert")  
  public int insert(T entity) throws E;  
    
  /** 
   * Update语句从CUDTemplate类中生成 （不需要实现查询SQL）
   * @return int
   * @param entity 
   */  
  @UpdateProvider(type = SQLTemplate.class,method = "update")  
  public int update(T entity) throws E;  
    
  /** 
   * Delete语句从CUDTemplate类中生成 （不需要实现查询SQL）
   * @return int
   * @param entity 
   */  
  @DeleteProvider(type = SQLTemplate.class,method = "delete")  
  public int delete(T entity) throws E;  
  
 

}  


package com.tangdi.production.mpbase.lib;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.DELETE_FROM;
import static org.apache.ibatis.jdbc.SqlBuilder.FROM;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.SELECT;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;




/**
 * 
 * 手机支付平台<br/>
 * 定义SQL模版,用于组装SQL语句。
 * @author zhengqiang
 *
 * @param <T>
 */
public class SQLTemplate <T extends MBaseBean> {  
//	private static Logger  log = LoggerFactory.getLogger(SQLTemplate.class);
	
	/**
	 * 统计行数
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public String count(T obj) throws Exception{
		
		BEGIN();
		
		SELECT("COUNT(*)");
		FROM(obj.tableName());
	//	WHERE(obj.primaryKey());
		
		return SQL();
	}
	
	/**
	 * 查询
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public String select(T obj) throws Exception{
		BEGIN();
		
		SELECT("*");
		FROM(obj.tableName());
		WHERE(obj.primaryKey());
		
		return SQL();
	}
	/**
	 * 增加数据
	 * @param obj
	 * @return
	 * @throws Exception
	 */
    public String insert(T obj) throws Exception {  
        BEGIN();  
          
        INSERT_INTO(obj.tableName());  
        obj.caculationColumnList();  
        VALUES(obj.returnInsertColumnsName(), obj.returnInsertColumnsValue());  
        
        return SQL();
    }  
     /**
      * 修改数据
      * @param obj
      * @return
      * @throws Exception
      */
    public String update(T obj) throws Exception {  
          
        BEGIN();  
          
        UPDATE(obj.tableName());  
        obj.caculationColumnList();  
        SET(obj.returnUpdateSet());  
        WHERE(obj.primaryKey());  
    
        
        return SQL();
    }  
    /**
     * 删除数据
     * @param obj
     * @return
     * @throws Exception
     */
    public String delete(T obj) throws Exception {  
          
        BEGIN();  
      
        DELETE_FROM(obj.tableName());  
        WHERE(obj.primaryKey());  
        
        return SQL();
    }  
    
 
}  

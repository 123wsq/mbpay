package com.tangdi.production.mpbase.lib;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tangdi.production.mpbase.annotation.Column;
import com.tangdi.production.mpbase.annotation.PK;
import com.tangdi.production.mpbase.annotation.Table;
import com.tangdi.production.tdbase.domain.BaseBean;


/**
 * 手机支付平台<br/>
 * 定义基类实体
 * @author zhengqiang
 *
 */
public class MBaseBean extends BaseBean implements Serializable {  
	private static Logger  log = LoggerFactory.getLogger(MBaseBean.class);
    private static final long serialVersionUID = 1L;  
  
    //存储列信息
    private transient static Map<Class<? extends MBaseBean>,Map<String,String>> columnMap = new HashMap<Class<? extends MBaseBean>, Map<String,String>>();  
  
    /**
     * 获取表名
     * @return
     * @throws Exception
     */
    public String tableName() throws Exception {  
        Table table = this.getClass().getAnnotation(Table.class);  
        if(table != null) {
        	log.debug("Tablename={}",table.name());
            return table.name();  
        }else{  
            throw new Exception("Bean没有设置@Table。"); 
        }
    }  
      
    /**
     * 获取主键
     * @return
     */
    public String primaryKey() {  
    	StringBuilder sb = new StringBuilder();
    	int i = 0;
        for(Field field : this.getClass().getDeclaredFields()) {  
            if(field.isAnnotationPresent(PK.class) &&
            		field.isAnnotationPresent(Column.class)){
            	if(i++ != 0)  
                    sb.append(" AND "); 
            	sb.append(field.getAnnotation(Column.class).name()).append("=#{").append(field.getName()).append("}");
            	
            	i ++;
            }   
        }  
        
        if(i == 0)
        	throw new RuntimeException("Bean没有设置@PrimaryKey。");  
       
        return sb.toString();
    }  
    
    private boolean isNull(String fieldname) {  
        try {  
            Field field = this.getClass().getDeclaredField(fieldname);  
            return isNull(field);  
        } catch (SecurityException e) {  
            e.printStackTrace();  
        } catch (NoSuchFieldException e) {  
            e.printStackTrace();  
        } catch (IllegalArgumentException e) {  
            e.printStackTrace();  
        }  
          
        return false;  
    }  
      
    private boolean isNull(Field field) {  
        try {  
            field.setAccessible(true);  
            return field.get(this) == null;  
        } catch (SecurityException e) {  
            e.printStackTrace();  
        } catch (IllegalArgumentException e) {  
            e.printStackTrace();  
        } catch (IllegalAccessException e) {  
            e.printStackTrace();  
        }  
          
        return false;  
    }  
      
    public void caculationColumnList() {  
        if(columnMap.containsKey(this.getClass()))  
            return;  
          
        Field[] fields = this.getClass().getDeclaredFields();  
        Map<String,String> columnList = new HashMap<String,String>();
        for(Field field : fields) {  
            if(field.isAnnotationPresent(Column.class)) {
               columnList.put(field.getName(), field.getAnnotation(Column.class).name());
            }
        }  
          
        columnMap.put(this.getClass(), columnList);  
    }  
      
    public List<WhereColumn> returnWhereColumnsName() {  
        Field[] fields = this.getClass().getDeclaredFields();  
        List<WhereColumn> columnList = new ArrayList<WhereColumn>(fields.length);  
          
        for(Field field : fields) {  
            if(field.isAnnotationPresent(Column.class)&& !isNull(field))   // && !isNull(field)
                columnList.add(new WhereColumn(field.getAnnotation(Column.class).name(), 
                		field.getGenericType().equals(String.class)));  
        }  
          
        return columnList;  
    }  
      
    public class WhereColumn {  
        public String name;  
        public boolean isString;  
          
        public WhereColumn(String name,boolean isString) {  
            this.name = name;  
            this.isString = isString;  
        }  
    }  
      
    /** 
     * Insert列名
     * @return 
     */  
    public String returnInsertColumnsName() {  
        StringBuilder sb = new StringBuilder();  

        Map<String,String> map = columnMap.get(this.getClass());
        Iterator<String> iterator = map.keySet().iterator();
        int i=0;
        while(iterator.hasNext()){
        	String key = iterator.next();
        	if(isNull(key))  
                continue;  
              
            if(i++ != 0)  
                sb.append(',');  
            sb.append(map.get(key));  
        }
        return sb.toString();  
    }  
      
    /** 
     * Insert值
     * @return 
     */  
    public String returnInsertColumnsValue() {  
        StringBuilder sb = new StringBuilder();  

        Map<String,String> map = columnMap.get(this.getClass());
        Iterator<String> iterator = map.keySet().iterator();
        int i=0;
        while(iterator.hasNext()){
        	String key = iterator.next();
        	if(isNull(key))  
                continue;  
              
            if(i++ != 0)  
                sb.append(',');  
            sb.append("#{").append(key).append('}');  
        }
        return sb.toString();  
    }  
      
    /** 
     * Update Set的字段名值对
     * @return 
     */  
    public String returnUpdateSet() {  
        StringBuilder sb = new StringBuilder();  

        Map<String,String> map = columnMap.get(this.getClass());
        Iterator<String> iterator = map.keySet().iterator();
        int i=0;
        while(iterator.hasNext()){
        	String key = iterator.next();
        	if(isNull(key))  
                continue; 
        	try {
        		Field field = this.getClass().getDeclaredField(key);
        		if(!field.isAnnotationPresent(PK.class)){
        			if(i++ != 0)  
        				sb.append(',');  
        			sb.append(map.get(key)).append("=#{").append(key).append('}');  
        		}
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
        }
        
        return sb.toString();  
    }  
      
  
    public Integer getPrimaryKeyId(){return 0;}  
    

    /**
     * 打印类字段信息 
     */
    @Override  
    public String toString() {  
        Field[] fields = this.getClass().getDeclaredFields();  
        StringBuilder sb = new StringBuilder();  
        sb.append('[');  
        for(Field f : fields) {  
            if(Modifier.isStatic(f.getModifiers()) || Modifier.isFinal(f.getModifiers()))  
                continue;  
            Object value = null;  
            try {  
                f.setAccessible(true);  
                value = f.get(this);  
            } catch (IllegalArgumentException e) {  
                e.printStackTrace();  
            } catch (IllegalAccessException e) {  
                e.printStackTrace();  
            }  
            if(value != null)  
                sb.append(f.getName()).append('=').append(value).append(',');  
        }  
        sb.append(']');  
          
        return sb.toString();  
    }  

}

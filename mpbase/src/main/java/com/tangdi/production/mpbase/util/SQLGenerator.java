package com.tangdi.production.mpbase.util;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 生成查询数量的SQL 
 * @author zhengqiang
 *
 * @param <T>
 */
public class SQLGenerator<T> {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	private Set<String> columns;
	private String tableName;
	private String columnsStr;
	private String pkName;
	private String seq;

	public SQLGenerator(Set<String> columns, String tableName, String pkName,
			String seq) {
		super();
		this.columns = columns;
		this.tableName = tableName;
		this.pkName = pkName;
		this.seq = seq;
		this.columnsStr = StringUtils.join(this.columns, ",");
	}

	/**
	 * * 生成新增的SQL * *
	 * 
	 * @param t
	 *            *
	 * @param currentColumnFieldNames
	 *            *
	 * @return
	 */
	public String sql_create(T t, Map<String, String> currentColumnFieldNames) {
		List<Object> values = obtainFieldValues(t, currentColumnFieldNames);
		StringBuilder sql_build = new StringBuilder();
		sql_build.append("INSERT INTO ").append(tableName).append("(")
				.append(columnsStr).append(")values(")
				.append(StringUtils.join(values, ",")).append(")");
		String sql = sql_build.toString();
		logger.debug("生成的SQL为: " + sql);
		return sql;
	}

	/**
	 * 生成批量新增的SQL
	 * 
	 * @param tList
	 * @param currentColumnFieldNames
	 *            *
	 * @return
	 * */
	public String sql_createOfBatch(List<T> tList,
			Map<String, String> currentColumnFieldNames, Object pkVal) {
		StringBuilder sql_build = new StringBuilder();
		int len = tList.size(), i = 0;
		if (StringUtils.isNotEmpty(seq) && null == pkVal) {
			// ID使用序列
			sql_build.append("INSERT INTO ").append(tableName).append("(")
					.append(columnsStr).append(")");
			sql_build.append(" SELECT ").append(seq).append(" , a.* FROM ( ");
			for (; i < len; i++) {
				T t = tList.get(i);
				List<Object> values = obtainFieldValues(t,
						currentColumnFieldNames);
				values.remove(seq);
				if (i == 0) {
					sql_build.append(" SELECT ");
				} else {
					sql_build.append(" UNION ALL SELECT ");
				}
				int j = 0, vlen = values.size();
				for (; j < vlen; j++) {
					sql_build.append(values.get(j)).append(" AS T").append(j);
					if (j != (vlen - 1)) {
						sql_build.append(",");
					}
				}
				sql_build.append(" FROM DUAL ");
			}
			sql_build.append(" ) a ");
		} else { // ID没有使用序列
			sql_build.append("INSERT INTO ").append(tableName).append("(")
					.append(columnsStr).append(")");
			for (; i < len; i++) {
				T t = tList.get(i);
				List<Object> values = obtainFieldValues(t,
						currentColumnFieldNames);
				if (i == 0) {
					sql_build.append(" SELECT ");
				} else {
					sql_build.append(" UNION ALL SELECT ");
				}
				sql_build.append(StringUtils.join(values, ",")).append(
						" FROM DUAL ");
			}
		}
		String sql = sql_build.toString();
		logger.debug("生成的SQL为: " + sql);
		return sql;
	}

	/**
	 * * 提供给生成新增SQL 使用 * *
	 * 
	 * @param t
	 *            *
	 * @param currentColumnFieldNames
	 *            *
	 * @return
	 */
	private List<Object> obtainFieldValues(T t,
			Map<String, String> currentColumnFieldNames) {
		List<Object> values = new LinkedList<Object>();
		for (String column : columns) {
			Object value = ReflectionUtils.obtainFieldValue(t,
					currentColumnFieldNames.get(column));
			if (StringUtils.equalsIgnoreCase(column, pkName) && null == value) {
				value = seq;
			} else {
				value = handleValue(value);
			}
			values.add(value);
		}
		return values;
	}

	/** * 处理value * * @param value * @return */
	private Object handleValue(Object value) {
		if (value instanceof String) {
			value = "\'" + value + "\'";
		} else if (value instanceof Date) {
			Date date = (Date) value;
			String dateStr = DateUtil.convertDateToString(date,
					DateUtil.FULL_TIME_FORMAT_EN);
			value = "TO_TIMESTAMP('" + dateStr
					+ "','YYYY-MM-DD HH24:MI:SS.FF3')";
		} else if (value instanceof Boolean) {
			Boolean v = (Boolean) value;
			value = v ? 1 : 0;
		} else if (null == value || StringUtils.isBlank(value.toString())) {
			value = "''";
		}
		return value;
	}

	/** * 生成根据ID删除的SQL * * @param id * @return */
	public <PK> String sql_removeById(PK id) {
		StringBuilder sql_build = new StringBuilder();
		sql_build.append("DELETE FROM ").append(this.tableName)
				.append(" WHERE ").append(pkName).append(" = ").append(id);
		String sql = sql_build.toString();
		logger.debug("生成的SQL为: " + sql);
		return sql;
	}

	/** * 生成根据IDs批量删除的SQL * * @param ids * @return */
	public <PK> String sql_removeOfBatch(List<PK> ids) {
		StringBuilder sql_build = new StringBuilder();
		sql_build.append("DELETE FROM ").append(this.tableName)
				.append(" WHERE ").append(pkName).append(" IN ( 0 ");
		int len = ids.size(), i = 0;
		for (; i < len; i++) {
			PK id = ids.get(i);
			sql_build.append(",").append(id);
			if (i > 0 && i % (100 - 1) == 0) {
				sql_build.append(")").append(" OR ").append(pkName)
						.append(" IN ( 0 ");
			}
		}
		sql_build.append(")");
		String sql = sql_build.toString();
		logger.debug("生成的SQL为: " + sql);
		return sql;
	}

	public String sql_removeAll() {
		StringBuilder sql_build = new StringBuilder();
		sql_build.append("DELETE FROM ").append(this.tableName);
		String sql = sql_build.toString();
		logger.debug("生成的SQL为: " + sql);
		return sql;
	}

	/**
	 * * 生成更新的SQL * *
	 * 
	 * @param t
	 *            *
	 * @param currentColumnFieldNames
	 *            *
	 * @return
	 */
	public String sql_modify(T t, Map<String, String> currentColumnFieldNames) {
		List<String> values = obtainColumnVals(t, currentColumnFieldNames);
		Object id = ReflectionUtils.obtainFieldValue(t,
				currentColumnFieldNames.get(pkName));
		id = handleValue(id);
		StringBuilder sql_build = new StringBuilder();
		sql_build.append("UPDATE ").append(tableName).append(" SET ")
				.append(StringUtils.join(values, ",")).append(" WHERE ")
				.append(pkName).append(" = ").append(id);
		String sql = sql_build.toString();
		logger.debug("生成的SQL为: " + sql);
		return sql;
	}

	/**
	 * * 提供给生成更新SQL使用 * *
	 * 
	 * @param t
	 *            *
	 * @param currentColumnFieldNames
	 *            *
	 * @return
	 */
	private List<String> obtainColumnVals(T t,
			Map<String, String> currentColumnFieldNames) {
		List<String> colVals = new LinkedList<String>();
		for (String column : columns) {
			Object value = ReflectionUtils.obtainFieldValue(t,
					currentColumnFieldNames.get(column));
			if (value != null && !StringUtils.equalsIgnoreCase(column, pkName)) {
				colVals.add(column + "=" + handleValue(value));
			}
		}
		return colVals;
	}

	/** * 生成根据ID查询的SQL * * @param id * @return */
	public <PK> String sql_findOneById(PK id) {
		StringBuilder sql_build = new StringBuilder();
		sql_build.append("SELECT ").append(columnsStr).append(" FROM ")
				.append(this.tableName)
				.append(" WHERE ROWNUM = 1 AND " + pkName + " = " + id);
		String sql = sql_build.toString();
		logger.debug("生成的SQL为: " + sql);
		return sql;
	}

	/** * 生成查询所有的SQL * * @return */
	public String sql_findAll() {
		StringBuilder sql_build = new StringBuilder();
		sql_build.append("SELECT ").append(columnsStr).append(" FROM ")
				.append(this.tableName);
		String sql = sql_build.toString();
		logger.debug("生成的SQL为: " + sql);
		return sql;
	}

	/** * 生成查询数量的SQL * * @return */
	public String sql_findAllCount() {
		StringBuilder sql_build = new StringBuilder();
		sql_build.append("SELECT COUNT(1) ").append(" FROM ")
				.append(this.tableName);
		String sql = sql_build.toString();
		logger.debug("生成的SQL为: " + sql);
		return sql;
	}
}

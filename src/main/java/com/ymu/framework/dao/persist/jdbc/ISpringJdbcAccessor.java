package com.ymu.framework.dao.persist.jdbc;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.KeyHolder;

public interface ISpringJdbcAccessor {

	/**
	 * 查询，返回结果集列表
	 * 
	 * @param dataSource
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getList(JdbcTemplate dataSource, String sql)
			throws Exception;

	/**
	 * 插入数据
	 * 
	 * @param sql
	 * @param callBack
	 * @return
	 * @author mutian
	 */
	public KeyHolder add(JdbcTemplate jdbcTemplate,
			PreparedStatementCreator callBack) throws Exception;

	/**
	 * 插入数据并返回id
	 * 
	 * @param sql
	 *            完整的插入sql语句
	 * @return 该条实体id
	 * @throws Exception
	 * @author mutian
	 */
	public Long add(JdbcTemplate jdbcTemplate, String sql) throws Exception;

	/**
	 * '等'精确查询获取表的某条记录
	 * 
	 * @param tableName
	 * @param gts
	 *            查询条件集
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getObjectGt(JdbcTemplate jdbcTemplate,
			String tableName, Map<String, Object> gts) throws Exception;

	/**
	 * 批量插入数据
	 * 
	 * @param beans
	 * @param sql
	 * @param callback
	 * @return
	 * @throws Exception
	 */
	public <T> int[] batchUpdate(JdbcTemplate jdbcTemplate,List<T> beans, String sql,
			BatchPreparedStatementSetter callback) throws Exception;

	/**
	 * 获取某个表的最大id
	 * 
	 * @param jdbcTemplate
	 * @param tableName
	 *            表名
	 * @return 成功返回该表最大id，否则返回0
	 * @throws Exception
	 */
	public Long getMaxId(JdbcTemplate jdbcTemplate, String tableName)
			throws Exception;

}

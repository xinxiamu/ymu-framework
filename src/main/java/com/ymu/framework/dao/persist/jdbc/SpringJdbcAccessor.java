package com.ymu.framework.dao.persist.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.ymu.framework.utils.PrintUtils;
import com.ymu.framework.utils.SqlUtils;
import com.ymu.framework.utils.logger.LoggerUtil;

public final class SpringJdbcAccessor {

	/**
	 * 插入数据
	 * 
	 * @param jdbcTemplate
	 * @param callBack
	 * @return
	 * @author mutian
	 */
	public static KeyHolder add(JdbcTemplate jdbcTemplate, PreparedStatementCreator callBack) throws Exception {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(callBack, keyHolder);
		return keyHolder;
	}

	/**
	 * 查询，返回结果集列表
	 * 
	 * @param dataSource
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String, Object>> getList(JdbcTemplate jdbcTemplate, String sql) throws Exception {
		List<Map<String, Object>> resultSet = jdbcTemplate.queryForList(sql);
		return resultSet;
	}

	/**
	 * 插入数据并返回id
	 * 
	 * @param sql
	 *            完整的插入sql语句
	 * @return 该条实体id
	 * @throws Exception
	 * @author mutian
	 */
	public static Long add(JdbcTemplate jdbcTemplate, String sql) throws Exception {
		final String sql_insert = sql;

		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(sql_insert, new String[] { "id" });
				return ps;
			}
		}, keyHolder);

		long id = (Long) keyHolder.getKey();

		if (id > 0) {
			return id;
		}
		return 0L;
	}

	/**
	 * '等'精确查询获取表的某条记录
	 * 
	 * @param tableName
	 * @param gts
	 *            查询条件集
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> getObjectGt(JdbcTemplate jdbcTemplate, String tableName, Map<String, Object> gts)
			throws Exception {
		StringBuffer sb = new StringBuffer("select * from " + tableName + " where 1=1 ");
		if (gts == null || gts.isEmpty()) {
			return null;
		}
		Set<String> keys = gts.keySet();
		for (String key : keys) {
			Object fieldV = gts.get(key);
			if (fieldV == null) {
				continue;
			}
			if (fieldV == String.class) {
				sb.append(" and " + key + "='" + fieldV + "' ");
			} else {
				sb.append(" and " + key + "=" + fieldV + " ");
			}
		}
		Map<String, Object> resultSet = jdbcTemplate.queryForMap(sb.toString());
		return resultSet;
	}

	/**
	 * 批量插入数据
	 * 
	 * @param beans
	 * @param sql
	 * @param callback
	 * @return
	 * @throws Exception
	 */
	public static <T> int[] batchUpdate(JdbcTemplate jdbcTemplate, List<T> beans, String sql,
			BatchPreparedStatementSetter callback) throws Exception {
		int[] updateCounts = jdbcTemplate.batchUpdate(sql, callback);
		return updateCounts;
	}

	/**
	 * 批量插入数据
	 * 
	 * @param beans
	 * @param sql
	 * @param callback
	 * @return
	 * @throws Exception
	 */
	public static Long getMaxId(JdbcTemplate jdbcTemplate, String tableName) throws Exception {
		String sql = "SELECT MAX(id) AS maxId FROM " + tableName + "";
		Long maxId = 0L;
		maxId = jdbcTemplate.queryForObject(sql, Long.class);
		return maxId;
	}

	/**
	 * 大批量插入数据。百万级别插入(10秒)。推荐
	 * 
	 * @param jdbcTemplate
	 * @param tableName
	 *            表明
	 * @param fieds
	 *            要插入表字段
	 * @param values
	 *            字段对应值
	 */
	public static void addBatch(JdbcTemplate jdbcTemplate, String tableName, String[] fieds, List<Object[]> values)
			throws Exception {
		long startTime = System.currentTimeMillis();

		if (jdbcTemplate == null || fieds == null || values == null) {
			throw new NullPointerException("不能传null");
		}

		Connection conn = jdbcTemplate.getDataSource().getConnection();
		conn.setAutoCommit(false);
		// Statement st = conn.createStatement();
		// 比起st，pst会更好些
		PreparedStatement pst = conn.prepareStatement("");

		int total = values.size();
		int step = 10000; // 提交步长,一万条插一次
		int per = total % step == 0 ? total / step : total / step + 1; //不整除则 +1
		for (int i = 0; i < per; i++) {
			// 构建完整sql
			String sql = null;
			if (total <= step) {
				sql = SqlUtils.generateInsertSql(tableName, fieds, values);
			} else {
				if (i == per - 1) {
					sql = SqlUtils.generateInsertSql(tableName, fieds, values.subList(i * step, total));
				} else {
					sql = SqlUtils.generateInsertSql(tableName, fieds, values.subList(i * step, i * step + step));
				}
			}

			// 添加执行sql
			pst.addBatch(sql);
			// 执行操作
			pst.executeBatch();
			// 提交事务
			conn.commit();
		}
		// 头等连接
		pst.close();
		conn.close();

		long endTime = System.currentTimeMillis();
		PrintUtils.println("===批量插入用时（s）：" + (endTime - startTime) / 1000);
	}
}

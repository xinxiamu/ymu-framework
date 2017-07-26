package com.ymu.framework.dao.persist.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.ymu.framework.utils.time.DateUtil;

public class SpringJdbcAccessor implements ISpringJdbcAccessor {

	@Override
	public KeyHolder add(JdbcTemplate jdbcTemplate, PreparedStatementCreator callBack) throws Exception {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(callBack, keyHolder);
		return keyHolder;
	}

	@Override
	public List<Map<String, Object>> getList(JdbcTemplate jdbcTemplate, String sql) throws Exception {
		List<Map<String, Object>> resultSet = jdbcTemplate.queryForList(sql);
		return resultSet;
	}

	@Override
	public Long add(JdbcTemplate jdbcTemplate, String sql) throws Exception {
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

	@Override
	public Map<String, Object> getObjectGt(JdbcTemplate jdbcTemplate, String tableName, Map<String, Object> gts)
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

	@Override
	public <T> int[] batchUpdate(JdbcTemplate jdbcTemplate, List<T> beans, String sql,
			BatchPreparedStatementSetter callback) throws Exception {
		int[] updateCounts = jdbcTemplate.batchUpdate(sql, callback);
		return updateCounts;
	}

	@Override
	public Long getMaxId(JdbcTemplate jdbcTemplate, String tableName) throws Exception {
		String sql = "SELECT MAX(id) AS maxId FROM " + tableName + "";
		Long maxId = 0L;
		maxId = jdbcTemplate.queryForObject(sql, Long.class);
		return maxId;
	}

	@Override
	public void addBatch(JdbcTemplate jdbcTemplate, Object[] fieds, List<Object[]> values) throws Exception {
		if (jdbcTemplate == null || fieds == null || values == null) {
			throw new NullPointerException("不能传null");
		}

		Connection conn = jdbcTemplate.getDataSource().getConnection();
		conn.setAutoCommit(false);
		// Statement st = conn.createStatement();
		// 比起st，pst会更好些
		PreparedStatement pst = conn.prepareStatement("");
		for (int i = 1; i <= 100; i++) {
			// 提交步长,一万条插一次
			for (int j = 1; j <= 10000; j++) {

			}
			// 构建完整sql
			String sql = "";
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
	}
}

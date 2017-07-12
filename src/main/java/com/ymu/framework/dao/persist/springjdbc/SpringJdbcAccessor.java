package com.ymu.framework.dao.persist.springjdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

@Component
public class SpringJdbcAccessor implements ISpringJdbcAccessor {

	@Override
	public KeyHolder add(JdbcTemplate jdbcTemplate,PreparedStatementCreator callBack)
			throws Exception {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(callBack, keyHolder);
		return keyHolder;
	}

	@Override
	public List<Map<String, Object>> getList(JdbcTemplate jdbcTemplate,
			String sql) throws Exception {
		List<Map<String, Object>> resultSet = jdbcTemplate.queryForList(sql);
		return resultSet;
	}

	@Override
	public Long add(JdbcTemplate jdbcTemplate,String sql) throws Exception {
		final String sql_insert = sql;

		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement ps = con.prepareStatement(sql_insert,
						new String[] { "id" });
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
	public Map<String, Object> getObjectGt(JdbcTemplate jdbcTemplate,String tableName,
			Map<String, Object> gts) throws Exception {
		StringBuffer sb = new StringBuffer("select * from " + tableName
				+ " where 1=1 ");
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
	public <T> int[] batchUpdate(JdbcTemplate jdbcTemplate,List<T> beans, String sql,
			BatchPreparedStatementSetter callback) throws Exception {
		 int[] updateCounts = jdbcTemplate.batchUpdate(sql, callback);
		return updateCounts;
	}

	@Override
	public Long getMaxId(JdbcTemplate jdbcTemplate,String tableName)  throws Exception {
		String sql = "SELECT MAX(id) AS maxId FROM "+tableName+"";
		Long maxId = 0L;
		maxId = jdbcTemplate.queryForObject(sql, Long.class);
		return maxId;
	}
}

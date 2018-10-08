package com.ymu.framework.dao.base;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ymu.framework.base.BaseEntity;
import com.ymu.framework.dao.persist.jdbc.SpringJdbcAccessor;
import com.ymu.framework.dao.persist.jdbc.knife.JdbcBaseDaoImpl;
import com.ymu.framework.dao.persist.jdbc.knife.JdbcHelper;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 父类。
 * @param <T> 实体仓库操作对象。
 * @param <E> 实体对象。
 */
@SuppressWarnings("rawtypes")
public abstract class BaseDaoImpl<T extends BaseRepository,E extends BaseEntity> extends JdbcBaseDaoImpl<E> implements BaseDao<T> {

	@Autowired
	@PersistenceContext
	protected EntityManager em;

	@Autowired
	protected JdbcTemplate jdbcTemplate;
	
	@Autowired
	protected T mRepository;

	@Autowired
	protected SpringJdbcAccessor springJdbcAccessor;

	@Autowired
	protected DSLContext jooqDsl;
	
	@Override
	public T getMRepository() {
		return mRepository;
	}
	
	@Override
	public EntityManager getEntityManager() {
		return em;
	}

	@Override
	public SpringJdbcAccessor getSpringJdbcAccessor() {
		return springJdbcAccessor;
	}

	@Override
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
}
	

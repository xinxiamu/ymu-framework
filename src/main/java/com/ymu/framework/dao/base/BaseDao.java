package com.ymu.framework.dao.base;

import com.ymu.framework.dao.persist.jdbc.SpringJdbcAccessor;

import javax.persistence.EntityManager;

/**
 * 父接口。
 * @param <T> 实体仓库操作对象。
 */
@SuppressWarnings("rawtypes")
public interface BaseDao<T extends BaseRepository>  {

	 T getMRepository();
	 
	 EntityManager getEntityManager();

	 SpringJdbcAccessor getSpringJdbcAccessor();
}

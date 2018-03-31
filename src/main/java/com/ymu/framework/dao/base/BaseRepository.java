package com.ymu.framework.dao.base;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

@NoRepositoryBean
@Transactional(readOnly = true)
public interface BaseRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

	List<Object[]> listBySQL(String sql);

	@Transactional
	void updateBySql(String sql, Object... args);

	@Transactional
	void updateByHql(String hql, Object... args);
}

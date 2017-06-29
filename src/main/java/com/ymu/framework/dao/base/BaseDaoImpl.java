package com.ymu.framework.dao.base;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("rawtypes")
public abstract class BaseDaoImpl<T extends BaseRepository> implements BaseDao<T> {

	@Autowired
	@PersistenceContext
	protected EntityManager em;
	
	@Autowired
	protected T mRepository;
	
	@Override
	public T getMRepository() {
		return mRepository;
	}
	
	@Override
	public EntityManager getEntityManager() {
		return em;
	}
	
}
	

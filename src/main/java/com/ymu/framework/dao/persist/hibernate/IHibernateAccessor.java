package com.ymu.framework.dao.persist.hibernate;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.ymu.framework.utils.erro.BaseException;

public interface IHibernateAccessor {

	/**
	 * 保存对象
	 * @param sessionFactory
	 * @param class1
	 * @return	成功返回id，否则返回0
	 * @author mutian
	 */
	public <T> Long save(SessionFactory sessionFactory,Class<T> class1);
	
	/**
	 * 批量插入数据
	 * @param sessionFactory
	 * @param objects
	 * @return 	返回插入总条数
	 * @throws BaseException
	 */
	public <T> Long saveBatch(Session session,List<T> objects);
	
}

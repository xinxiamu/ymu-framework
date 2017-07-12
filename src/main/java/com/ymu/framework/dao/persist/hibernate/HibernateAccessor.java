package com.ymu.framework.dao.persist.hibernate;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

public class HibernateAccessor implements IHibernateAccessor {

	@Override
	public <T> Long save(SessionFactory sessionFactory, Class<T> class1) {
		Long identifier = 0L;

		Session session = sessionFactory.openSession();
		try {
			Serializable id = session.save(class1);
			identifier = Long.valueOf(id.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return identifier;
	}

	@Override
	public <T> Long saveBatch(Session session, List<T> objects) {
		Long k = 0L;

		Transaction tx = session.beginTransaction();
		for (int i = 0; i < objects.size(); i++) {
			session.save(objects.get(i));
			k++;
			// 将session一级缓存的数据定期刷入数据库,并清空
			if (i % 20 == 0) {
				session.flush();
				session.clear();
			}
		}
		tx.commit();
		if (session != null) {
			session.close();
		}
		return k;
	}

}

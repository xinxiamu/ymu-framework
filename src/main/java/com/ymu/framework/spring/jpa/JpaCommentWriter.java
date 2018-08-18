package com.ymu.framework.spring.jpa;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * 用来处理Jpa的注释的
 *
 * @author lujijiang
 *
 */
public class JpaCommentWriter implements InitializingBean {

	/**
	 * JPA工厂
	 */
	private EntityManagerFactory entityManagerFactory;

	public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}

	/**
	 * 初始化处理方法
	 */
	private void initializeStartup() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {
			if (Hibernates.isHibernate(entityManager)) {
				Hibernates.writeJpaComments(entityManagerFactory.unwrap(SessionFactory.class));
			}
		} finally {
			entityManager.close();
		}
	}

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(entityManagerFactory, "The entityManagerFactory should be null");
		initializeStartup();
	}

}

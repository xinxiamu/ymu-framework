package com.ymu.framework.dao.ds;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 *  解析注入的数据源。
 */
public abstract class AbstractDataSourceAspect {

	/**
	 * 给定默认数据源
	 * @return
	 */
	public abstract String defaultDb();

	@Before("@annotation(com.ymu.framework.dao.ds.DSInject)")
	public void beforeSwitchDS(JoinPoint point) {

		// 获得当前访问的class
		Class<?> className = point.getTarget().getClass();

		// 获得访问的方法名
		String methodName = point.getSignature().getName();
		// 得到方法的参数的类型
		Class[] argClass = ((MethodSignature) point.getSignature()).getParameterTypes();
		String dataSource = defaultDb(); //默认主库
		try {
			// 得到访问的方法对象
			Method method = className.getMethod(methodName, argClass);

			// 判断是否存在@DBInject注解
			if (method.isAnnotationPresent(DSInject.class)) {
				DSInject annotation = method.getAnnotation(DSInject.class);
				// 取出注解中的数据源名
				dataSource = annotation.value();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 切换数据源
		DataSourceContextHolder.setDS(dataSource);

	}

	@After("@annotation(com.ymu.framework.dao.ds.DSInject)")
	public void afterSwitchDS(JoinPoint point) {
		DataSourceContextHolder.clearDS();
	}
}

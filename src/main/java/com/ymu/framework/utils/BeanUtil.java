package com.ymu.framework.utils;

import java.beans.PropertyDescriptor;
import java.beans.Transient;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class BeanUtil {

	private BeanUtil() {
	}

	/**
	 * 持久化实体bean转换成map。注意：实体不用基础类型。要用基础类型封装类。如long->Long
	 * 
	 * @param entityBean
	 * @param ignoreProperties
	 * @return
	 */
	public static final Map<String, Object> beanToMap(final Object entityBean, String... ignoreProperties) {
		PropertyDescriptor propertyDescriptor = null;
		List<String> ignoreList = (ignoreProperties != null) ? Arrays.asList(ignoreProperties) : null;
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Class<? extends Object> entityClazz = entityBean.getClass();
			Field[] fields = entityClazz.getDeclaredFields();
			for (Field field : fields) {
				int i = field.getModifiers();
				if (Modifier.isStatic(i)) { // 静态域
					continue;
				}

				String fieldName = field.getName();// 字段名
				propertyDescriptor = new PropertyDescriptor(fieldName, entityClazz);
				Method readMethod = propertyDescriptor.getReadMethod();

				if (readMethod == null || (ignoreList != null && ignoreList.contains(propertyDescriptor.getName()))
						|| readMethod.getAnnotation(Transient.class) != null
						|| field.getAnnotation(javax.persistence.Transient.class) != null
						|| readMethod.getAnnotation(javax.persistence.Transient.class) != null) {
					continue;
				}

				if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
					readMethod.setAccessible(true);
				}
				Object value = readMethod.invoke(entityBean);
				// 不复制null值
				if (value == null) {
					continue;
				}
				result.put(fieldName, value);
			}
		} catch (Exception e) {
			throw new RuntimeException(
					"Could not copy property '" + propertyDescriptor.getName() + "' from source to map", e);
		}
		return result;
	}
}

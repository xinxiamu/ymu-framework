package com.ymu.framework.utils;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.beans.Transient;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public final class BeanUtil extends org.apache.commons.beanutils.BeanUtils {

	private BeanUtil() {
	}

	static Logger logger = LoggerFactory.getLogger(BeanUtil.class);

	private static final Map<Class<?>, Map<String, PropertyDescriptor>> PROPERTY_DESCRIPTORS_CASEINSENSITIVE_MAP = new ConcurrentHashMap<Class<?>, Map<String, PropertyDescriptor>>();

	private static final Map<Class<?>, Map<String, PropertyDescriptor>> PROPERTY_DESCRIPTORS_CASESENSITIVE_MAP = new ConcurrentHashMap<Class<?>, Map<String, PropertyDescriptor>>();

	/**
	 * 拷贝器
	 * 
	 * @author lujijiang
	 *
	 */
	public static class Copier {

		@SuppressWarnings("rawtypes")
		private final static Map<Class, Map<Class, FastCopier>> COPIERS_CASESENSITIVE_MAP = new ConcurrentHashMap<Class, Map<Class, FastCopier>>();

		@SuppressWarnings("rawtypes")
		private final static Map<Class, Map<Class, FastCopier>> COPIERS_CASEINSENSITIVE_MAP = new ConcurrentHashMap<Class, Map<Class, FastCopier>>();

		/**
		 * 包含字段
		 */
		Set<String> includes;

		/**
		 * 排除字段
		 */
		Set<String> excludes;

		/**
		 * 是否大小写敏感，默认否
		 * 
		 * @return
		 */
		boolean caseSensitive = true;

		/**
		 * 是否包含null值属性
		 * 
		 * @return
		 */
		boolean includeNull = true;

		/**
		 * 是否包含null值属性
		 * 
		 * @return
		 */
		boolean includeEmpty = true;

		/**
		 * 是否包含基类型的0
		 * 
		 * @return
		 */
		boolean includePrimitiveZero = true;

		/**
		 * 是否包含基类型的false
		 * 
		 * @return
		 */
		boolean includePrimitiveFalse = true;

		/**
		 * 数据来源的bean
		 */
		Object from;

		/**
		 * 数据目标bean
		 */
		List<Object> toList;

		/**
		 * 包含null字段<br>
		 * copier.includeNull();
		 * 
		 * @return 复印机对象本身
		 */
		public Copier includeNull() {
			this.includeNull = true;
			return this;
		}

		/**
		 * 排除null字段<br>
		 * copier.excludeNull();
		 * 
		 * @return 复印机对象本身
		 */
		public Copier excludeNull() {
			this.includeNull = false;
			return this;
		}

		/**
		 * 排除空对象字段<br>
		 * copier.includeEmpty();
		 * 
		 * @return 复印机对象本身
		 */
		public Copier includeEmpty() {
			this.includeEmpty = true;
			return this;
		}

		/**
		 * 排除空对象字段<br>
		 * copier.excludeEmpty();
		 * 
		 * @return 复印机对象本身
		 */
		public Copier excludeEmpty() {
			this.includeEmpty = false;
			return this;
		}

		/**
		 * 包含基类型的false
		 * 
		 * @return
		 */
		public Copier includePrimitiveFalse() {
			this.includePrimitiveFalse = true;
			return this;
		}

		/**
		 * 排除基类型的false
		 * 
		 * @return
		 */
		public Copier excludePrimitiveFalse() {
			this.includePrimitiveFalse = false;
			return this;
		}

		/**
		 * 包含基类型的0
		 * 
		 * @return
		 */
		public Copier includePrimitiveZero() {
			this.includePrimitiveZero = true;
			return this;
		}

		/**
		 * 排除基类型的0
		 * 
		 * @return
		 */
		public Copier excludePrimitiveZero() {
			this.includePrimitiveZero = false;
			return this;
		}

		/**
		 * 字段名大小写敏感
		 * 
		 * @return
		 */
		public Copier caseSensitive() {
			this.caseSensitive = true;
			return this;
		}

		/**
		 * 字段名忽略大小写
		 * 
		 * @return
		 */
		public Copier caseInsensitive() {
			this.caseSensitive = false;
			return this;
		}

		/**
		 * 指定包含的字段名称<br>
		 * 
		 * <pre>
		 * Copier copier = Lang.newCopier();
		 * copier.includes(&quot;name&quot;);
		 * </pre>
		 * 
		 * @param names
		 *            字段名
		 * @return 复印机对象本身
		 */
		public Copier includes(String... names) {
			for (String name : names) {
				if (name == null) {
					continue;
				}
				name = name.trim();
				if (this.includes == null) {
					includes = new HashSet<String>();
				}
				this.includes.add(name);
			}
			return this;
		}

		/**
		 * 指定排除的字段名称<br>
		 * 
		 * <pre>
		 * Copier copier = Lang.newCopier();
		 * copier.excludes(&quot;name&quot;);
		 * </pre>
		 * 
		 * @param names
		 *            字段名
		 * @return 复印机对象本身
		 */
		public Copier excludes(String... names) {
			for (String name : names) {
				if (name == null) {
					continue;
				}
				name = name.trim();
				if (this.excludes == null) {
					excludes = new HashSet<String>();
				}
				this.excludes.add(name);
			}
			return this;
		}

		public Copier from(Object from) {
			this.from = from;
			done();
			return this;
		}

		public Copier to(Object... tos) {
			toList = new ArrayList<Object>(tos.length);
			for (Object to : tos) {
				if (to != null) {
					toList.add(to);
				}
			}
			done();
			return this;
		}

		/**
		 * 清除内含对象，必须重新调用from和to方法才能再次拷贝数据
		 * 
		 * @return
		 */
		public Copier clear() {
			this.from = null;
			this.toList = null;
			return this;
		}

		/**
		 * 完成拷贝
		 */
		private void done() {
			if (from == null || toList == null || toList.isEmpty()) {
				return;
			}
			fastCopy();
		}

		@SuppressWarnings("rawtypes")
		private boolean isEmpty(Object obj) {
			if (obj == null) {
				return true;
			}
			if (obj instanceof Map) {
				return ((Map) obj).isEmpty();
			}
			if (obj instanceof Collection) {
				return ((Collection) obj).isEmpty();
			}
			if (obj.getClass().isArray()) {
				return Array.getLength(obj) == 0;
			}
			return false;
		}

		@SuppressWarnings("rawtypes")
		private void fastCopy() {
			for (Object to : toList) {
				if (to == null) {
					continue;
				}
				Class fromClass = from.getClass();
				Class toClass = to.getClass();

				Map<Class, FastCopier> copierMap = caseSensitive ? COPIERS_CASESENSITIVE_MAP.get(fromClass)
						: COPIERS_CASEINSENSITIVE_MAP.get(fromClass);
				if (copierMap == null) {
					copierMap = new ConcurrentHashMap<Class, FastCopier>();
					if (caseSensitive) {
						COPIERS_CASESENSITIVE_MAP.put(fromClass, copierMap);
					} else {
						COPIERS_CASEINSENSITIVE_MAP.put(fromClass, copierMap);
					}
				}

				FastCopier cglibCopier = copierMap.get(toClass);
				if (cglibCopier == null) {
					cglibCopier = FastCopier.create(fromClass, toClass, true, caseSensitive);
					copierMap.put(toClass, cglibCopier);
				}

				cglibCopier.copy(from, to, new FastCopierConverter() {
					public Object convert(Object fromValue, Object target, Method sourceReader, Method targetWriter,
							Object context) {
						Class sourceClass = sourceReader.getReturnType();
						Class targetClass = targetWriter.getParameterTypes()[0];
						if (!getWrapper(targetClass).isAssignableFrom(getWrapper(sourceClass))) {
							logger.debug(
									"The property {} has been ignored to copy because setter's type {} is not assignable from getter's type {}",
									context, targetClass, sourceClass);
							return target;
						}
						Type sourceGeneric = sourceReader.getGenericReturnType();
						Type targetGeneric = targetWriter.getGenericParameterTypes()[0];
						if (!assertGenericTypeEqual(sourceGeneric, targetGeneric)) {
							logger.debug(
									"The property {} has been ignored to copy because getter's type {} is not equal with setter's type {}",
									context, sourceGeneric, targetGeneric);
							return target;
						}
						if (includes != null && !includes.contains(context)) {
							return target;
						}
						if (excludes != null && excludes.contains(context)) {
							return target;
						}
						if (!includeNull && fromValue == null) {
							return target;
						}
						if (!includeEmpty && fromValue != null && isEmpty(fromValue)) {
							return target;
						}
						if (fromValue != null) {
							if (!includePrimitiveFalse && sourceClass.equals(boolean.class)
									&& BeanUtil.equals(fromValue, false)) {
								return target;
							}
							if (!includePrimitiveZero && sourceClass.isPrimitive() && fromValue instanceof Number
									&& BeanUtil.equals(fromValue, 0)) {
								return target;
							}
						}
						return fromValue;
					}

					private boolean assertGenericTypeEqual(Type source, Type target) {
						if (source instanceof Class && target instanceof Class) {
							return true;
						}
						if (source instanceof WildcardType || target instanceof WildcardType) {
							return false;
						}
						return source.equals(target);
					}

					private Class<?> getWrapper(Class<?> type) {
						if (!type.isPrimitive()) {
							return type;
						}
						if (boolean.class.equals(type)) {
							return Boolean.class;
						} else if (float.class.equals(type)) {
							return Float.class;
						} else if (long.class.equals(type)) {
							return Long.class;
						} else if (int.class.equals(type)) {
							return Integer.class;
						} else if (short.class.equals(type)) {
							return Short.class;
						} else if (byte.class.equals(type)) {
							return Byte.class;
						} else if (double.class.equals(type)) {
							return Double.class;
						} else if (char.class.equals(type)) {
							return Character.class;
						} else {
							return Void.class;
						}
					}
				});
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static boolean equals(Object a, Object b) {
		if (a == b) {
			return true;
		}
		if (a == null || b == null) {
			return false;
		}
		if (a.equals(b)) {
			return true;
		}
		// 比较
		if (a instanceof Comparable && b instanceof Comparable) {
			return ((Comparable) a).compareTo((Comparable) b) != 0;
		}
		return false;
	}

	/**
	 * 从数据来源对象创建一个Copier
	 * 
	 * @param from
	 * @return
	 */
	public static Copier from(Object from) {
		return new Copier().from(from);
	}

	/**
	 * 从数据目标对象创建一个Copier
	 * 
	 * @param tos
	 * @return
	 */
	public static Copier to(Object... tos) {
		return new Copier().to(tos);
	}

	private static Map<String, PropertyDescriptor> getPropertyMap(Class<?> type, boolean caseSensitive) {
		Map<String, PropertyDescriptor> propertyDescriptors = caseSensitive
				? PROPERTY_DESCRIPTORS_CASESENSITIVE_MAP.get(type) : PROPERTY_DESCRIPTORS_CASEINSENSITIVE_MAP.get(type);
		if (propertyDescriptors == null) {
			propertyDescriptors = new ConcurrentHashMap<String, PropertyDescriptor>();
			try {
				for (PropertyDescriptor propertyDescriptor : Introspector.getBeanInfo(type).getPropertyDescriptors()) {
					if (propertyDescriptor.getPropertyType() == null) {
						continue;
					}
					if ("class".equals(propertyDescriptor.getName())) {
						continue;
					}
					if (caseSensitive) {
						propertyDescriptors.put(propertyDescriptor.getName(), propertyDescriptor);
					} else {
						propertyDescriptors.put(propertyDescriptor.getName().toLowerCase(), propertyDescriptor);
					}
				}
			} catch (IntrospectionException e) {
				throw new RuntimeException(e);
			}
			if (caseSensitive) {
				PROPERTY_DESCRIPTORS_CASESENSITIVE_MAP.put(type, propertyDescriptors);
			} else {
				PROPERTY_DESCRIPTORS_CASEINSENSITIVE_MAP.put(type, propertyDescriptors);
			}
		}
		return propertyDescriptors;

	}

	/**
	 * 为bean设置属性值
	 * 
	 * @param bean
	 *            要设置的对象 属性的类型转换器
	 * @param nameString
	 *            属性名，使用空格、逗号等隔开多个字段名，忽略大小写
	 * @param args
	 *            属性值
	 */
	public static void set(Object bean, String nameString, Object... args) {
		Assert.notNull(bean, "The bean should not be null");
		Assert.notNull(args, "The args should not be null");
		if (nameString == null || nameString.trim().length() == 0) {
			throw new IllegalStateException(String.format("The nameString should not be empty"));
		}
		String[] names = nameString.split("[\\s,，]+");
		Assert.isTrue(names.length == args.length,
				String.format("The names length is %d but got %d arguments", names.length, args.length));
		Class<?> beanType = bean.getClass();
		Map<String, PropertyDescriptor> propertyMap = getPropertyMap(beanType, false);
		for (int i = 0; i < names.length; i++) {
			String name = names[i].trim();
			PropertyDescriptor propertyDescriptor = propertyMap.get(name);
			Assert.notNull(propertyDescriptor, String.format("The property %s of bean %s is not exist", name, bean));
			Method writeMethod = propertyDescriptor.getWriteMethod();
			Assert.notNull(writeMethod, String.format("The property %s of bean %s is read only", name, bean));
			try {
				writeMethod.invoke(bean, args[i]);
			} catch (IllegalAccessException e) {
				throw Lang.unchecked(e);
			} catch (IllegalArgumentException e) {
				throw Lang.unchecked(e);
			} catch (InvocationTargetException e) {
				throw Lang.unchecked(e);
			}
		}
	}

	// ------------------------------ end ------------------------//

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

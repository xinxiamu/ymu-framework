package com.ymu.framework.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MapUtils {

	/**
	 * 拿对应key值。没key,或者value为字符串null，NULL,"",则返回默认值
	 * 
	 * @param map
	 * @param key
	 * @return
	 * @author mutian
	 * @createTime:2014年7月13日下午8:05:14
	 * @updateAuthor:
	 * @updateTime:
	 */
	public static Object getMapValusT(Map<String, Object> map, String key,
			Object defaultValue) {
		if (map == null || map.isEmpty()) {
			return defaultValue;
		}
		Set<String> keys = map.keySet();
		if (keys.contains(key)) {
			Object value = map.get(key);
			if (value == null) {
				return defaultValue;
			} else if (value.getClass().isInstance(String.class)) {
				String valueStr = (String) value;
				if (valueStr.equalsIgnoreCase("null") || valueStr.equals("")) {
					return defaultValue;
				}
			}
			return value;

		} else {
			return defaultValue;
		}
	}

	public static Object getOj(Map<String, Object> map, String key) {
		Set<String> keys = map.keySet();
		if (keys.contains(key)) {
			return map.get(key);
		} else {
			return null;
		}
	}

	public static String getStr(Map<String, String> map, String key) {
		Set<String> keys = map.keySet();
		if (keys.contains(key)) {
			return map.get(key).toString();
		} else {
			return null;
		}
	}

	public static String getStr(Map<String, String> map, String key,
			String defaultValue) {
		if (map == null || map.isEmpty()) {
			return defaultValue;
		}

		Set<String> keys = map.keySet();
		if (keys.contains(key)) {
			return map.get(key).toString();
		} else {
			return defaultValue;
		}
	}

	public static Double getDouble(Map<String, String> map, String key,
			Double defaultValue) {
		if (map == null || map.isEmpty()) {
			return defaultValue;
		}

		Set<String> keys = map.keySet();
		if (keys.contains(key)) {
			String valueStr = map.get(key).toString();
			return Double.valueOf(valueStr);
		} else {
			return defaultValue;
		}
	}

	public static int getInt(Map<String, String> map, String key,
			int defaultValue) {
		if (map == null || map.isEmpty()) {
			return defaultValue;
		}

		Set<String> keys = map.keySet();
		if (keys.contains(key)) {
			String valueStr = map.get(key).toString();
			return Integer.valueOf(valueStr);
		} else {
			return defaultValue;
		}
	}

	/**
	 * 过滤map中的null，NULL值，用空代替。
	 * 
	 * @param map
	 * @return
	 * @author mutian
	 * @createTime:2014年7月16日下午3:28:06
	 * @updateAuthor:
	 * @updateTime:
	 */
	public static Map<String, Object> filterNull(Map<String, Object> map) {
		Map<String, Object> filterMap = new HashMap<String, Object>();
		Set<String> keys = map.keySet();
		for (String key : keys) {
			Object value = map.get(key);
			if (value == null) {
				value = "";
			} else if (value.getClass().isInstance(String.class)) {
				String valueStr = (String) value;
				if (valueStr.equalsIgnoreCase("null")) {
					value = "";
				}
			}
			filterMap.put(key, value);
		}
		return filterMap;
	}

	public static Map<String, String> filterNullStr(Map<String, String> map) {
		Map<String, String> filterMap = new HashMap<String, String>();
		Set<String> keys = map.keySet();
		for (String key : keys) {
			String value = map.get(key);
			if (value.equalsIgnoreCase("null")) {
				value = "";
			}
			filterMap.put(key, value);
		}
		return filterMap;
	}

	/**
	 * 把map1的值copy到map2中
	 * 
	 * @param map1
	 * @param map2
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void map1ToMap2(Map map1, Map map2) {
		if (map1 == null || map2 == null || map1.isEmpty()) {
			return;
		}

		Set<String> keys = map1.keySet();
		for (String key : keys) {
			map2.put(key, map1.get(key));
		}
	}

}

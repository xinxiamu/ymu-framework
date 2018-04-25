package com.ymu.framework.utils;

import com.alibaba.fastjson.JSON;
import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Redis缓存键生成器
 * 
 * @author lujijiang
 *
 */
public class RedisKeyGenerator implements KeyGenerator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.cache.interceptor.KeyGenerator#generate(java.lang.
	 * Object, java.lang.reflect.Method, java.lang.Object[])
	 */
	public Object generate(Object target, Method method, Object... params) {
		Map<String, Object> keyMap = new LinkedHashMap<>();
		keyMap.put("@target", target);
		keyMap.put("@method", generateMethodMap(method));
		keyMap.put("@params", params);
		return JSON.toJSONString(keyMap);
	}

	/**
	 * 生成方法对应Map
	 * 
	 * @param method
	 * @return
	 */
	private Map<String, Object> generateMethodMap(Method method) {
		Map<String, Object> methodMap = new LinkedHashMap<>();
		methodMap.put("name", method.getName());
		String[] parameterTypes = new String[method.getParameterTypes().length];
		for (int i = 0; i < parameterTypes.length; i++) {
			parameterTypes[i] = method.getParameterTypes()[i].getCanonicalName();
		}
		methodMap.put("parameterTypes", parameterTypes);
		return methodMap;
	}

}

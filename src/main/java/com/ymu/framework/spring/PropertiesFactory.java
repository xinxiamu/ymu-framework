package com.ymu.framework.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * 调用该类中的方法获取配置文件中的key-value值
 */
@Component
public class PropertiesFactory extends PropertyPlaceholderConfigurer {

	public static Map<String, Object> NAME_VALUE_PAIR_ALL;

	@Override
	protected void processProperties(
			ConfigurableListableBeanFactory beanFactoryToProcess,
			Properties props) throws BeansException {
		super.processProperties(beanFactoryToProcess, props);
		NAME_VALUE_PAIR_ALL = new HashMap<String, Object>();
		for (Iterator<Object> localIterator = props.keySet().iterator(); localIterator
				.hasNext();) {
			String keyStr = localIterator.next().toString();
			String value = props.getProperty(keyStr);
			NAME_VALUE_PAIR_ALL.put(keyStr, value);
		}
	}

	public static String getPropertyString(String propertiesKey) {
		return NAME_VALUE_PAIR_ALL.get(propertiesKey).toString();
	}

	public static long getPropertyLong(String propertiesKey) {
		return Long.valueOf(NAME_VALUE_PAIR_ALL.get(propertiesKey).toString());
	}

}
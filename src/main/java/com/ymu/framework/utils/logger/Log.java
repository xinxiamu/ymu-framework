package com.ymu.framework.utils.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @类描述：logback日志帮助类
 * 
 * @创建人：木天
 * @创建时间：2014年6月11日上午10:51:26
 * @修改人：Administrator
 * @修改时间：2014年6月11日上午10:51:26
 * @修改备注：
 * @version v1.0
 * @Copyright 木天
 * @mail 932852117@qq.com
 */
public class Log {
	
	public static void println(String msg) {
		System.out.println(msg);
	}
	
	public static void print(String msg) {
		System.out.print(msg);
	}

	public static <T> void i(Class<T> c, String msg) {
		init(c).info(msg);
	}

	public static void d(Object o, String msg) {
		init(o.getClass()).debug(msg);
	}
	
	public static <T> void d(Class<T> c, String msg) {
		init(c).debug(msg);
	}

	public static <T> void w(Class<T> c, String msg) {
		init(c).warn(msg);
	}

	public static <T> void e(Class<T> c, String msg) {
		init(c).error(msg);
	}

	public static <T> void t(Class<T> c, String msg) {
		init(c).trace(msg);
	}

	private static <T> Logger init(Class<T> c) {
		Logger logger = LoggerFactory.getLogger(c);
		return logger;
	}
}

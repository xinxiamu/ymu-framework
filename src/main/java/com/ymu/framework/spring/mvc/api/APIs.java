package com.ymu.framework.spring.mvc.api;

/**
 * API工具类
 *
 */
public class APIs {

	/**
	 * 抛出API错误
	 * 
	 * @param code
	 *            错误代码，自定义
	 * @param message
	 *            错误消息
	 * @param throwable
	 *            原始异常
	 * @return ApiException异常对象
	 */
	public static ApiException error(int code, String message, Throwable throwable) {
		return new ApiException(code, message,throwable);
	}
}
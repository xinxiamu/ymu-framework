package com.ymu.framework.spring.mvc.api;

import org.springframework.util.Assert;

/**
 *  API错误消息
 */
public class ApiException extends RuntimeException {

	private int code;

	private String message;

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public ApiException(int code, String message, Throwable throwable) {
		super(ApiException.class.getCanonicalName().concat(":").concat(String.valueOf(code)).concat(":")
				.concat(message), throwable);
		Assert.notNull(message, "The ApiException's message should not be null");
		this.code = code;
		this.message = message;
	}
}

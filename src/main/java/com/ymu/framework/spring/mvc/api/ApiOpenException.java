package com.ymu.framework.spring.mvc.api;

import org.springframework.util.Assert;

/**
 *  API错误消息。对外暴露统一异常。
 */
public class ApiOpenException extends RuntimeException {

	private int code;

	private String message;

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public ApiOpenException(int code, String message, Throwable throwable) {
		super(ApiOpenException.class.getCanonicalName().concat(":").concat(String.valueOf(code)).concat(":")
				.concat(message), throwable);
		Assert.notNull(message, "The ApiException's message should not be null");
		this.code = code;
		this.message = message;
	}
}

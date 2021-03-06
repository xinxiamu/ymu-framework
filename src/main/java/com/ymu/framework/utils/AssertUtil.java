package com.ymu.framework.utils;

public final class AssertUtil extends org.springframework.util.Assert {

	private AssertUtil() {
	}

	public final static void nullOrEmptyException(String str) {
		if (null == str || "".equals(str)) {
			throw new NullPointerException("符串不能为null或者空");
		}
	}

}

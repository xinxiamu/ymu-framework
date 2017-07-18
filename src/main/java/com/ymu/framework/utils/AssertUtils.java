package com.ymu.framework.utils;

public final class AssertUtils {

	private AssertUtils() {
	}

	public final static void nullOrEmptyException(String str) {
		if (null == str || "".equals(str)) {
			throw new NullPointerException("符串不能为null或者空");
		}
	}

}